package com.ecwid.apiclient.v3.impl

import com.ecwid.apiclient.v3.config.ApiServerDomain
import com.ecwid.apiclient.v3.config.ApiStoreCredentials
import com.ecwid.apiclient.v3.config.DEFAULT_HTTPS_PORT
import com.ecwid.apiclient.v3.config.LoggingSettings
import com.ecwid.apiclient.v3.dto.EcwidApiError
import com.ecwid.apiclient.v3.exception.EcwidApiException
import com.ecwid.apiclient.v3.exception.JsonDeserializationException
import com.ecwid.apiclient.v3.httptransport.HttpBody
import com.ecwid.apiclient.v3.httptransport.HttpRequest
import com.ecwid.apiclient.v3.httptransport.HttpResponse
import com.ecwid.apiclient.v3.httptransport.HttpTransport
import com.ecwid.apiclient.v3.httptransport.impl.ApacheCommonsHttpClientTransport
import com.ecwid.apiclient.v3.jsontransformer.JsonTransformer
import com.ecwid.apiclient.v3.jsontransformer.impl.GsonJsonTransformer
import java.net.URI
import java.util.*
import java.util.logging.Level
import java.util.logging.Logger
import kotlin.random.Random


private const val API_TOKEN_PARAM_NAME = "token"

internal class ApiClientHelper(
		private val apiServerDomain: ApiServerDomain,
		private val storeCredentials: ApiStoreCredentials,
		private val loggingSettings: LoggingSettings
) {

	private val log = Logger.getLogger(this::class.java.name)

	private val httpTransport: HttpTransport = ApacheCommonsHttpClientTransport()
	private val jsonTransformer: JsonTransformer = GsonJsonTransformer()

	inline fun <reified V> makeGetRequest(
			endpoint: String,
			params: Map<String, String>
	): V {
		val httpRequest = HttpRequest.HttpGetRequest(
				uri = createApiEndpointUri(endpoint),
				params = params.withApiTokenParam(storeCredentials.apiToken)
		)
		return makeRequest(httpRequest, V::class.java)
	}

	inline fun <reified V> makePostRequest(
			endpoint: String,
			params: Map<String, String>,
			httpBody: HttpBody
	): V {
		val httpRequest = HttpRequest.HttpPostRequest(
				uri = createApiEndpointUri(endpoint),
				params = params.withApiTokenParam(storeCredentials.apiToken),
				httpBody = httpBody
		)
		return makeRequest(httpRequest, V::class.java)
	}

	inline fun <reified V> makePutRequest(
			endpoint: String,
			params: Map<String, String>,
			httpBody: HttpBody
	): V {
		val httpRequest = HttpRequest.HttpPutRequest(
				uri = createApiEndpointUri(endpoint),
				params = params.withApiTokenParam(storeCredentials.apiToken),
				httpBody = httpBody
		)
		return makeRequest(httpRequest, V::class.java)
	}

	inline fun <reified V> makeDeleteRequest(
			endpoint: String,
			params: Map<String, String>
	): V {
		val httpRequest = HttpRequest.HttpDeleteRequest(
				uri = createApiEndpointUri(endpoint),
				params = params.withApiTokenParam(storeCredentials.apiToken)
		)
		return makeRequest(httpRequest, V::class.java)
	}

	fun <V> makeRequest(httpRequest: HttpRequest, clazz: Class<V>): V {
		val requestId = generateRequestId()

		logRequestIfNeeded(requestId, httpRequest)

		val startTime = Date().time
		val httpResponse = httpTransport.makeHttpRequest(httpRequest)

		val requestTime = Date().time - startTime
		val responseBytes = httpResponse.responseBytes
		val responseBody = lazy { responseBytes.asString() }
		return processHttpResponse(httpResponse, clazz, requestId, requestTime, responseBody, responseBytes)
	}

	private fun <V> processHttpResponse(httpResponse: HttpResponse, clazz: Class<V>, requestId: String, requestTime: Long, responseBody: Lazy<String>, responseBytes: ByteArray): V {
		return when (httpResponse) {
			is HttpResponse.Success -> {
				try {
					if (clazz.isAssignableFrom(String::class.java)) {
						logSuccessfulResponseIfNeeded(requestId, requestTime, responseBody.value)
						@Suppress("UNCHECKED_CAST") // We already checked above that this cast is safe
						responseBody.value as V
					} else if (clazz.isAssignableFrom(ByteArray::class.java)) {
						logSuccessfulResponseIfNeeded(requestId, requestTime, "[Binary data: byte array of size ${responseBytes.size}]")
						@Suppress("UNCHECKED_CAST") // We already checked above that this cast is safe
						responseBytes as V
					} else {
						logSuccessfulResponseIfNeeded(requestId, requestTime, responseBody.value)
						deserializeJson(responseBytes.asString(), clazz)!!
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
					logErrorResponseIfNeeded(requestId, requestTime, httpResponse.statusCode, responseBody.value)
					val ecwidError = deserializeJson(responseBody.value, EcwidApiError::class.java)
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

	private fun logRequestIfNeeded(requestId: String, httpRequest: HttpRequest) {
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
						httpRequest.httpBody.asString()?.let { add(it) }
					}
				}
		)
	}

	fun serializeJson(src: Any?): String = jsonTransformer.serialize(src)

	fun <T> deserializeJson(src: String, clazz: Class<T>): T = jsonTransformer.deserialize(src, clazz)!!

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
