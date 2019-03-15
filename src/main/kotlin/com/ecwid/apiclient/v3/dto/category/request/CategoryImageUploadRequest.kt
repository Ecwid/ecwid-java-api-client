package com.ecwid.apiclient.v3.dto.category.request

import com.ecwid.apiclient.v3.dto.UploadFileData

data class CategoryImageUploadRequest(
		val categoryId: Int = 0,
		val fileData: UploadFileData = UploadFileData.ExternalUrlData("")
)