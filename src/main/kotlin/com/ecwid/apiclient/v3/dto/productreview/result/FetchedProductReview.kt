package com.ecwid.apiclient.v3.dto.productreview.result

import com.ecwid.apiclient.v3.dto.common.ApiFetchedDTO
import com.ecwid.apiclient.v3.dto.common.ApiResultDTO
import com.ecwid.apiclient.v3.dto.productreview.enums.ProductReviewStatus
import java.util.*

data class FetchedProductReview(
	val id: Long? = null,
	val productId: Long? = null,
	val customerId: Long? = null,
	val orderId: Long? = null,
	val status: ProductReviewStatus? = null,
	val rating: Int? = null,
	val review: String? = null,
	val reviewerInfo: FetchedProductReviewerInfo? = null,
	val createDate: Date? = null,
	val updateDate: Date? = null,
	val createTimestamp: Long? = null,
	val updateTimestamp: Long? = null,
) : ApiFetchedDTO, ApiResultDTO {

	override fun getModifyKind() =
		ApiFetchedDTO.ModifyKind.ReadOnly

	data class FetchedProductReviewerInfo(
		val name: String? = null,
		val email: String? = null,
		val city: String? = null,
		val country: String? = null,
		val ordersCount: Int? = null,
	)

}

