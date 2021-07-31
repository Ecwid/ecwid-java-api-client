package com.ecwid.apiclient.v3.dto.product.result

import com.ecwid.apiclient.v3.dto.common.ApiResultDTO

data class GetProductFiltersResult(
	val productCount: Int = 0,
	val filters: ProductFilters = ProductFilters()
) : ApiResultDTO {

	data class ProductFilters(
		val price: PriceFilter? = null,
		val inventory: InventoryFilterValues? = null,
		val onsale: OnSaleFilterValues? = null,
		val categories: CategoriesFilterValues? = null,
		val options: Map<Option, OptionFilterValues>? = null,
		val attributes: Map<Attribute, AttributeFilterValues>? = null,
	)

	data class PriceFilter(
		val minValue: Double = 0.0,
		val maxValue: Double = 0.0,
	)

	data class InventoryFilterValues(
		val values: List<InventoryFilterValue> = listOf()
	) {

		data class InventoryFilterValue(
			val id: String = "",
			val title: String = "",
			val productCount: Int = 0,
		)
	}

	data class OnSaleFilterValues(
		val values: List<OnSaleFilterValue> = listOf()
	) {

		data class OnSaleFilterValue(
			val id: String = "",
			val title: String = "",
			val productCount: Int = 0,
		)

	}

	data class CategoriesFilterValues(
		val values: List<CategoriesFilterValue> = listOf()
	) {

		data class CategoriesFilterValue(
			val id: Long = 0,
			val title: String = "",
			val titleTranslated: String = "",
			val productCount: Int = 0,
		)

	}

	data class OptionFilterValues(
		val title: String = "",
		val titleTranslated: String = "",
		val values: List<OptionFilterValue> = listOf()
	) {

		data class OptionFilterValue(
			val title: String = "",
			val titleTranslated: String = "",
			val productCount: Int = 0,
		)

	}

	data class AttributeFilterValues(
		val values: List<AttributeFilterValue> = listOf()
	) {

		data class AttributeFilterValue(
			val title: String = "",
			val productCount: Int = 0,
		)

	}

	data class Option(
		val optionName: String = ""
	)

	data class Attribute(
		val attributeName: String = ""
	)

}
