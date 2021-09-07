package com.ecwid.apiclient.v3.dto.category.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.impl.RequestInfo

/**
 * path - List of category path elements
 */
data class CategoriesByPathRequest(
	val path: List<String> = listOf(),
	val offset: Int = 0,
	val limit: Int = 100,
	val lang: String? = null
) : ApiRequest {
	override fun toRequestInfo() = RequestInfo.createGetRequest(
		pathSegments = listOf(
			"categoriesByPath"
		),
		params = toParams()
	)

	private fun toParams(): Map<String, String> {
		val request = this
		val delimiterLength = 6
		val randomDelimiter = "{" + generateRandomUID(delimiterLength) + "}"
		val generatedPath = path.joinToString(randomDelimiter)

		return mutableMapOf<String, String>().apply {
			put("path", generatedPath)
			put("delimiter", randomDelimiter)
			put("offset", request.offset.toString())
			put("limit", request.limit.toString())
			request.lang?.let { put("lang", it) }
		}.toMap()
	}
}

private fun generateRandomUID(length: Int): String {
	val alphabet = "0123456789abcdefghijkmnopqrstuvwxyzABCDEFGHJKLMNPQRSTUVWXYZ"
	return (1..length).joinToString("") { alphabet.random().toString() }
}
