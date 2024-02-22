package com.ecwid.apiclient.v3.rule.nullablepropertyrules

import com.ecwid.apiclient.v3.dto.productreview.request.ProductReviewSearchRequest
import com.ecwid.apiclient.v3.rule.NullablePropertyRule
import com.ecwid.apiclient.v3.rule.NullablePropertyRule.AllowNullable

val productReviewSearchRequestNullablePropertyRules: List<NullablePropertyRule<*, *>> = listOf(
	AllowNullable(ProductReviewSearchRequest::createdFrom),
	AllowNullable(ProductReviewSearchRequest::createdTo),
	AllowNullable(ProductReviewSearchRequest::orderId),
	AllowNullable(ProductReviewSearchRequest::productId),
	AllowNullable(ProductReviewSearchRequest::rating),
	AllowNullable(ProductReviewSearchRequest::reviewId),
	AllowNullable(ProductReviewSearchRequest::sortBy),
	AllowNullable(ProductReviewSearchRequest::status),
	AllowNullable(ProductReviewSearchRequest::updatedFrom),
	AllowNullable(ProductReviewSearchRequest::updatedTo),
)
