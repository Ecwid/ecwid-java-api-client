package com.ecwid.api.v3.httptransport

import java.io.File
import java.io.InputStream

sealed class HttpBody(val mimeType: String) {

	class EmptyBody: HttpBody("")
	class StringBody(val body: String, mimeType: String): HttpBody(mimeType)
	class ByteArrayBody(val bytes: ByteArray, mimeType: String): HttpBody(mimeType)
	class InputStreamBody(val stream: InputStream, mimeType: String): HttpBody(mimeType)
	class LocalFileBody(val file: File, mimeType: String): HttpBody(mimeType)

	fun asString() = when (this) {
		is EmptyBody -> null
		is StringBody -> if (body.isEmpty()) null else body
		is ByteArrayBody -> "[Binary data: from byte array of size ${bytes.size}]"
		is InputStreamBody -> "[Binary data: from stream]"
		is LocalFileBody -> "[Binary data: from file '${file.absolutePath}']"
	}

}

