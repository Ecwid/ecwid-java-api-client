package com.ecwid.apiclient.v3.rule.nullablepropertyrules

import com.ecwid.apiclient.v3.dto.product.request.GetProductFiltersRequest
import com.ecwid.apiclient.v3.rule.NullablePropertyRule
import com.ecwid.apiclient.v3.rule.NullablePropertyRule.AllowNullable

val getProductFiltersRequestNullablePropertyRules: List<NullablePropertyRule<*, *>> = listOf(
    AllowNullable(GetProductFiltersRequest::attributes),
	AllowNullable(GetProductFiltersRequest::categories),
	AllowNullable(GetProductFiltersRequest::createdFrom),
	AllowNullable(GetProductFiltersRequest::createdTo),
	AllowNullable(GetProductFiltersRequest::enabled),
	AllowNullable(GetProductFiltersRequest::filterFacetLimits),
	AllowNullable(GetProductFiltersRequest::filterParentCategoryId),
	AllowNullable(GetProductFiltersRequest::includeProductsFromSubcategories),
	AllowNullable(GetProductFiltersRequest::inventory),
	AllowNullable(GetProductFiltersRequest::keyword),
	AllowNullable(GetProductFiltersRequest::lang),
	AllowNullable(GetProductFiltersRequest::onSale),
	AllowNullable(GetProductFiltersRequest::options),
	AllowNullable(GetProductFiltersRequest::priceFrom),
	AllowNullable(GetProductFiltersRequest::priceTo),
	AllowNullable(GetProductFiltersRequest::updatedFrom),
	AllowNullable(GetProductFiltersRequest::updatedTo)
)
