package com.ecwid.apiclient.v3.dto.product.request

import com.ecwid.apiclient.v3.dto.UploadFileData

data class ProductFileUploadRequest(
		val productId: Int = 0,
		val fileName: String = "",
		val description: String = "",
		val fileData: UploadFileData = UploadFileData.ExternalUrlData("")
)