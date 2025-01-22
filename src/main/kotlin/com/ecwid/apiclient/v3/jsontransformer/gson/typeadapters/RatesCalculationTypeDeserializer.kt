package com.ecwid.apiclient.v3.jsontransformer.gson.typeadapters

import com.ecwid.apiclient.v3.dto.profile.enums.RatesCalculationType
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class RatesCalculationTypeDeserializer : JsonDeserializer<RatesCalculationType> {
	override fun deserialize(
		json: JsonElement,
		typeOfT: Type,
		context: JsonDeserializationContext
	): RatesCalculationType? {
		val strValue = json.asString
		return RatesCalculationType.entries.firstOrNull { it.asApiString() == strValue }
	}
}
