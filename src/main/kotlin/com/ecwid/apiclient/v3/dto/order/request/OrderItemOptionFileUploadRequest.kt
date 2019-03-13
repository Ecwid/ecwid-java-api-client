package com.ecwid.apiclient.v3.dto.order.request

import com.ecwid.apiclient.v3.dto.UploadFileData

data class OrderItemOptionFileUploadRequest(
		var orderNumber: Int = 0,
		var orderItemId: Int = 0,
		var optionName: String = "",
		var fileName: String = "",
		var fileData: UploadFileData = UploadFileData.ExternalUrlData("")
)