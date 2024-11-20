package com.ecwid.apiclient.v3.dto.product.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.dto.common.PagingRequest
import com.ecwid.apiclient.v3.impl.RequestInfo
import com.ecwid.apiclient.v3.responsefields.ResponseFields
import java.util.*
import java.util.concurrent.TimeUnit

sealed class ProductsSearchRequest : ApiRequest {

	data class ByFilters(
		val keyword: String? = null,
		val searchMethod: SearchMethod? = null,
		val externalReferenceId: String? = null,
		val priceFrom: Double? = null,
		val priceTo: Double? = null,
		val categories: List<Long>? = null,
		val includeProductsFromSubcategories: Boolean? = null,
		val sortBy: SortOrder? = null,
		val createdFrom: Date? = null,
		val createdTo: Date? = null,
		val updatedFrom: Date? = null,
		val updatedTo: Date? = null,
		val enabled: Boolean? = null,
		val inStock: Boolean? = null,
		val inventory: Boolean? = null,
		val onSale: Boolean? = null,
		val attributes: ProductSearchAttributes? = null,
		val options: ProductSearchOptions? = null,
		val sku: String? = null,
		val isGiftCard: Boolean? = null,
		val discountsAllowed: Boolean? = null,
		val isCustomerSetPrice: Boolean? = null,
		val baseUrl: String? = null,
		val cleanUrls: Boolean? = null,
		val slugsWithoutIds: Boolean? = null,
		override val offset: Int = 0,
		val limit: Int = 100,
		val lang: String? = null,
		val visibleInStorefront: Boolean? = null,
		val responseFields: ResponseFields = ResponseFields.All,
	) : ProductsSearchRequest(), PagingRequest<ByFilters> {
		override fun toRequestInfo() = RequestInfo.createGetRequest(
			pathSegments = listOf(
				"products"
			),
			params = toParams(),
			responseFields = responseFields,
		)

		private fun toParams(): Map<String, String> {
			val request = this
			return mutableMapOf<String, String>().apply {
				request.keyword?.let { put("keyword", it) }
				request.searchMethod?.let { put("searchMethod", it.name) }
				request.externalReferenceId?.let { put("externalReferenceId", it) }
				request.sku?.let { put("sku", it) }
				request.isGiftCard?.let { put("isGiftCard", it.toString()) }
				request.discountsAllowed?.let { put("discountsAllowed", it.toString()) }
				request.isCustomerSetPrice?.let { put("isCustomerSetPrice", it.toString()) }
				request.priceFrom?.let { put("priceFrom", it.toString()) }
				request.priceTo?.let { put("priceTo", it.toString()) }
				request.createdFrom?.let { put("createdFrom", TimeUnit.MILLISECONDS.toSeconds(it.time).toString()) }
				request.createdTo?.let { put("createdTo", TimeUnit.MILLISECONDS.toSeconds(it.time).toString()) }
				request.updatedFrom?.let { put("updatedFrom", TimeUnit.MILLISECONDS.toSeconds(it.time).toString()) }
				request.updatedTo?.let { put("updatedTo", TimeUnit.MILLISECONDS.toSeconds(it.time).toString()) }
				request.enabled?.let { put("enabled", it.toString()) }
				request.inStock?.let { put("inStock", it.toString()) }
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
				request.slugsWithoutIds?.let { put("slugsWithoutIds", it.toString()) }
				request.sortBy?.let { put("sortBy", it.name) }
				put("offset", request.offset.toString())
				put("limit", request.limit.toString())
				request.lang?.let { put("lang", it) }
				request.visibleInStorefront?.let { put("visibleInStorefront", it.toString()) }
			}.toMap()
		}

		override fun copyWithOffset(offset: Int) = copy(offset = offset)
	}

	data class ByIds(
		val productIds: List<Int> = listOf(),
		val baseUrl: String? = null,
		val cleanUrls: Boolean? = null,
		val slugsWithoutIds: Boolean? = null,
		val sortBy: SortOrder? = null,
		val lang: String? = null,
		val responseFields: ResponseFields = ResponseFields.All,
	) : ProductsSearchRequest() {
		override fun toRequestInfo() = RequestInfo.createGetRequest(
			pathSegments = listOf(
				"products"
			),
			params = toParams(),
			responseFields = responseFields,
		)

		@Suppress("unused")
		constructor(productId: Int) : this(listOf(productId))

		private fun toParams(): Map<String, String> {
			val request = this
			return mutableMapOf<String, String>().apply {
				put("productId", request.productIds.joinToString(","))
				request.baseUrl?.let { put("baseUrl", it) }
				request.cleanUrls?.let { put("cleanUrls", it.toString()) }
				request.slugsWithoutIds?.let { put("slugsWithoutIds", it.toString()) }
				request.sortBy?.let { put("sortBy", it.name) }
				request.lang?.let { put("lang", it) }
			}.toMap()
		}
	}

	data class ProductSearchAttributes(
		val attributes: List<AttributeValue> = emptyList()
	) {

		data class AttributeValue(val name: String = "", val values: List<String> = listOf()) {
			constructor(name: String, value: String) : this(name = name, values = listOf(value))
		}
	}

	data class ProductSearchOptions(
		val options: List<OptionValue> = emptyList()
	) {

		data class OptionValue(val name: String = "", val values: List<String> = listOf()) {
			constructor(name: String, value: String) : this(name = name, values = listOf(value))
		}
	}

	@Suppress("unused")
	enum class SearchMethod {
		STOREFRONT,
		CP
	}

	@Suppress("unused")
	enum class SortOrder {
		RELEVANCE,
		DEFINED_BY_STORE_OWNER,
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
