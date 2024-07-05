package com.ecwid.apiclient.v3.dto.productreview.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.dto.common.PagingRequest
import com.ecwid.apiclient.v3.dto.productreview.enums.ProductReviewSortOrder
import com.ecwid.apiclient.v3.dto.productreview.enums.ProductReviewStatus
import com.ecwid.apiclient.v3.impl.RequestInfo
import com.ecwid.apiclient.v3.responsefields.ResponseFields
import java.time.Instant
import java.util.concurrent.TimeUnit

data class ProductReviewSearchRequest(
	val reviewId: String? = null,
	val productId: String? = null,
	val orderId: String? = null,
	val status: ProductReviewStatus? = null,
	val rating: String? = null,
	val createdFrom: Instant? = null,
	val createdTo: Instant? = null,
	val updatedFrom: Instant? = null,
	val updatedTo: Instant? = null,
	val searchKeyword: String? = null,
	val sortBy: ProductReviewSortOrder? = null,
	val limit: Int = 100,
	override val offset: Int = 0,
	val responseFields: ResponseFields = ResponseFields.All,
) : ApiRequest, PagingRequest<ProductReviewSearchRequest> {
	override fun toRequestInfo() = RequestInfo.createGetRequest(
		pathSegments = listOf(
			"reviews"
		),
		params = toParams(),
		responseFields = responseFields,
	)

	private fun toParams(): Map<String, String> {
		val request = this
		return mutableMapOf<String, String>().apply {
			request.reviewId?.let { put("reviewId", it) }
			request.productId?.let { put("productId", it) }
			request.orderId?.let { put("orderId", it) }
			request.status?.let { put("status", it.toString()) }
			request.rating?.let { put("rating", it) }
			request.createdFrom?.let { put("createdFrom", TimeUnit.MILLISECONDS.toSeconds(it.toEpochMilli()).toString()) }
			request.createdTo?.let { put("createdTo", TimeUnit.MILLISECONDS.toSeconds(it.toEpochMilli()).toString()) }
			request.updatedFrom?.let { put("updatedFrom", TimeUnit.MILLISECONDS.toSeconds(it.toEpochMilli()).toString()) }
			request.updatedTo?.let { put("updatedTo", TimeUnit.MILLISECONDS.toSeconds(it.toEpochMilli()).toString()) }
			request.searchKeyword?.let { put("searchKeyword", it) }
			request.sortBy?.let { put("sortBy", it.name) }
			put("offset", request.offset.toString())
			put("limit", request.limit.toString())
		}.toMap()
	}

	override fun copyWithOffset(offset: Int) = copy(offset = offset)

}
