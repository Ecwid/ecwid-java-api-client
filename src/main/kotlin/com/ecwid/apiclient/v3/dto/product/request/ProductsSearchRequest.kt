package com.ecwid.apiclient.v3.dto.product.request

import java.util.*

sealed class ProductsSearchRequest {

	data class ByFilters(
			val keyword: String? = null,
			val priceFrom: Double? = null,
			val priceTo: Double? = null,
			val categories: List<Int>? = null,
			val includeProductsFromSubcategories: Boolean? = null,
			val sortBy: SortOrder? = null,
			val createdFrom: Date? = null,
			val createdTo: Date? = null,
			val updatedFrom: Date? = null,
			val updatedTo: Date? = null,
			val enabled: Boolean? = null,
			val inventory: Boolean? = null,
			val onSale: Boolean? = null,
			val attributes: ProductSearchAttributes? = null,
			val options: ProductSearchOptions? = null,
			val sku: String? = null,
			val baseUrl: String? = null,
			val cleanUrls: Boolean? = null,
			val offset: Int = 0,
			val limit: Int = 100
	): ProductsSearchRequest()

	data class ByIds(val productIds: List<Int> = listOf()): ProductsSearchRequest() {
		constructor(productId: Int): this(listOf(productId))
	}

	data class ProductSearchAttributes(val attributes: List<AttributeValue>) {

		data class AttributeValue(val name: String = "", val values: List<String> = listOf()) {
			constructor(name: String, value: String): this(name = name, values = listOf(value))
		}

	}

	data class ProductSearchOptions(val options: List<OptionValue>) {

		data class OptionValue(val name: String = "", val values: List<String> = listOf()) {
			constructor(name: String, value: String): this(name = name, values = listOf(value))
		}

	}

	enum class SortOrder {
		RELEVANCE,
		ADDED_TIME_DESC,
		ADDED_TIME_ASC,
		NAME_ASC,
		NAME_DESC,
		PRICE_ASC,
		PRICE_DESC,
		UPDATED_TIME_ASC,
		UPDATED_TIME_DESC
	}

}