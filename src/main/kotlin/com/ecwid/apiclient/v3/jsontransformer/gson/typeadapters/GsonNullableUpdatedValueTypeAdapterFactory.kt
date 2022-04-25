package com.ecwid.apiclient.v3.jsontransformer.gson.typeadapters

import com.ecwid.apiclient.v3.dto.common.NullableUpdatedValue
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonNull
import com.google.gson.TypeAdapter
import com.google.gson.TypeAdapterFactory
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import java.lang.reflect.ParameterizedType


object GsonNullableUpdatedValueTypeAdapterFactory : TypeAdapterFactory {
	override fun <T : Any> create(gson: Gson, type: TypeToken<T>): TypeAdapter<T>? {
		return if (type.rawType == NullableUpdatedValue::class.java) {
			@Suppress("UNCHECKED_CAST")
			GsonNullableUpdatedValueTypeAdapter(this, gson, type).nullSafe() as TypeAdapter<T>
		} else {
			null
		}
	}
}


private class GsonNullableUpdatedValueTypeAdapter(
	factory: TypeAdapterFactory,
	gson: Gson,
	typeToken: TypeToken<*>,
) : TypeAdapter<NullableUpdatedValue<*>>() {

	@Suppress("UNCHECKED_CAST")
	private val wrappedValueDelegateAdapter = gson.getDelegateAdapter(
		factory,
		TypeToken.get((typeToken.type as ParameterizedType).actualTypeArguments[0]),
	) as TypeAdapter<Any>

	private val elementAdapter = gson.getAdapter(JsonElement::class.java)

	override fun write(writer: JsonWriter, value: NullableUpdatedValue<*>) {
		val wrappedValue: Any? = value.value
		if (wrappedValue != null) {
			elementAdapter.write(writer, wrappedValueDelegateAdapter.toJsonTree(wrappedValue))
		} else {
			val originalSerializeNulls = writer.serializeNulls
			try {
				writer.serializeNulls = true
				elementAdapter.write(writer, JsonNull.INSTANCE)
			} finally {
				writer.serializeNulls = originalSerializeNulls
			}
		}
	}

	override fun read(reader: JsonReader): NullableUpdatedValue<*> {
		throw UnsupportedOperationException()
	}
}
