package com.ecwid.apiclient.v3.dto.product.request

import com.ecwid.apiclient.v3.dto.UploadFileData

data class ProductGalleryImageUploadRequest (
		val productId: Int = 0,
		val fileName: String = "",
		val fileData: UploadFileData = UploadFileData.ExternalUrlData("")
)