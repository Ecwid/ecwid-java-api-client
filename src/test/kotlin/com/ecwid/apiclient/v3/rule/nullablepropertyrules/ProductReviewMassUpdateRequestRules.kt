package com.ecwid.apiclient.v3.rule.nullablepropertyrules

import com.ecwid.apiclient.v3.dto.productreview.request.ProductReviewMassUpdate
import com.ecwid.apiclient.v3.rule.NullablePropertyRule
import com.ecwid.apiclient.v3.rule.NullablePropertyRule.AllowNullable

val productReviewMassUpdateRequestNullablePropertyRules: List<NullablePropertyRule<*, *>> = listOf(
	AllowNullable(ProductReviewMassUpdate.Filters::createdFrom),
	AllowNullable(ProductReviewMassUpdate.Filters::createdTo),
	AllowNullable(ProductReviewMassUpdate.Filters::orderId),
	AllowNullable(ProductReviewMassUpdate.Filters::productId),
	AllowNullable(ProductReviewMassUpdate.Filters::rating),
	AllowNullable(ProductReviewMassUpdate.Filters::reviewId),
	AllowNullable(ProductReviewMassUpdate.Filters::searchKeyword),
	AllowNullable(ProductReviewMassUpdate.Filters::status),
	AllowNullable(ProductReviewMassUpdate::filters),
	AllowNullable(ProductReviewMassUpdate::newStatus),
	AllowNullable(ProductReviewMassUpdate::reviewIds),
	AllowNullable(ProductReviewMassUpdate::selectMode),
	AllowNullable(ProductReviewMassUpdate::sendUpdateToReviewsService),
)
