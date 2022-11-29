package com.ecwid.apiclient.v3.dto.product.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.dto.product.request.GetProductFiltersRequest.*
import com.ecwid.apiclient.v3.impl.RequestInfo
import java.util.*
import java.util.concurrent.TimeUnit

data class GetProductFiltersRequest(
	val filterFields: List<FilterFieldType> = listOf(),
	val filterFacetLimits: Map<FilterFieldType, FilterFacetLimit>? = null,
	val filterParentCategoryId: FilterCategoryId? = null,
	val keyword: String? = null,
	val priceFrom: Double? = null,
	val priceTo: Double? = null,
	val categories: List<FilterCategoryId>? = null,
	val includeProductsFromSubcategories: Boolean? = null,
	val createdFrom: Date? = null,
	val createdTo: Date? = null,
	val updatedFrom: Date? = null,
	val updatedTo: Date? = null,
	val enabled: Boolean? = null,
	val options: Map<FilterFieldType.Option, List<String>>? = null,
	val attributes: Map<FilterFieldType.Attribute, List<String>>? = null,
	val inventory: Boolean? = null,
	val onSale: Boolean? = null,
	val lang: String? = null
) : ApiRequest {

	@Suppress("unused")
	sealed class FilterFieldType {

		object Price : FilterFieldType() {
			override fun getFilterFieldName() = "price"
		}

		object Inventory : FilterFieldType() {
			override fun getFilterFieldName() = "inventory"
		}

		object OnSale : FilterFieldType() {
			override fun getFilterFieldName() = "onsale"
		}

		object Categories : FilterFieldType() {
			override fun getFilterFieldName() = "categories"
		}

		data class Option(
			private val optionName: String = ""
		) : FilterFieldType() {
			override fun getFilterFieldName() = "option_" + escapeName(optionName)
		}

		data class Attribute(
			private val attributeName: String = ""
		) : FilterFieldType() {
			override fun getFilterFieldName() = "attribute_" + escapeName(attributeName)
		}

		abstract fun getFilterFieldName(): String

		protected fun escapeName(name: String): String {
			return name.replace(",", "\\,").replace("\\", "\\\\")
		}

	}

	@Suppress("unused")
	sealed class FilterFacetLimit {

		data class Limit(
			private val limit: Int = 0
		) : FilterFacetLimit() {
			override fun getFilterFacetLimitValue() = limit.toString()
		}

		object All : FilterFacetLimit() {
			override fun getFilterFacetLimitValue() = "all"
		}

		abstract fun getFilterFacetLimitValue(): String

	}

	@Suppress("unused")
	sealed class FilterCategoryId {

		data class Category(
			private val categoryId: Long = 0
		) : FilterCategoryId() {
			override fun getFilterParentCategoryIdValue() = categoryId.toString()
		}

		object Home : FilterCategoryId() {
			override fun getFilterParentCategoryIdValue() = "home"
		}

		abstract fun getFilterParentCategoryIdValue(): String

	}

	override fun toRequestInfo() = RequestInfo.createGetRequest(
		pathSegments = listOf(
			"products",
			"filters"
		),
		params = toParams()
	)

	private fun toParams(): Map<String, String> {
		val request = this
		return mutableMapOf<String, String>().apply {
			put("filterFields", request.filterFields.toFilterFields())
			request.filterFacetLimits?.let { filterFacetLimits ->
				put("filterFacetLimit", filterFacetLimits.toFilterFacetLimitValue())
			}
			request.filterParentCategoryId?.let { filterParentCategoryId ->
				put("filterParentCategoryId", filterParentCategoryId.getFilterParentCategoryIdValue())
			}
			request.keyword?.let { keyword ->
				put("keyword", keyword)
			}
			request.priceFrom?.let { priceFrom ->
				put("priceFrom", "$priceFrom")
			}
			request.priceTo?.let { priceTo ->
				put("priceTo", "$priceTo")
			}
			request.categories?.let { categories ->
				put("categories", categories.toCategoriesValue())
			}
			request.includeProductsFromSubcategories?.let { includeProductsFromSubcategories ->
				put("includeProductsFromSubcategories", "$includeProductsFromSubcategories")
			}
			request.createdFrom?.let { createdFrom ->
				put("createdFrom", TimeUnit.MILLISECONDS.toSeconds(createdFrom.time).toString())
			}
			request.createdTo?.let { createdTo ->
				put("createdTo", TimeUnit.MILLISECONDS.toSeconds(createdTo.time).toString())
			}
			request.updatedFrom?.let { updatedFrom ->
				put("updatedFrom", TimeUnit.MILLISECONDS.toSeconds(updatedFrom.time).toString())
			}
			request.updatedTo?.let { updatedTo ->
				put("updatedTo", TimeUnit.MILLISECONDS.toSeconds(updatedTo.time).toString())
			}
			request.enabled?.let { enabled ->
				put("enabled", "$enabled")
			}
			request.options?.let { options ->
				options.forEach { (optionField, optionValues) ->
					put(optionField.getFilterFieldName(), optionValues.joinToString(separator = ","))
				}
			}
			request.attributes?.let { attributes ->
				attributes.forEach { (attributeField, attributeValues) ->
					put(attributeField.getFilterFieldName(), attributeValues.joinToString(separator = ","))
				}
			}
			request.inventory?.let { inventory ->
				put("inventory", if (inventory) "instock" else "outofstock")
			}
			request.onSale?.let { onSale ->
				put("onsale", if (onSale) "onsale" else "notonsale")
			}
			request.lang?.let { lang ->
				put("lang", lang)
			}
		}.toMap()
	}

}

private fun List<FilterFieldType>.toFilterFields(): String {
	return joinToString(separator = ",", transform = FilterFieldType::getFilterFieldName)
}

private fun Map<FilterFieldType, FilterFacetLimit>.toFilterFacetLimitValue(): String {
	return toList()
		.joinToString(separator = ",") { (filterFieldType, filterFacetLimit) ->
			"${filterFieldType.getFilterFieldName()}:${filterFacetLimit.getFilterFacetLimitValue()}"
		}
}

private fun List<FilterCategoryId>.toCategoriesValue(): String {
	val categoriesValue = joinToString(separator = ",") { categoryId ->
		categoryId.getFilterParentCategoryIdValue()
	}
	return categoriesValue
}
