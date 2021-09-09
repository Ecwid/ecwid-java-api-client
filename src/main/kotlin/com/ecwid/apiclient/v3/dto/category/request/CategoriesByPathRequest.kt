package com.ecwid.apiclient.v3.dto.category.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.impl.RequestInfo

/**
 * Weird delimiter to ensure it never occurs in category names.
 */
private const val DELIMITER = "Ω≈ç√∫˜˜≤≥çæ…¬˚∆˙"

data class CategoriesByPathRequest(
	/**
	 * Full category path with delimiter
	 */
	val path: String = "",
	val delimiter: String = "",
	val offset: Int = 0,
	val limit: Int = 100,
	val lang: String? = null
) : ApiRequest {

	constructor(
		/**
		 * List of category path elements
		 */
		pathElements: List<String>,
		offset: Int,
		limit: Int,
		lang: String?
	) : this(
		path = pathElements.joinToString(DELIMITER),
		delimiter = DELIMITER,
		offset = offset,
		limit = limit,
		lang = lang
	)

	override fun toRequestInfo() = RequestInfo.createGetRequest(
		pathSegments = listOf(
			"categoriesByPath"
		),
		params = toParams()
	)

	private fun toParams(): Map<String, String> {
		val request = this

		return mutableMapOf<String, String>().apply {
			put("path", path)
			put("delimiter", delimiter)
			put("offset", request.offset.toString())
			put("limit", request.limit.toString())
			request.lang?.let { put("lang", it) }
		}.toMap()
	}
}
