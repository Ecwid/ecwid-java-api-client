package com.ecwid.apiclient.v3.dto.productreview.result

import com.ecwid.apiclient.v3.dto.common.ApiResultDTO

data class ProductReviewMassUpdateResult(
	val updateCount: Int = 0
) : ApiResultDTO
