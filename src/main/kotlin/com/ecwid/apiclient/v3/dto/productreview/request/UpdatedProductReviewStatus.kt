package com.ecwid.apiclient.v3.dto.productreview.request

import com.ecwid.apiclient.v3.dto.common.ApiRequestDTO
import com.ecwid.apiclient.v3.dto.productreview.enums.ProductReviewStatus

data class UpdatedProductReviewStatus(
	val status: ProductReviewStatus? = null
) : ApiRequestDTO
