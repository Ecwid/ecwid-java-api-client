package com.ecwid.apiclient.v3.dto.sluginfo

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.impl.RequestInfo
import com.ecwid.apiclient.v3.responsefields.ResponseFields

data class SlugInfoRequest(
	val storeRootPage: Boolean = false,
	val slug: String = "",
	val getStaticContent: Boolean = false,
	val degeneratorParams: Map<String, Any>? = null,
	val responseFields: ResponseFields = ResponseFields.All
) : ApiRequest {

	override fun toRequestInfo(): RequestInfo = RequestInfo.createGetRequest(
		pathSegments = listOf(
			"storefront-widget-pages"
		),
		params = toParams(),
		responseFields = responseFields,
	)


	private fun toParams(): Map<String, String> {
		val request = this
		return mutableMapOf<String, String>().apply {
			put("storeRootPage", storeRootPage.toString())
			put("getStaticContent", getStaticContent.toString())
			put("slug", slug)
			request.degeneratorParams?.let {
				request.degeneratorParams.forEach { put(it.key, it.value.toString()) }
			}
		}.toMap()
	}
}


