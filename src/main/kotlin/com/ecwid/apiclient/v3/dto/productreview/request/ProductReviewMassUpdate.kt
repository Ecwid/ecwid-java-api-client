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
		val reviewId: String? = null,
		val productId: String? = null,
		val orderId: String? = null,
		val status: ProductReviewStatus? = null,
		val rating: Int? = null,
		val createdFrom: Instant? = null,
		val createdTo: Instant? = null,
		val searchKeyword: String? = null,
		)
}
