package com.ecwid.apiclient.v3.dto.productreview.result

import com.ecwid.apiclient.v3.dto.common.ApiResultDTO

data class ProductReviewFiltersDataResult(
	val allCount: Int = 0,
	val moderatedCount: Int = 0,
	val publishedCount: Int = 0,
) : ApiResultDTO
