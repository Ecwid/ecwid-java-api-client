package com.ecwid.apiclient.v3.dto

import java.io.File
import java.io.InputStream

sealed class UploadFileData {
	class ExternalUrlData(val externalUrl: String): UploadFileData()
	class LocalFileData(val file: File): UploadFileData()
	class InputStreamData(val stream: InputStream): UploadFileData()
	class ByteArrayData(val bytes: ByteArray): UploadFileData()
}