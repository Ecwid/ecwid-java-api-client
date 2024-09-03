package com.ecwid.apiclient.v3.httptransport.impl

import org.apache.http.Header
import org.apache.http.HttpStatus
import org.apache.http.client.methods.HttpGet
import org.apache.http.client.methods.HttpHead
import org.apache.http.client.methods.HttpUriRequest
import org.apache.http.client.methods.RequestBuilder
import org.apache.http.impl.client.DefaultRedirectStrategy
import org.apache.http.protocol.HttpContext

/**
 * List of headers that can be passed to a redirect location.
 * We should NOT expose any custom headers and Authorization header to external sources
 */
private val allowedHeaders = setOf(
	"Accept",
	"Accept-Charset",
	"Accept-Encoding",
	"Accept-Language",
	"Access-Control-Request-Method",
	"Access-Control-Request-Headers",
	"Cache-Control",
	"Connection",
	"Content-Encoding",
	"Content-Length",
	"Content-Type",
	"Date",
	"Host",
	"HTTP2-Settings",
	"If-Match",
	"If-Modified-Since",
	"If-None-Match",
	"If-Unmodified-Since",
	"Origin",
	"Referer",
	"User-Agent",
	"X-Forwarded-For",
	"X-Forwarded-Host",
	"X-Forwarded-Proto"
)

class RemoveDisallowedHeadersRedirectStrategy : DefaultRedirectStrategy() {
	override fun getRedirect(
		request: org.apache.http.HttpRequest,
		response: org.apache.http.HttpResponse,
		context: HttpContext?
	): HttpUriRequest {
		val uri = getLocationURI(request, response, context)
		val method = request.requestLine.method
		return if (method.equals(HttpHead.METHOD_NAME, ignoreCase = true)) {
			object : HttpHead(uri) {
				override fun setHeaders(headers: Array<out Header>) {
					super.setHeaders(headers.filter { it.name in allowedHeaders }.toTypedArray())
				}
			}
		} else {
			val httpGet = object : HttpGet(uri) {
				override fun setHeaders(headers: Array<out Header>) {
					super.setHeaders(headers.filter { it.name in allowedHeaders }.toTypedArray())
				}
			}
			if (method.equals(HttpGet.METHOD_NAME, ignoreCase = true)) {
				httpGet
			} else {
				val status = response.statusLine.statusCode
				if (status == HttpStatus.SC_TEMPORARY_REDIRECT || status == SC_PERMANENT_REDIRECT) {
					RequestBuilder.copy(request).setUri(uri).build()
				} else {
					httpGet
				}
			}
		}
	}
}
