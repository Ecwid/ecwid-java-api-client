package com.ecwid.apiclient.v3.httptransport.impl.client5

import org.apache.hc.client5.http.impl.DefaultRedirectStrategy
import org.apache.hc.core5.http.HttpHost
import org.apache.hc.core5.http.HttpRequest
import org.apache.hc.core5.http.protocol.HttpContext


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

	@Suppress("SpreadOperator")
	override fun isRedirectAllowed(
		currentTarget: HttpHost?,
		newTarget: HttpHost?,
		redirect: HttpRequest?,
		context: HttpContext?
	): Boolean {
		redirect?.headers?.filter { it.name in allowedHeaders }?.toTypedArray()?.also {
			redirect.setHeaders(*it)
		}
		return true
	}
}
