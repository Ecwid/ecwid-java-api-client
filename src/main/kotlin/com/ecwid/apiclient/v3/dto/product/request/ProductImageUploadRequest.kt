package com.ecwid.apiclient.v3.dto.product.request

import com.ecwid.apiclient.v3.dto.UploadFileData

data class ProductImageUploadRequest(
		val productId: Int = 0,
		val fileData: UploadFileData = UploadFileData.ExternalUrlData("")
)