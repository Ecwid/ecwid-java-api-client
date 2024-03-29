package com.ecwid.apiclient.v3.dto.productreview.result

import com.ecwid.apiclient.v3.dto.common.ApiResultDTO

data class ProductReviewSearchResult(
	val items: List<FetchedProductReview> = listOf(),
	val count: Int = 0,
	val total: Int = 0,
	val limit: Int = 0,
	val offset: Int = 0,
) : ApiResultDTO
