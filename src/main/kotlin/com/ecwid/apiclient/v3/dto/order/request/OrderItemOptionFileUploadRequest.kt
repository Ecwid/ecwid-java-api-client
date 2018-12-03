package com.ecwid.apiclient.v3.dto.order.request

import java.io.File
import java.io.InputStream

data class OrderItemOptionFileUploadRequest(
		var orderNumber: Int = 0,
		var orderItemId: Int = 0,
		var optionName: String = "",
		var fileName: String = "",
		var fileData: UploadFileData = UploadFileData.ExternalUrlData("")
) {

	sealed class UploadFileData {
		class ExternalUrlData(val externalUrl: String): UploadFileData()
		class LocalFileData(val file: File): UploadFileData()
		class InputStreamData(val stream: InputStream): UploadFileData()
		class ByteArrayData(val bytes: ByteArray): UploadFileData()
	}

}