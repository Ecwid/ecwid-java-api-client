package com.ecwid.apiclient.v3

import com.ecwid.apiclient.v3.config.ApiServerDomain
import com.ecwid.apiclient.v3.config.ApiStoreCredentials
import com.ecwid.apiclient.v3.config.DEFAULT_HTTPS_PORT
import com.ecwid.apiclient.v3.config.LoggingSettings
import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.dto.common.EcwidApiError
import com.ecwid.apiclient.v3.dto.product.result.FetchedProduct.ProductOption
import com.ecwid.apiclient.v3.exception.EcwidApiException
import com.ecwid.apiclient.v3.exception.JsonDeserializationException
import com.ecwid.apiclient.v3.httptransport.*
import com.ecwid.apiclient.v3.impl.*
import com.ecwid.apiclient.v3.impl.MIME_TYPE_APPLICATION_JSON
import com.ecwid.apiclient.v3.util.maskApiToken
import com.ecwid.apiclient.v3.jsontransformer.JsonTransformer
import com.ecwid.apiclient.v3.jsontransformer.JsonTransformerProvider
import com.ecwid.apiclient.v3.jsontransformer.PolymorphicType
import com.ecwid.apiclient.v3.util.buildEndpointPath
import java.io.ByteArrayInputStream
import java.io.FileInputStream
import java.net.URI
import java.util.*
import java.util.logging.Level
import java.util.logging.Logger
import kotlin.random.Random

private const val API_TOKEN_PARAM_NAME = "token"

class ApiClientHelper private constructor(
		val apiServerDomain: ApiServerDomain,
		val storeCredentials: ApiStoreCredentials,
		val loggingSettings: LoggingSettings,
		val httpTransport: HttpTransport,
		val jsonTransformer: JsonTransformer
) {

	private val log = Logger.getLogger(this::class.qualifiedName)

	constructor(
			apiServerDomain: ApiServerDomain,
			storeCredentials: ApiStoreCredentials,
			loggingSettings: LoggingSettings,
			httpTransport: HttpTransport,
			jsonTransformerProvider: JsonTransformerProvider
	): this(
			apiServerDomain = apiServerDomain,
			storeCredentials = storeCredentials,
			loggingSettings = loggingSettings,
			httpTransport = httpTransport,
			jsonTransformer = jsonTransformerProvider.build(listOf(createProductOptionsPolymorphicType()))
	)

	@PublishedApi
	internal inline fun <reified V> makeRequestInt(request: ApiRequest, responseParser: ResponseParser<V>): V {
		val requestId = generateRequestId()

		val requestInfo = request.toRequestInfo()
		val httpRequest = requestInfo.toHttpRequest()
		logRequestIfNeeded(requestId, httpRequest, requestInfo.httpBody)

		val startTime = Date().time
		val httpResponse = httpTransport.makeHttpRequest(httpRequest)

		return processHttpResponse(
				httpResponse = httpResponse,
				requestId = requestId,
				requestTime = Date().time - startTime,
				responseParser = responseParser
		)
	}

	inline fun <reified V> makeObjectResultRequest(request: ApiRequest): V {
		return makeRequestInt(request, ObjectResponseParser(jsonTransformer, V::class.java))
	}

	inline fun <reified VBase, reified VExt> makeObjectWithExtResultRequest(request: ApiRequest): ParsedResponseWithExt<VBase, VExt> {
		return makeRequestInt(request, ObjectWithExtResponseParser(jsonTransformer, VBase::class.java, VExt::class.java))
	}

	@Suppress("NOTHING_TO_INLINE")
	inline fun makeByteArrayResultRequest(request: ApiRequest): ByteArray {
		return makeRequestInt(request, ByteArrayResponseParser())
	}

	@Suppress("NOTHING_TO_INLINE")
	inline fun makeStringResultRequest(request: ApiRequest): String {
		return makeRequestInt(request, StringResponseParser())
	}

	@PublishedApi
	internal fun <V> processHttpResponse(httpResponse: HttpResponse, requestId: String, requestTime: Long, responseParser: ResponseParser<V>): V {
		val responseBytes = httpResponse.responseBytes
		return when (httpResponse) {
			is HttpResponse.Success -> {
				try {
					val parsedResponse = responseParser.parse(responseBytes)
					val responseBodyForLog = responseParser.getLogString(responseBytes)
					logSuccessfulResponseIfNeeded(requestId, requestTime, responseBodyForLog)
					parsedResponse
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

	@PublishedApi
	internal fun logRequestIfNeeded(requestId: String, httpRequest: HttpRequest, httpBody: HttpBody) {
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

	@PublishedApi
	internal fun RequestInfo.toHttpRequest(): HttpRequest = when (method) {
		HttpMethod.GET -> HttpRequest.HttpGetRequest(
				uri = createApiEndpointUri(pathSegments),
				params = params.withApiTokenParam(storeCredentials.apiToken)
		)
		HttpMethod.POST -> HttpRequest.HttpPostRequest(
				uri = createApiEndpointUri(pathSegments),
				params = params.withApiTokenParam(storeCredentials.apiToken),
				transportHttpBody = httpBody.prepare(jsonTransformer)
		)
		HttpMethod.PUT -> HttpRequest.HttpPutRequest(
				uri = createApiEndpointUri(pathSegments),
				params = params.withApiTokenParam(storeCredentials.apiToken),
				transportHttpBody = httpBody.prepare(jsonTransformer)
		)
		HttpMethod.DELETE -> HttpRequest.HttpDeleteRequest(
				uri = createApiEndpointUri(pathSegments),
				params = params.withApiTokenParam(storeCredentials.apiToken)
		)
	}

	@PublishedApi
	internal fun createApiEndpointUri(pathSegments: List<String>): String {
		val endpoint = buildEndpointPath(pathSegments)
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

@PublishedApi
internal fun generateRequestId(): String {
	val characters = ('a'..'z') + ('A'..'Z') + ('0'..'9')
	return (1..8)
			.map { Random.nextInt(0, characters.size) }
			.map(characters::get)
			.joinToString("")
}

@PublishedApi
internal fun Map<String, String>.withApiTokenParam(apiToken: String): Map<String, String> {
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

fun ByteArray.asString() = String(this, Charsets.UTF_8)

@PublishedApi
internal fun HttpBody.prepare(jsonTransformer: JsonTransformer): TransportHttpBody {
	return when (this) {
		HttpBody.EmptyBody -> TransportHttpBody.EmptyBody
		is HttpBody.JsonBody -> {
			val bodyAsBytes = jsonTransformer.serialize(obj, objExt).toByteArray()
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

private fun createProductOptionsPolymorphicType(): PolymorphicType<ProductOption> {
	return PolymorphicType(
			rootClass = ProductOption::class.java,
			jsonFieldName = "type",
			childClasses = mapOf(
					"select" to ProductOption.SelectOption::class.java,
					"size" to ProductOption.SizeOption::class.java,
					"radio" to ProductOption.RadioOption::class.java,
					"checkbox" to ProductOption.CheckboxOption::class.java,
					"textfield" to ProductOption.TextFieldOption::class.java,
					"textarea" to ProductOption.TextAreaOption::class.java,
					"date" to ProductOption.DateOption::class.java,
					"files" to ProductOption.FilesOption::class.java
			)
	)
}
