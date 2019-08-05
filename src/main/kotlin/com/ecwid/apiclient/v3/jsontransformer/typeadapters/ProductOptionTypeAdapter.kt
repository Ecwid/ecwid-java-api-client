package com.ecwid.apiclient.v3.jsontransformer.typeadapters

import com.ecwid.apiclient.v3.dto.product.enums.ProductOptionType
import com.ecwid.apiclient.v3.dto.product.result.FetchedProduct
import com.ecwid.apiclient.v3.exception.JsonDeserializationException
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class ProductOptionDeserializer : JsonDeserializer<FetchedProduct.ProductOption> {
	override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): FetchedProduct.ProductOption? {
		val jsonObject = json.asJsonObject
		val type = try {
			ProductOptionType.valueOf(jsonObject["type"].asString)
		} catch (t: Throwable) {
			throw JsonDeserializationException("Unknown ProductOption type", t)
		}

		val klass = when (type) {
			ProductOptionType.SELECT -> FetchedProduct.ProductOption.SelectOption::class.java
			ProductOptionType.RADIO -> FetchedProduct.ProductOption.RadioOption::class.java
			ProductOptionType.CHECKBOX -> FetchedProduct.ProductOption.CheckboxOption::class.java
			ProductOptionType.TEXTFIELD -> FetchedProduct.ProductOption.TextFieldOption::class.java
			ProductOptionType.TEXTAREA -> FetchedProduct.ProductOption.TextAreaOption::class.java
			ProductOptionType.DATE -> FetchedProduct.ProductOption.DateOption::class.java
			ProductOptionType.FILES -> FetchedProduct.ProductOption.FilesOption::class.java
		}
		return context.deserialize(json, klass)
	}
}