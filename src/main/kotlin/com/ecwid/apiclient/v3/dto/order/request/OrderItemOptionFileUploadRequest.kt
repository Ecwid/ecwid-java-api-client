package com.ecwid.apiclient.v3.dto.order.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.dto.common.UploadFileData
import com.ecwid.apiclient.v3.dto.common.buildUploadRequestInfo
import com.ecwid.apiclient.v3.impl.RequestInfo

data class OrderItemOptionFileUploadRequest(
	val orderNumber: Int = 0,
	val orderIdentity: String = "",
	val orderItemId: Long = 0,
	val optionName: String = "",
	val fileName: String = "",
	val fileData: UploadFileData = UploadFileData.ExternalUrlData("")
) : ApiRequest {

	constructor(
		orderNumber: Int = 0,
		orderItemId: Long = 0,
		optionName: String = "",
		fileName: String = "",
		fileData: UploadFileData = UploadFileData.ExternalUrlData("")
	) : this(
		orderNumber = orderNumber,
		orderIdentity = orderNumber.toString(),
		orderItemId = orderItemId,
		optionName = optionName,
		fileName = fileName,
		fileData = fileData
	)

	override fun toRequestInfo(): RequestInfo {
		val commonParams = mapOf(
			"fileName" to fileName
		)
		return buildUploadRequestInfo(pathSegments, commonParams, fileData)
	}

	private val pathSegments = listOf(
		"orders",
		orderIdentity,
		"items",
		"$orderItemId",
		"options",
		optionName
	)
}
