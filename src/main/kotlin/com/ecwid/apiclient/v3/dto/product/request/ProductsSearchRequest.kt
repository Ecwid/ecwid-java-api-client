package com.ecwid.apiclient.v3.dto.product.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.impl.RequestInfo
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
	) : ProductsSearchRequest(), ApiRequest {
		override fun toRequestInfo() = RequestInfo.createGetRequest(
				endpoint = "products",
				params = toParams()
		)

		private fun toParams(): Map<String, String> {
			val request = this
			return mutableMapOf<String, String>().apply {
				request.keyword?.let { put("keyword", it) }
				request.sku?.let { put("sku", it) }
				request.priceFrom?.let { put("priceFrom", it.toString()) }
				request.priceTo?.let { put("priceTo", it.toString()) }
				request.createdFrom?.let { put("createdFrom", (it.time / 1000).toString()) }
				request.createdTo?.let { put("createdTo", (it.time / 1000).toString()) }
				request.updatedFrom?.let { put("updatedFrom", (it.time / 1000).toString()) }
				request.updatedTo?.let { put("updatedTo", (it.time / 1000).toString()) }
				request.enabled?.let { put("enabled", it.toString()) }
				request.inventory?.let { put("inventory", if (it) "instock" else "outofstock") }
				request.onSale?.let { put("onsale", if (it) "onsale" else "notonsale") }
				request.attributes?.let { attributes ->
					attributes.attributes.forEach { (attributeName, attributeValue) ->
						put("attribute_$attributeName", attributeValue.joinToString(","))
					}
				}
				request.options?.let { options ->
					options.options.forEach { (optionName, optionValue) ->
						put("option_$optionName", optionValue.joinToString(","))
					}
				}
				request.categories?.let { put("categories", it.joinToString(",")) }
				request.includeProductsFromSubcategories?.let { put("includeProductsFromSubcategories", it.toString()) }
				request.baseUrl?.let { put("baseUrl", it) }
				request.cleanUrls?.let { put("cleanUrls", it.toString()) }
				request.sortBy?.let { put("sortBy", it.name) }
				put("offset", request.offset.toString())
				put("limit", request.limit.toString())
			}.toMap()
		}
	}

	data class ByIds(val productIds: List<Int> = listOf()) : ProductsSearchRequest(), ApiRequest {
		override fun toRequestInfo() = RequestInfo.createGetRequest(
				endpoint = "products",
				params = toParams()
		)

		constructor(productId: Int) : this(listOf(productId))

		private fun toParams(): Map<String, String> {
			val request = this
			return mutableMapOf<String, String>().apply {
				put("productId", request.productIds.joinToString(","))
			}.toMap()
		}
	}

	data class ProductSearchAttributes(val attributes: List<AttributeValue>) {

		data class AttributeValue(val name: String = "", val values: List<String> = listOf()) {
			constructor(name: String, value: String) : this(name = name, values = listOf(value))
		}

	}

	data class ProductSearchOptions(val options: List<OptionValue>) {

		data class OptionValue(val name: String = "", val values: List<String> = listOf()) {
			constructor(name: String, value: String) : this(name = name, values = listOf(value))
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
