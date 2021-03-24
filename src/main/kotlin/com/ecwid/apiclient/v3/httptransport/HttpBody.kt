package com.ecwid.apiclient.v3.httptransport

import com.ecwid.apiclient.v3.impl.MIME_TYPE_APPLICATION_JSON
import java.io.File
import java.io.InputStream

sealed class HttpBody(val mimeType: String) {

	object EmptyBody : HttpBody("")
	class JsonBody(val obj: Any, var objExt: Any? = null) : HttpBody(MIME_TYPE_APPLICATION_JSON)
	class ByteArrayBody(val bytes: ByteArray, mimeType: String) : HttpBody(mimeType)
	class InputStreamBody(val stream: InputStream, mimeType: String) : HttpBody(mimeType)
	class LocalFileBody(val file: File, mimeType: String) : HttpBody(mimeType)

	fun asString() = when (this) {
		is EmptyBody -> null
		is JsonBody -> obj.toString()
		is ByteArrayBody -> "[Binary data: from byte array of size ${bytes.size}]"
		is InputStreamBody -> "[Binary data: from stream]"
		is LocalFileBody -> "[Binary data: from file '${file.absolutePath}']"
	}

}

sealed class TransportHttpBody(val mimeType: String) {
	object EmptyBody : TransportHttpBody("")
	class InputStreamBody(val stream: InputStream, mimeType: String) : TransportHttpBody(mimeType)
	class ByteArrayBody(val byteArray: ByteArray, mimeType: String) : TransportHttpBody(mimeType)
}
