package com.ecwid.apiclient.v3.impl

import com.ecwid.apiclient.v3.asString
import com.ecwid.apiclient.v3.jsontransformer.JsonTransformer

interface ResponseParser<V> {
    fun parse(responseBytes: ByteArray): V
    fun getLogString(responseBytes: ByteArray): String
}

data class ParsedResponseWithExt<VBase, VExt>(
		val baseResult: VBase,
		val extResult: VExt
)

@PublishedApi
internal class StringResponseParser: ResponseParser<String> {

	override fun parse(responseBytes: ByteArray): String {
		return responseBytes.asString()
	}

	override fun getLogString(responseBytes: ByteArray): String {
		return responseBytes.asString()
	}

}

@PublishedApi
internal class ByteArrayResponseParser: ResponseParser<ByteArray> {

	override fun parse(responseBytes: ByteArray): ByteArray {
		return responseBytes
	}

	override fun getLogString(responseBytes: ByteArray): String {
		return "[Binary data: byte array of size ${responseBytes.size}]"
	}

}

@PublishedApi
internal class ObjectResponseParser<V>(
		private val jsonTransformer: JsonTransformer,
		private val clazz: Class<V>
): ResponseParser<V> {

	override fun parse(responseBytes: ByteArray): V {
		return jsonTransformer.deserialize(responseBytes.asString(), clazz)!!
	}

	override fun getLogString(responseBytes: ByteArray): String {
		return responseBytes.asString()
	}

}

@PublishedApi
internal class ObjectWithExtResponseParser<VBase, VExt>(
		private val jsonTransformer: JsonTransformer,
		private val baseClass: Class<VBase>,
		private val extClass: Class<VExt>
): ResponseParser<ParsedResponseWithExt<VBase, VExt>> {

	override fun parse(responseBytes: ByteArray): ParsedResponseWithExt<VBase, VExt> {
		val baseResult = jsonTransformer.deserialize(responseBytes.asString(), baseClass)!!
		val extResult = jsonTransformer.deserialize(responseBytes.asString(), extClass)!!
		return ParsedResponseWithExt(baseResult, extResult)
	}

	override fun getLogString(responseBytes: ByteArray): String {
		return responseBytes.asString()
	}

}

