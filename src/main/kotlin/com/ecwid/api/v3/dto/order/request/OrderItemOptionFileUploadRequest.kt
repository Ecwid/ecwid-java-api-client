package com.ecwid.api.v3.dto.order.request

import java.io.File
import java.io.InputStream

data class OrderItemOptionFileUploadRequest(
		val orderNumber: Int = 0,
		val orderItemId: Int = 0,
		val optionName: String = "",
		val fileName: String = "",
		val fileData: UploadFileData = UploadFileData.ExternalUrlData("")
) {

	sealed class UploadFileData {
		class ExternalUrlData(val externalUrl: String): UploadFileData()
		class LocalFileData(val file: File): UploadFileData()
		class InputStreamData(val stream: InputStream): UploadFileData()
		class ByteArrayData(val bytes: ByteArray): UploadFileData()
	}

}