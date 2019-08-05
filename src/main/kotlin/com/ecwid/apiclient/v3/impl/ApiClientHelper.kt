package com.ecwid.apiclient.v3.impl

import com.ecwid.apiclient.v3.config.ApiServerDomain
import com.ecwid.apiclient.v3.config.ApiStoreCredentials
import com.ecwid.apiclient.v3.config.DEFAULT_HTTPS_PORT
import com.ecwid.apiclient.v3.config.LoggingSettings
import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.dto.EcwidApiError
import com.ecwid.apiclient.v3.exception.EcwidApiException
import com.ecwid.apiclient.v3.exception.JsonDeserializationException
import com.ecwid.apiclient.v3.httptransport.*
import com.ecwid.apiclient.v3.jsontransformer.GsonJsonTransformer
import java.io.ByteArrayInputStream
import java.io.FileInputStream
import java.net.URI
import java.util.*
import java.util.logging.Level
import java.util.logging.Logger
import kotlin.random.Random

private const val API_TOKEN_PARAM_NAME = "token"

internal class ApiClientHelper(
		private val apiServerDomain: ApiServerDomain,
		private val storeCredentials: ApiStoreCredentials,
		private val loggingSettings: LoggingSettings,
		private val httpTransport: HttpTransport
) {

	private val log = Logger.getLogger(this::class.java.name)

	internal val jsonTransformer = GsonJsonTransformer()

	inline fun <reified V> makeRequest(
			request: ApiRequest
	): V {
		val requestInfo = request.toRequestInfo()
		val httpRequest = when (requestInfo.method) {
			HttpMethod.GET -> HttpRequest.HttpGetRequest(
					uri = createApiEndpointUri(requestInfo.endpoint),
					params = requestInfo.params.withApiTokenParam(storeCredentials.apiToken)
			)
			HttpMethod.POST -> HttpRequest.HttpPostRequest(
					uri = createApiEndpointUri(requestInfo.endpoint),
					params = requestInfo.params.withApiTokenParam(storeCredentials.apiToken),
					transportHttpBody = requestInfo.httpBody.prepare(jsonTransformer)
			)
			HttpMethod.PUT -> HttpRequest.HttpPutRequest(
					uri = createApiEndpointUri(requestInfo.endpoint),
					params = requestInfo.params.withApiTokenParam(storeCredentials.apiToken),
					transportHttpBody = requestInfo.httpBody.prepare(jsonTransformer)
			)
			HttpMethod.DELETE -> HttpRequest.HttpDeleteRequest(
					uri = createApiEndpointUri(requestInfo.endpoint),
					params = requestInfo.params.withApiTokenParam(storeCredentials.apiToken)
			)
		}
		return makeRequest(httpRequest, requestInfo.httpBody, V::class.java)
	}

	fun <V> makeRequest(httpRequest: HttpRequest, httpBody: HttpBody, clazz: Class<V>): V {
		val requestId = generateRequestId()

		logRequestIfNeeded(requestId, httpRequest, httpBody)

		val startTime = Date().time
		val httpResponse = httpTransport.makeHttpRequest(httpRequest)

		return processHttpResponse(
				httpResponse = httpResponse,
				clazz = clazz,
				requestId = requestId,
				requestTime = Date().time - startTime
		)
	}

	private fun <V> processHttpResponse(httpResponse: HttpResponse, clazz: Class<V>, requestId: String, requestTime: Long): V {
		val responseBytes = httpResponse.responseBytes
		return when (httpResponse) {
			is HttpResponse.Success -> {
				try {
					if (clazz.isAssignableFrom(String::class.java)) {
						val responseBody = responseBytes.asString()
						logSuccessfulResponseIfNeeded(requestId, requestTime, responseBody)
						@Suppress("UNCHECKED_CAST") // We already checked above that this cast is safe
						responseBody as V
					} else if (clazz.isAssignableFrom(ByteArray::class.java)) {
						logSuccessfulResponseIfNeeded(requestId, requestTime, "[Binary data: byte array of size ${responseBytes.size}]")
						@Suppress("UNCHECKED_CAST") // We already checked above that this cast is safe
						responseBytes as V
					} else {
						val responseBody = responseBytes.asString()
						logSuccessfulResponseIfNeeded(requestId, requestTime, responseBody)
						jsonTransformer.deserialize(responseBytes.asString(), clazz)!!
					}
				} catch (e: JsonDeserializationException) {
					logCannotParseResponseError(requestId, requestTime, responseBytes.asString(), e)
					throw EcwidApiException(
							message = e.message,
							cause = e
					)
				}
			}
			is HttpResponse.Error -> {
				try {
					val responseBody = responseBytes.asString()
					logErrorResponseIfNeeded(requestId, requestTime, httpResponse.statusCode, responseBody)
					val ecwidError = jsonTransformer.deserialize(responseBody, EcwidApiError::class.java)
					throw EcwidApiException(
							statusCode = httpResponse.statusCode,
							reasonPhrase = httpResponse.reasonPhrase,
							code = ecwidError?.errorCode,
							message = ecwidError?.errorMessage
					)
				} catch (e: JsonDeserializationException) {
					throw EcwidApiException(
							statusCode = httpResponse.statusCode,
							reasonPhrase = httpResponse.reasonPhrase,
							message = e.message,
							cause = e
					)
				}
			}
			is HttpResponse.TransportError -> {
				logTransportErrorResponseIfNeeded(requestId, requestTime, httpResponse.exception.message, httpResponse.exception)
				throw EcwidApiException(
						message = httpResponse.exception.message,
						cause = httpResponse.exception
				)
			}
		}
	}

	private fun logRequestIfNeeded(requestId: String, httpRequest: HttpRequest, httpBody: HttpBody) {
		if (!loggingSettings.logRequest) return

		val params = if (loggingSettings.maskRequestApiToken) {
			httpRequest.params.withMaskedApiTokenParam()
		} else {
			httpRequest.params
		}

		logEntry(
				prefix = "Request",
				logLevel = Level.INFO,
				requestId = requestId,
				sections = mutableListOf<String>().apply {
					add("${httpRequest.method} ${httpRequest.uri}")
					add(params.dumpToString())
					if (loggingSettings.logRequestBody) {
						httpBody.asString()?.let { add(it) }
					}
				}
		)
	}

	private fun createApiEndpointUri(endpoint: String): String {
		return URI(
				"https",
				null,
				apiServerDomain.host,
				if (apiServerDomain.securePort == DEFAULT_HTTPS_PORT) -1 else apiServerDomain.securePort,
				"/api/v3/${storeCredentials.storeId}/$endpoint",
				null,
				null
		).toString()
	}

	private fun logSuccessfulResponseIfNeeded(requestId: String, requestTime: Long, responseBody: String) {
		if (!loggingSettings.logResponse) return
		logEntry(
				prefix = "Response",
				logLevel = Level.INFO,
				requestId = requestId,
				sections = mutableListOf<String>().apply {
					add("OK")
					add("$requestTime ms")
					if (loggingSettings.logSuccessfulResponseBody) {
						add(responseBody)
					}
				}
		)
	}

	private fun logErrorResponseIfNeeded(requestId: String, requestTime: Long, httpStatusCode: Int, responseBody: String) {
		if (!loggingSettings.logResponse) return
		logEntry(
				prefix = "Response",
				logLevel = Level.INFO,
				requestId = requestId,
				sections = mutableListOf<String>().apply {
					add("ERR $httpStatusCode")
					add("$requestTime ms")
					if (loggingSettings.logFailedResponseBody) {
						add(responseBody)
					}
				}
		)
	}

	private fun logTransportErrorResponseIfNeeded(requestId: String, requestTime: Long, errorMessage: String?, exception: Exception) {
		if (!loggingSettings.logResponse) return
		logEntry(
				prefix = "Response",
				logLevel = Level.WARNING,
				requestId = requestId,
				sections = mutableListOf<String>().apply {
					add("ERR")
					add("$requestTime ms")
					if (errorMessage != null) {
						add(errorMessage)
					}
				},
				exception = exception
		)
	}

	private fun logCannotParseResponseError(requestId: String, requestTime: Long, responseBody: String, exception: Exception) {
		if (!loggingSettings.logResponse) return
		logEntry(
				prefix = "Response",
				logLevel = Level.WARNING,
				requestId = requestId,
				sections = mutableListOf<String>().apply {
					add("ERR")
					add("$requestTime ms")
					if (loggingSettings.logFailedResponseBody) {
						add(responseBody)
					}
				},
				exception = exception
		)
	}

	private fun logEntry(prefix: String, logLevel: Level?, requestId: String, sections: List<String>, exception: Exception? = null) {
		val logMessage = "$prefix [$requestId]: " + sections.joinToString(separator = "; ")
		if (exception != null) {
			log.log(logLevel, logMessage, exception)
		} else {
			log.log(logLevel, logMessage)
		}
	}

}

private fun generateRequestId(): String {
	val characters = ('a'..'z') + ('A'..'Z') + ('0'..'9')
	return (1..8)
			.map { Random.nextInt(0, characters.size) }
			.map(characters::get)
			.joinToString("");
}

private fun Map<String, String>.withApiTokenParam(apiToken: String): Map<String, String> {
	return toMutableMap()
			.apply {
				put(API_TOKEN_PARAM_NAME, apiToken)
			}
			.toMap()
}

private fun Map<String, String>.withMaskedApiTokenParam(): Map<String, String> {
	return toMutableMap()
			.apply {
				val apiTokenParam = get(API_TOKEN_PARAM_NAME)
				if (apiTokenParam != null) {
					put(API_TOKEN_PARAM_NAME, maskApiToken(apiTokenParam))
				}
			}
			.toMap()
}

private fun Map<String, String>.dumpToString(): String {
	return this
			.map { (key, value) -> "$key=$value" }
			.joinToString(separator = ", ")
}

private fun ByteArray.asString() = String(this, Charsets.UTF_8)

private fun HttpBody.prepare(jsonTransformer: GsonJsonTransformer): TransportHttpBody {
	return when (this) {
		HttpBody.EmptyBody -> TransportHttpBody.EmptyBody
		is HttpBody.JsonBody -> {
			val bodyAsBytes = jsonTransformer.serialize(obj).toByteArray()
			TransportHttpBody.InputStreamBody(ByteArrayInputStream(bodyAsBytes), MIME_TYPE_APPLICATION_JSON)
		}
		is HttpBody.ByteArrayBody -> {
			TransportHttpBody.InputStreamBody(ByteArrayInputStream(bytes), mimeType)
		}
		is HttpBody.InputStreamBody -> {
			TransportHttpBody.InputStreamBody(stream, mimeType)
		}
		is HttpBody.LocalFileBody -> {
			TransportHttpBody.InputStreamBody(FileInputStream(file), mimeType)
		}
	}
}