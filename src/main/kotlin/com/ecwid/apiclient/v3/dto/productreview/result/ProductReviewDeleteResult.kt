package com.ecwid.apiclient.v3.dto.productreview.result

import com.ecwid.apiclient.v3.dto.common.ApiResultDTO

data class ProductReviewDeleteResult(
	val deleteCount: Int = 0
) : ApiResultDTO
