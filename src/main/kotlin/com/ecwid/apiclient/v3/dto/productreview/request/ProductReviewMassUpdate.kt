package com.ecwid.apiclient.v3.dto.productreview.request

import com.ecwid.apiclient.v3.dto.common.ApiRequestDTO
import com.ecwid.apiclient.v3.dto.productreview.enums.ProductReviewSelectMode
import com.ecwid.apiclient.v3.dto.productreview.enums.ProductReviewStatus
import java.time.Instant

data class ProductReviewMassUpdate(
	val delete: Boolean = false,
	val filters: Filters? = null,
	val newStatus: ProductReviewStatus? = null,
	val reviewIds: List<Long>? = null,
	val selectMode: ProductReviewSelectMode? = null,
	val sendUpdateToReviewsService: Boolean? = null,
) : ApiRequestDTO {

	data class Filters(
		val createdFrom: Instant? = null,
		val createdTo: Instant? = null,
		val productId: List<Long>? = null,
		val orderId: List<Long>? = null,
		val reviewId: List<Long>? = null,
		val rating: List<Int>? = null,
		val searchKeyword: String? = null,
		val status: ProductReviewStatus? = null,
	)
}
