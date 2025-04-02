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
		var text: String = ""
		var textTranslated: LocalizedValueMap? = null
		var priceModifier: Double = 0.0
		var priceModifierType: PriceModifierType = PriceModifierType.ABSOLUTE
		var type: ProductOptionChoiceType = ProductOptionChoiceType.SELECT
		var hexCodes: List<String> = emptyList()
		var imageId: String? = null

		json.asJsonObject.keySet().forEach { key ->
			val jsonElement = json.asJsonObject[key]
			when (key) {
				"text" -> text = jsonElement.asString
				"textTranslated" -> textTranslated = context.deserialize(jsonElement, LocalizedValueMap::class.java)
				"priceModifier" -> priceModifier = jsonElement.asDouble
				"priceModifierType" -> priceModifierType = PriceModifierType.valueOf(jsonElement.asString.uppercase())
				"type" -> type = ProductOptionChoiceType.valueOf(jsonElement.asString.uppercase())
				"hexCodes" -> hexCodes = jsonElement.asJsonArray.map { it.asString }
				"imageId" -> imageId = jsonElement.asString
			}
		}
		return when (type) {
			ProductOptionChoiceType.SELECT -> FetchedProduct.ProductOptionChoice.SelectChoice(
				text = text,
				textTranslated = textTranslated,
				priceModifier = priceModifier,
				priceModifierType = priceModifierType,
			)

			ProductOptionChoiceType.SWATCH -> FetchedProduct.ProductOptionChoice.SwatchChoice(
				text = text,
				textTranslated = textTranslated,
				priceModifier = priceModifier,
				priceModifierType = priceModifierType,
				hexCodes = hexCodes,
				imageId = imageId,
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
		var text: String = ""
		var textTranslated: LocalizedValueMap? = null
		var priceModifier: Double = 0.0
		var priceModifierType: PriceModifierType = PriceModifierType.ABSOLUTE
		var type: ProductOptionChoiceType = ProductOptionChoiceType.SELECT
		var hexCodes: List<String> = emptyList()
		var imageId: String? = null

		json.asJsonObject.keySet().forEach { key ->
			val jsonElement = json.asJsonObject[key]
			when (key) {
				"text" -> text = jsonElement.asString
				"textTranslated" -> textTranslated = context.deserialize(jsonElement, LocalizedValueMap::class.java)
				"priceModifier" -> priceModifier = jsonElement.asDouble
				"priceModifierType" -> priceModifierType = PriceModifierType.valueOf(jsonElement.asString.uppercase())
				"type" -> type = ProductOptionChoiceType.valueOf(jsonElement.asString.uppercase())
				"hexCodes" -> hexCodes = jsonElement.asJsonArray.map { it.asString }
				"imageId" -> imageId = jsonElement.asString
			}
		}
		return when (type) {
			ProductOptionChoiceType.SELECT -> UpdatedProduct.ProductOptionChoice.SelectChoice(
				text = text,
				textTranslated = textTranslated,
				priceModifier = priceModifier,
				priceModifierType = priceModifierType,
			)

			ProductOptionChoiceType.SWATCH -> UpdatedProduct.ProductOptionChoice.SwatchChoice(
				text = text,
				textTranslated = textTranslated,
				priceModifier = priceModifier,
				priceModifierType = priceModifierType,
				hexCodes = hexCodes,
				imageId = imageId,
			)
		}
	}

}
