package com.ecwid.apiclient.v3.dto.productreview.request

import com.ecwid.apiclient.v3.dto.common.ApiRequestDTO
import com.ecwid.apiclient.v3.dto.productreview.enums.ProductReviewSelectMode
import com.ecwid.apiclient.v3.dto.productreview.enums.ProductReviewStatus
import java.time.Instant

data class ProductReviewMassUpdate(
	val reviewIds: List<Long>? = null,
	val selectMode: ProductReviewSelectMode? = null,
	val delete: Boolean = false,
	val newStatus: ProductReviewStatus? = null,
	val filters: Filters? = null,
) : ApiRequestDTO {

	data class Filters(
		val reviewId: List<Long>? = null,
		val productId: List<Long>? = null,
		val orderId: List<Long>? = null,
		val status: ProductReviewStatus? = null,
		val rating: List<Int>? = null,
		val createdFrom: Instant? = null,
		val createdTo: Instant? = null,
		val searchKeyword: String? = null,
		)
}
