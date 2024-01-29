package com.ecwid.apiclient.v3.dto.productreview.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.dto.common.PagingRequest
import com.ecwid.apiclient.v3.dto.productreview.enums.ProductReviewSortOrder
import com.ecwid.apiclient.v3.dto.productreview.enums.ProductReviewStatus
import com.ecwid.apiclient.v3.impl.RequestInfo
import com.ecwid.apiclient.v3.responsefields.ResponseFields
import java.util.*
import java.util.concurrent.TimeUnit

data class ProductReviewsSearchRequest(
	val reviewId: Long? = null,
	val productId: Long? = null,
	val orderId: Long? = null,
	val status: ProductReviewStatus? = null,
	val createdFrom: Date? = null,
	val createdTo: Date? = null,
	val updatedFrom: Date? = null,
	val updatedTo: Date? = null,
	val sortBy: ProductReviewSortOrder? = null,
	val limit: Int = 100,
	override val offset: Int = 0,
	val responseFields: ResponseFields = ResponseFields.All,
) : ApiRequest, PagingRequest<ProductReviewsSearchRequest> {
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
			request.reviewId?.let { put("reviewId", request.reviewId.toString()) }
			request.productId?.let { put("productId", request.productId.toString()) }
			request.orderId?.let { put("orderId", request.orderId.toString()) }
			request.status?.let { put("status", request.status.toString()) }
			request.createdFrom?.let { put("createdFrom", TimeUnit.MILLISECONDS.toSeconds(it.time).toString()) }
			request.createdTo?.let { put("createdTo", TimeUnit.MILLISECONDS.toSeconds(it.time).toString()) }
			request.updatedFrom?.let { put("updatedFrom", TimeUnit.MILLISECONDS.toSeconds(it.time).toString()) }
			request.updatedTo?.let { put("updatedTo", TimeUnit.MILLISECONDS.toSeconds(it.time).toString()) }
			request.sortBy?.let { put("sortBy", it.name) }
			put("offset", request.offset.toString())
			put("limit", request.limit.toString())
		}.toMap()
	}

	override fun copyWithOffset(offset: Int) = copy(offset = offset)

}
