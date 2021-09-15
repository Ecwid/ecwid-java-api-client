package com.ecwid.apiclient.v3.dto.profile.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.dto.common.UploadFileData
import com.ecwid.apiclient.v3.dto.common.buildUploadRequestInfo
import com.ecwid.apiclient.v3.httptransport.HttpBody
import com.ecwid.apiclient.v3.impl.MIME_TYPE_OCTET_STREAM
import com.ecwid.apiclient.v3.impl.RequestInfo

data class InvoiceLogoUploadRequest(
	val fileData: UploadFileData = UploadFileData.ExternalUrlData("")
) : ApiRequest {
	override fun toRequestInfo(): RequestInfo {
		val pathSegments = listOf(
			"profile",
			"invoicelogo"
		)
		return buildUploadRequestInfo(pathSegments, emptyMap(), fileData)
	}

}
