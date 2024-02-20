package com.ecwid.apiclient.v3.rule.nullablepropertyrules

import com.ecwid.apiclient.v3.dto.productreview.result.FetchedProductReview
import com.ecwid.apiclient.v3.rule.NullablePropertyRule
import com.ecwid.apiclient.v3.rule.NullablePropertyRule.AllowNullable

val fetchedProductReviewNullablePropertyRules: List<NullablePropertyRule<*, *>> = listOf(
	AllowNullable(FetchedProductReview::createDate),
	AllowNullable(FetchedProductReview::createTimestamp),
	AllowNullable(FetchedProductReview::customerId),
	AllowNullable(FetchedProductReview::id),
	AllowNullable(FetchedProductReview::orderId),
	AllowNullable(FetchedProductReview::orderItemId),
	AllowNullable(FetchedProductReview::orderNumber),
	AllowNullable(FetchedProductReview::productId),
	AllowNullable(FetchedProductReview::productName),
	AllowNullable(FetchedProductReview::rating),
	AllowNullable(FetchedProductReview::review),
	AllowNullable(FetchedProductReview::reviewerInfo),
	AllowNullable(FetchedProductReview::status),
	AllowNullable(FetchedProductReview::updateDate),
	AllowNullable(FetchedProductReview::updateTimestamp),
	AllowNullable(FetchedProductReview.FetchedProductReviewerInfo::city),
	AllowNullable(FetchedProductReview.FetchedProductReviewerInfo::country),
	AllowNullable(FetchedProductReview.FetchedProductReviewerInfo::email),
	AllowNullable(FetchedProductReview.FetchedProductReviewerInfo::name),
	AllowNullable(FetchedProductReview.FetchedProductReviewerInfo::ordersCount),
)
