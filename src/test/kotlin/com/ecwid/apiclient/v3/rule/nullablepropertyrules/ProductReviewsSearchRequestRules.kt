package com.ecwid.apiclient.v3.rule.nullablepropertyrules

import com.ecwid.apiclient.v3.dto.productreview.request.ProductReviewsSearchRequest
import com.ecwid.apiclient.v3.rule.NullablePropertyRule
import com.ecwid.apiclient.v3.rule.NullablePropertyRule.AllowNullable

val productReviewsSearchRequestNullablePropertyRules: List<NullablePropertyRule<*, *>> = listOf(
	AllowNullable(ProductReviewsSearchRequest::createdFrom),
	AllowNullable(ProductReviewsSearchRequest::createdTo),
	AllowNullable(ProductReviewsSearchRequest::orderId),
	AllowNullable(ProductReviewsSearchRequest::productId),
	AllowNullable(ProductReviewsSearchRequest::rating),
	AllowNullable(ProductReviewsSearchRequest::reviewId),
	AllowNullable(ProductReviewsSearchRequest::sortBy),
	AllowNullable(ProductReviewsSearchRequest::status),
	AllowNullable(ProductReviewsSearchRequest::updatedFrom),
	AllowNullable(ProductReviewsSearchRequest::updatedTo),
)
