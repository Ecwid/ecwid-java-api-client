package com.ecwid.apiclient.v3.rule.nullablepropertyrules

import com.ecwid.apiclient.v3.dto.product.request.ProductsSearchRequest
import com.ecwid.apiclient.v3.rule.NullablePropertyRule
import com.ecwid.apiclient.v3.rule.NullablePropertyRule.AllowNullable

val productsSearchRequestNullablePropertyRules: List<NullablePropertyRule<*, *>> = listOf(
	AllowNullable(ProductsSearchRequest.ByFilters::attributes),
	AllowNullable(ProductsSearchRequest.ByFilters::baseUrl),
	AllowNullable(ProductsSearchRequest.ByFilters::categories),
	AllowNullable(ProductsSearchRequest.ByFilters::cleanUrls),
	AllowNullable(ProductsSearchRequest.ByFilters::slugsWithoutIds),
	AllowNullable(ProductsSearchRequest.ByFilters::createdFrom),
	AllowNullable(ProductsSearchRequest.ByFilters::createdTo),
	AllowNullable(ProductsSearchRequest.ByFilters::enabled),
	AllowNullable(ProductsSearchRequest.ByFilters::inStock),
	AllowNullable(ProductsSearchRequest.ByFilters::includeProductsFromSubcategories),
	AllowNullable(ProductsSearchRequest.ByFilters::inventory),
	AllowNullable(ProductsSearchRequest.ByFilters::keyword),
	AllowNullable(ProductsSearchRequest.ByFilters::lang),
	AllowNullable(ProductsSearchRequest.ByFilters::onSale),
	AllowNullable(ProductsSearchRequest.ByFilters::options),
	AllowNullable(ProductsSearchRequest.ByFilters::priceFrom),
	AllowNullable(ProductsSearchRequest.ByFilters::priceTo),
	AllowNullable(ProductsSearchRequest.ByFilters::searchMethod),
	AllowNullable(ProductsSearchRequest.ByFilters::sku),
	AllowNullable(ProductsSearchRequest.ByFilters::sortBy),
	AllowNullable(ProductsSearchRequest.ByFilters::updatedFrom),
	AllowNullable(ProductsSearchRequest.ByFilters::updatedTo),
	AllowNullable(ProductsSearchRequest.ByFilters::externalReferenceId),
	AllowNullable(ProductsSearchRequest.ByFilters::isGiftCard),
	AllowNullable(ProductsSearchRequest.ByFilters::discountsAllowed),
	AllowNullable(ProductsSearchRequest.ByFilters::isCustomerSetPrice),
	AllowNullable(ProductsSearchRequest.ByFilters::visibleInStorefront),
	AllowNullable(ProductsSearchRequest.ByIds::baseUrl),
	AllowNullable(ProductsSearchRequest.ByIds::cleanUrls),
	AllowNullable(ProductsSearchRequest.ByIds::slugsWithoutIds),
	AllowNullable(ProductsSearchRequest.ByIds::sortBy),
	AllowNullable(ProductsSearchRequest.ByIds::lang),
)
