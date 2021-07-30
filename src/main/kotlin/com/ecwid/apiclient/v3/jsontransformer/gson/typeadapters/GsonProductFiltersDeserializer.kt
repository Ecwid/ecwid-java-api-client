package com.ecwid.apiclient.v3.jsontransformer.gson.typeadapters

import com.ecwid.apiclient.v3.dto.product.result.GetProductFiltersResult.*
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class GsonProductFiltersDeserializer : JsonDeserializer<ProductFilters> {

	override fun deserialize(
		json: JsonElement,
		typeOfT: Type,
		context: JsonDeserializationContext
	): ProductFilters {

		var priceFilter: PriceFilter? = null
		var inventoryFilterValues: InventoryFilterValues? = null
		var onSaleFilterValues: OnSaleFilterValues? = null
		var categoriesFilterValues: CategoriesFilterValues? = null
		var optionsFilterMapValues: MutableMap<Option, OptionFilterValues>? = null
		var attributesFilterMapValues: MutableMap<Attribute, AttributeFilterValues>? = null

		json.asJsonObject.keySet().forEach { key ->
			val jsonElement = json.asJsonObject[key]
			when {
				key == "price" -> {
					priceFilter = context.deserialize(jsonElement, PriceFilter::class.java)
				}
				key == "inventory" -> {
					inventoryFilterValues = context.deserialize(jsonElement, InventoryFilterValues::class.java)
				}
				key == "onsale" -> {
					onSaleFilterValues = context.deserialize(jsonElement, OnSaleFilterValues::class.java)
				}
				key == "categories" -> {
					categoriesFilterValues = context.deserialize(jsonElement, CategoriesFilterValues::class.java)
				}
				key.startsWith("option_") -> {
					val optionName = key.replaceFirst("option_", "")
					val optionFilterValues: OptionFilterValues =
						context.deserialize(jsonElement, OptionFilterValues::class.java)

					val newOptionsFilterMapValues = optionsFilterMapValues ?: mutableMapOf()
					newOptionsFilterMapValues[Option(optionName)] = optionFilterValues
					optionsFilterMapValues = newOptionsFilterMapValues
				}
				key.startsWith("attribute_") -> {
					val attributeName = key.replaceFirst("attribute_", "")
					val attributeFilterValue: AttributeFilterValues =
						context.deserialize(jsonElement, AttributeFilterValues::class.java)

					val newAttributesFilterMapValues = attributesFilterMapValues ?: mutableMapOf()
					newAttributesFilterMapValues[Attribute(attributeName)] = attributeFilterValue
					attributesFilterMapValues = newAttributesFilterMapValues
				}
			}
		}

		return ProductFilters(
			price = priceFilter,
			inventory = inventoryFilterValues,
			onsale = onSaleFilterValues,
			categories = categoriesFilterValues,
			options = optionsFilterMapValues?.toMap(),
			attributes = attributesFilterMapValues?.toMap(),
		)
	}

}
