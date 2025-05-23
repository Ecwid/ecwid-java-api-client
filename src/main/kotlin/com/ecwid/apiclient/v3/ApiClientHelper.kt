package com.ecwid.apiclient.v3

import com.ecwid.apiclient.v3.config.*
import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.dto.common.EcwidApiError
import com.ecwid.apiclient.v3.dto.common.PartialResult
import com.ecwid.apiclient.v3.dto.product.result.FetchedProduct.ProductOption
import com.ecwid.apiclient.v3.dto.report.result.FetchedReportResponse
import com.ecwid.apiclient.v3.dto.report.result.FetchedReportResponse.FetchedAdditionalData.*
import com.ecwid.apiclient.v3.dto.report.result.FetchedReportResponse.FetchedAdditionalData.AdditionalDataType.*
import com.ecwid.apiclient.v3.exception.EcwidApiException
import com.ecwid.apiclient.v3.exception.JsonDeserializationException
import com.ecwid.apiclient.v3.httptransport.*
import com.ecwid.apiclient.v3.impl.*
import com.ecwid.apiclient.v3.jsontransformer.JsonTransformer
import com.ecwid.apiclient.v3.jsontransformer.JsonTransformerProvider
import com.ecwid.apiclient.v3.jsontransformer.PolymorphicType
import com.ecwid.apiclient.v3.metric.RequestSizeMetric
import com.ecwid.apiclient.v3.metric.RequestTimeMetric
import com.ecwid.apiclient.v3.metric.ResponseSizeMetric
import com.ecwid.apiclient.v3.responsefields.ResponseFields
import com.ecwid.apiclient.v3.responsefields.responseFieldsOf
import com.ecwid.apiclient.v3.util.buildEndpointPath
import com.ecwid.apiclient.v3.util.createSecurePatterns
import com.ecwid.apiclient.v3.util.maskLogString
import java.net.URI
import java.util.*
import java.util.logging.Level
import java.util.logging.Logger
import kotlin.random.Random
import kotlin.reflect.KClass

private const val APP_CLIENT_ID_HEADER_NAME = "X-Ecwid-App-Client-Id"
private const val APP_CLIENT_SECRET_HEADER_NAME = "X-Ecwid-App-Secret-Key"
private const val RESPONSE_FIELDS_PARAM_NAME = "responseFields"
private const val REQUEST_ID_HEADER_NAME = "X-Ecwid-Api-Request-Id"

private const val REQUEST_ID_LENGTH = 8
private val REQUEST_ID_CHARACTERS = ('a'..'z') + ('A'..'Z') + ('0'..'9')

class ApiClientHelper private constructor(
	private val apiServerDomain: ApiServerDomain,
	private val credentials: ApiCredentials?,
	private val loggingSettings: LoggingSettings,
	val httpTransport: HttpTransport,
	val jsonTransformer: JsonTransformer,
	private val requestKind: RequestKind? = null,
) {

	private val log = Logger.getLogger(this::class.qualifiedName)

	constructor(
		apiServerDomain: ApiServerDomain,
		storeCredentials: ApiStoreCredentials? = null,
		loggingSettings: LoggingSettings,
		httpTransport: HttpTransport,
		jsonTransformerProvider: JsonTransformerProvider
	) : this(
		apiServerDomain = apiServerDomain,
		credentials = storeCredentials,
		loggingSettings = loggingSettings,
		httpTransport = httpTransport,
		jsonTransformer = jsonTransformerProvider.build(createPolymorphicTypeList())
	)

	constructor(
		apiServerDomain: ApiServerDomain,
		credentials: ApiCredentials? = null,
		loggingSettings: LoggingSettings,
		httpTransport: HttpTransport,
		jsonTransformerProvider: JsonTransformerProvider
	) : this(
		apiServerDomain = apiServerDomain,
		credentials = credentials,
		loggingSettings = loggingSettings,
		httpTransport = httpTransport,
		jsonTransformer = jsonTransformerProvider.build(createPolymorphicTypeList())
	)

	constructor(
		apiServerDomain: ApiServerDomain,
		credentials: ApiCredentials? = null,
		loggingSettings: LoggingSettings,
		httpTransport: HttpTransport,
		jsonTransformerProvider: JsonTransformerProvider,
		requestKind: RequestKind?,
	) : this(
		apiServerDomain = apiServerDomain,
		credentials = credentials,
		loggingSettings = loggingSettings,
		httpTransport = httpTransport,
		jsonTransformer = jsonTransformerProvider.build(createPolymorphicTypeList()),
		requestKind = requestKind,
	)

	@PublishedApi
	internal fun <V> makeRequestInt(request: ApiRequest, responseParser: ResponseParser<V>, responseFieldsOverride: ResponseFields? = null): V {
		val requestId = generateRequestId()

		val requestInfo = request.toRequestInfo()
		val originalHttpRequest = requestInfo.toHttpRequest(requestId, responseFieldsOverride)
		val (httpRequest, requestSizeCounter) = RequestSizeMetric.makeHttpRequestCountable(originalHttpRequest)
		logRequestIfNeeded(requestId, httpRequest, requestInfo.httpBody)

		val startTime = Date().time
		val httpResponse = httpTransport.makeHttpRequest(httpRequest)
		val requestTimeMs = Date().time - startTime

		RequestTimeMetric.observeRequest(
			apiRequest = request,
			requestInfo = requestInfo,
			requestTimeMs = requestTimeMs,
			httpResponse = httpResponse,
		)
		RequestSizeMetric.observeRequest(
			apiRequest = request,
			requestInfo = requestInfo,
			size = requestSizeCounter,
			httpResponse = httpResponse,
		)
		ResponseSizeMetric.observeResponse(
			apiRequest = request,
			requestInfo = requestInfo,
			httpResponse = httpResponse,
		)

		return processHttpResponse(
			httpResponse = httpResponse,
			requestId = requestId,
			requestTime = requestTimeMs,
			responseParser = responseParser
		)
	}

	inline fun <reified V> makeObjectResultRequest(request: ApiRequest): V {
		return makeRequestInt(request, ObjectResponseParser(jsonTransformer, V::class.java))
	}

	fun <V : PartialResult<*>> makeObjectPartialResultRequest(request: ApiRequest, resultClass: KClass<V>): V {
		return makeRequestInt(
			request = request,
			responseParser = ObjectResponseParser(jsonTransformer, resultClass.java),
			responseFieldsOverride = responseFieldsOf(resultClass)
		)
	}

	@Suppress("UNCHECKED_CAST")
	fun <V : PartialResult<*>> makeObjectPartialResultRequestList(request: ApiRequest, resultClass: KClass<V>): List<V> {
		return makeRequestInt(
			request = request,
			responseParser = ObjectResponseParser(jsonTransformer, resultClass.java.arrayType() as Class<Array<V>>),
			responseFieldsOverride = responseFieldsOf(resultClass)
		).toList()
	}

	@Suppress("unused")
	fun <VBase : PartialResult<*>, VExt : PartialResult<*>> makeObjectPartialResultWithExtRequest(
		request: ApiRequest,
		baseResultClass: KClass<VBase>,
		extResultClass: KClass<VExt>,
	): ParsedResponseWithExt<VBase, VExt> {
		return makeRequestInt(
			request = request,
			responseParser = ObjectWithExtResponseParser(jsonTransformer, baseResultClass.java, extResultClass.java),
			responseFieldsOverride = responseFieldsOf(baseResultClass) + responseFieldsOf(extResultClass),
		)
	}

	@Suppress("unused")
	inline fun <reified VBase, reified VExt> makeObjectWithExtResultRequest(request: ApiRequest): ParsedResponseWithExt<VBase, VExt> {
		return makeRequestInt(
			request,
			ObjectWithExtResponseParser(jsonTransformer, VBase::class.java, VExt::class.java)
		)
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
	internal fun <V> processHttpResponse(
		httpResponse: HttpResponse,
		requestId: String,
		requestTime: Long,
		responseParser: ResponseParser<V>
	): V {
		val responseBytes = httpResponse.responseBytes
		return when (httpResponse) {
			is HttpResponse.Success -> {
				try {
					val parsedResponse = responseParser.parse(responseBytes)
					val getResponseBodyForLog = { responseParser.getLogString(responseBytes) }
					logSuccessfulResponseIfNeeded(requestId, requestTime, getResponseBodyForLog)
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
					val ecwidError = if (responseBody.isNotBlank()) {
						jsonTransformer.deserializeOrNull(responseBody, EcwidApiError::class.java)
					} else {
						null
					}
					throw EcwidApiException(
						statusCode = httpResponse.statusCode,
						reasonPhrase = httpResponse.reasonPhrase,
						code = ecwidError?.errorCode,
						message = ecwidError?.errorMessage,
						args = ecwidError?.args,
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
				logTransportErrorResponseIfNeeded(
					requestId,
					requestTime,
					httpResponse.exception.message,
					httpResponse.exception
				)
				throw EcwidApiException(
					message = httpResponse.exception.message,
					cause = httpResponse.exception
				)
			}
		}
	}

	@PublishedApi
	internal fun logRequestIfNeeded(requestId: String, httpRequest: HttpRequest, httpBody: HttpBody) {
		if (!loggingSettings.logRequest) {
			return
		}

		val securePatterns = createSecurePatterns()
		logEntry(
			prefix = "Request",
			logLevel = Level.INFO,
			requestId = requestId,
			maxSectionLength = loggingSettings.maxLogSectionLength,
			sections = mutableListOf<String>().apply {
				add("${httpRequest.method} ${httpRequest.uri}")
				if (loggingSettings.logRequestParams) {
					add(httpRequest.params.dumpToString().maskLogString(securePatterns))
				}
				if (loggingSettings.logRequestBody) {
					httpBody.asString()?.maskLogString(securePatterns)?.let(::add)
				}
			},
		)
	}

	@PublishedApi
	internal fun RequestInfo.toHttpRequest(requestId: String, responseFieldsOverride: ResponseFields?): HttpRequest {
		val uri = createApiEndpointUri(pathSegments)
		val params = if (responseFieldsOverride != null) params.withResponseFieldsParam(responseFieldsOverride) else params
		val headers = headers.withRequestId(requestId).let {
			if (requestKind != null) {
				it.withRequestKind(requestKind)
			} else {
				if (credentials != null) {
					it.withCredentials(credentials)
				} else {
					it
				}
			}
		}
		return when (method) {
			HttpMethod.GET -> HttpRequest.HttpGetRequest(
				uri = uri,
				params = params,
				headers = headers,
			)

			HttpMethod.POST -> HttpRequest.HttpPostRequest(
				uri = uri,
				params = params,
				transportHttpBody = httpBody.prepare(jsonTransformer),
				headers = headers,
			)

			HttpMethod.PUT -> HttpRequest.HttpPutRequest(
				uri = uri,
				params = params,
				transportHttpBody = httpBody.prepare(jsonTransformer),
				headers = headers,
			)

			HttpMethod.DELETE -> HttpRequest.HttpDeleteRequest(
				uri = uri,
				params = params,
				headers = headers,
			)
		}
	}

	@PublishedApi
	internal fun createApiEndpointUri(pathSegments: List<String>): String {
		val uri = URI(
			"https",
			null,
			apiServerDomain.host,
			if (apiServerDomain.securePort == DEFAULT_HTTPS_PORT) -1 else apiServerDomain.securePort,
			null,
			null,
			null
		)

		val encodedPath = if (requestKind != null) {
			requestKind.buildBaseEndpointPath() + "/" + buildEndpointPath(pathSegments)
		} else {
			credentials?.let { buildBaseEndpointPath(it) } + "/" + buildEndpointPath(pathSegments)
		}
		return uri.toString() + encodedPath
	}

	private fun logSuccessfulResponseIfNeeded(requestId: String, requestTime: Long, getResponseBody: () -> String) {
		if (!loggingSettings.logResponse) {
			return
		}

		val securePatterns = createSecurePatterns()
		logEntry(
			prefix = "Response",
			logLevel = Level.INFO,
			requestId = requestId,
			maxSectionLength = loggingSettings.maxLogSectionLength,
			sections = mutableListOf<String>().apply {
				add("OK")
				add("$requestTime ms")
				if (loggingSettings.logSuccessfulResponseBody) {
					add(getResponseBody.invoke().maskLogString(securePatterns))
				}
			}
		)
	}

	private fun logErrorResponseIfNeeded(
		requestId: String,
		requestTime: Long,
		httpStatusCode: Int,
		responseBody: String
	) {
		if (!loggingSettings.logResponse) {
			return
		}

		logEntry(
			prefix = "Response",
			logLevel = Level.INFO,
			requestId = requestId,
			maxSectionLength = loggingSettings.maxLogSectionLength,
			sections = mutableListOf<String>().apply {
				add("ERR $httpStatusCode")
				add("$requestTime ms")
				if (loggingSettings.logFailedResponseBody) {
					add(responseBody)
				}
			}
		)
	}

	private fun logTransportErrorResponseIfNeeded(
		requestId: String,
		requestTime: Long,
		errorMessage: String?,
		exception: Exception
	) {
		if (!loggingSettings.logResponse) {
			return
		}

		logEntry(
			prefix = "Response",
			logLevel = Level.WARNING,
			requestId = requestId,
			maxSectionLength = loggingSettings.maxLogSectionLength,
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

	private fun logCannotParseResponseError(
		requestId: String,
		requestTime: Long,
		responseBody: String,
		exception: Exception
	) {
		if (!loggingSettings.logResponse) {
			return
		}

		logEntry(
			prefix = "Response",
			logLevel = Level.WARNING,
			requestId = requestId,
			maxSectionLength = loggingSettings.maxLogSectionLength,
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

	private fun logEntry(
		prefix: String,
		logLevel: Level?,
		requestId: String,
		maxSectionLength: Int,
		sections: List<String>,
		exception: Exception? = null,
	) {
		val sectionsString = sections.joinToString(separator = "; ") {
			if (it.length > maxSectionLength) it.take(maxSectionLength) + "…" else it
		}
		val logMessage = "$prefix [$requestId]: $sectionsString"
		if (exception != null) {
			log.log(logLevel, logMessage, exception)
		} else {
			log.log(logLevel, logMessage)
		}
	}

	private fun buildBaseEndpointPath(credentials: ApiCredentials) = when (credentials) {
		is ApiStoreCredentials -> "/api/v3/${credentials.storeId}"
		is ApiAppCredentials -> "/api/v3"
	}
}

@PublishedApi
internal fun generateRequestId(): String {
	return (0 until REQUEST_ID_LENGTH)
		.map { Random.nextInt(0, REQUEST_ID_CHARACTERS.size) }
		.map(REQUEST_ID_CHARACTERS::get)
		.joinToString("")
}

@PublishedApi
internal fun Map<String, String>.withCredentials(credentials: ApiCredentials) = when (credentials) {
	is ApiStoreCredentials -> this.withApiTokenHeader(credentials.apiToken)
	is ApiAppCredentials -> withAppCredentialsHeaders(credentials)
}

@PublishedApi
internal fun Map<String, String>.withRequestKind(requestKind: RequestKind) = toMutableMap().apply {
	putAll(requestKind.buildHeaders())
}

internal fun Map<String, String>.withResponseFieldsParam(responseFields: ResponseFields): Map<String, String> {
	return if (responseFields.isAll()) {
		this - RESPONSE_FIELDS_PARAM_NAME
	} else {
		this + (RESPONSE_FIELDS_PARAM_NAME to responseFields.toHttpParameter())
	}
}

@PublishedApi
internal fun Map<String, String>.withApiTokenHeader(apiToken: String): Map<String, String> {
	return toMutableMap()
		.apply {
			put("Authorization", "Bearer $apiToken")
		}
		.toMap()
}

@PublishedApi
internal fun Map<String, String>.withAppCredentialsHeaders(appCredentials: ApiAppCredentials): Map<String, String> {
	return toMutableMap()
		.apply {
			put(APP_CLIENT_ID_HEADER_NAME, appCredentials.clientId)
			put(APP_CLIENT_SECRET_HEADER_NAME, appCredentials.clientSecret)
		}
		.toMap()
}

private fun Map<String, String>.withRequestId(requestId: String): Map<String, String> {
	return toMutableMap()
		.apply {
			put(REQUEST_ID_HEADER_NAME, requestId)
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
			TransportHttpBody.ByteArrayBody(bodyAsBytes, MIME_TYPE_APPLICATION_JSON)
		}

		is HttpBody.ByteArrayBody -> {
			TransportHttpBody.ByteArrayBody(bytes, mimeType)
		}

		is HttpBody.InputStreamBody -> {
			TransportHttpBody.InputStreamBody(stream, mimeType)
		}

		is HttpBody.LocalFileBody -> {
			TransportHttpBody.LocalFileBody(file, mimeType)
		}
	}
}

fun createPolymorphicTypeList(): List<PolymorphicType<*>> {
	return listOf(
		createProductOptionsPolymorphicType(),
		createAdditionalDataPolymorphicType()
	)
}

private fun createProductOptionsPolymorphicType(): PolymorphicType<ProductOption> {
	return PolymorphicType(
		rootClass = ProductOption::class.java,
		jsonFieldName = "type",
		childClasses = mapOf(
			"select" to ProductOption.SelectOption::class.java,
			"size" to ProductOption.SizeOption::class.java,
			"radio" to ProductOption.RadioOption::class.java,
			"swatches" to ProductOption.SwatchesOption::class.java,
			"checkbox" to ProductOption.CheckboxOption::class.java,
			"textfield" to ProductOption.TextFieldOption::class.java,
			"textarea" to ProductOption.TextAreaOption::class.java,
			"date" to ProductOption.DateOption::class.java,
			"files" to ProductOption.FilesOption::class.java
		)
	)
}

private fun createAdditionalDataPolymorphicType(): PolymorphicType<FetchedReportResponse.FetchedAdditionalData> {
	return PolymorphicType(
		rootClass = FetchedReportResponse.FetchedAdditionalData::class.java,
		jsonFieldName = "type",
		childClasses = mapOf(
			UTM.name.lowercase() to AdditionalUtmData::class.java,
			ORDERS.name.lowercase() to AdditionalOrdersData::class.java,
			CUSTOMERS.name.lowercase() to AdditionalCustomerData::class.java,
			INVENTORY_PRODUCT.name.lowercase() to AdditionalInventoryData::class.java,
			PRODUCT.name.lowercase() to AdditionalProductData::class.java,
			COUPONS.name.lowercase() to AdditionalCouponData::class.java,
			ABANDONED_CARTS.name.lowercase() to AdditionalAbandonedCartData::class.java,
			SHIPPING.name.lowercase() to AdditionalShippingData::class.java,
			LANDING.name.lowercase() to AdditionalLandingData::class.java,
			CATEGORY.name.lowercase() to AdditionalCategoryData::class.java,
		)
	)
}
