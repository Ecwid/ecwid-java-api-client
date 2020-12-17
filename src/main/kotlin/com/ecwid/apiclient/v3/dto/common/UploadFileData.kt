package com.ecwid.apiclient.v3.dto.common

import java.io.File
import java.io.InputStream

sealed class UploadFileData {

	data class ExternalUrlData(
			val externalUrl: String = ""
	) : UploadFileData()

	data class LocalFileData(
			val file: File
	) : UploadFileData()

	data class InputStreamData(
			val stream: InputStream
	) : UploadFileData()

	data class ByteArrayData(
			val bytes: ByteArray = byteArrayOf()
	) : UploadFileData() {

		override fun equals(other: Any?): Boolean {
			if (this === other) return true
			if (javaClass != other?.javaClass) return false

			other as ByteArrayData

			if (!bytes.contentEquals(other.bytes)) return false

			return true
		}

		override fun hashCode(): Int {
			return bytes.contentHashCode()
		}

	}

}
