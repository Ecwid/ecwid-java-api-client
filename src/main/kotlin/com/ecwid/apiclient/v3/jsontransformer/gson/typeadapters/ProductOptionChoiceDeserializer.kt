package com.ecwid.apiclient.v3.jsontransformer.gson.typeadapters

import com.ecwid.apiclient.v3.dto.common.LocalizedValueMap
import com.ecwid.apiclient.v3.dto.product.enums.PriceModifierType
import com.ecwid.apiclient.v3.dto.product.enums.ProductOptionChoiceType
import com.ecwid.apiclient.v3.dto.product.request.UpdatedProduct
import com.ecwid.apiclient.v3.dto.product.result.FetchedProduct
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class FetchedProductOptionChoiceDeserializer : JsonDeserializer<FetchedProduct.ProductOptionChoice> {
	override fun deserialize(
		json: JsonElement,
		typeOf: Type,
		context: JsonDeserializationContext,
	): FetchedProduct.ProductOptionChoice {
		val template = buildProductOptionChoiceTemplate(json, context)
		return when (template.type) {
			ProductOptionChoiceType.SELECT -> FetchedProduct.ProductOptionChoice.SelectChoice(
				text = template.text,
				textTranslated = template.textTranslated,
				priceModifier = template.priceModifier,
				priceModifierType = template.priceModifierType,
			)

			ProductOptionChoiceType.SWATCH -> FetchedProduct.ProductOptionChoice.SwatchChoice(
				text = template.text,
				textTranslated = template.textTranslated,
				priceModifier = template.priceModifier,
				priceModifierType = template.priceModifierType,
				hexCodes = template.hexCodes,
				imageId = template.imageId,
			)
		}
	}
}

class UpdatedProductOptionChoiceDeserializer : JsonDeserializer<UpdatedProduct.ProductOptionChoice> {
	override fun deserialize(
		json: JsonElement,
		typeOf: Type,
		context: JsonDeserializationContext,
	): UpdatedProduct.ProductOptionChoice {
		val template = buildProductOptionChoiceTemplate(json, context)
		return when (template.type) {
			ProductOptionChoiceType.SELECT -> UpdatedProduct.ProductOptionChoice.SelectChoice(
				text = template.text,
				textTranslated = template.textTranslated,
				priceModifier = template.priceModifier,
				priceModifierType = template.priceModifierType,
			)

			ProductOptionChoiceType.SWATCH -> UpdatedProduct.ProductOptionChoice.SwatchChoice(
				text = template.text,
				textTranslated = template.textTranslated,
				priceModifier = template.priceModifier,
				priceModifierType = template.priceModifierType,
				hexCodes = template.hexCodes,
				imageId = template.imageId,
			)
		}
	}
}

data class ProductOptionChoiceTemplate(
	var text: String = "",
	var textTranslated: LocalizedValueMap? = null,
	var priceModifier: Double = 0.0,
	var priceModifierType: PriceModifierType = PriceModifierType.ABSOLUTE,
	var type: ProductOptionChoiceType = ProductOptionChoiceType.SELECT,
	var hexCodes: List<String> = emptyList(),
	var imageId: String? = null,
)

private fun buildProductOptionChoiceTemplate(
	json: JsonElement,
	context: JsonDeserializationContext,
): ProductOptionChoiceTemplate {
	val template = ProductOptionChoiceTemplate()
	json.asJsonObject.keySet().forEach { key ->
		val jsonElement = json.asJsonObject[key]
		when (key) {
			"text" -> template.text = jsonElement.asString
			"textTranslated" -> template.textTranslated = context.deserialize(jsonElement, LocalizedValueMap::class.java)
			"priceModifier" -> template.priceModifier = jsonElement.asDouble
			"priceModifierType" -> template.priceModifierType = PriceModifierType.valueOf(jsonElement.asString.uppercase())
			"type" -> template.type = ProductOptionChoiceType.valueOf(jsonElement.asString.uppercase())
			"hexCodes" -> template.hexCodes = jsonElement.asJsonArray.map { it.asString }
			"imageId" -> template.imageId = jsonElement.asString
		}
	}
	return template
}
