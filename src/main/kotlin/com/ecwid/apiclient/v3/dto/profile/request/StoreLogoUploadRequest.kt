package com.ecwid.apiclient.v3.dto.profile.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.dto.common.UploadFileData
import com.ecwid.apiclient.v3.impl.RequestInfo

data class StoreLogoUploadRequest(
	val fileData: UploadFileData = UploadFileData.ExternalUrlData("")
) : ApiRequest {
	override fun toRequestInfo() = RequestInfo.buildUploadRequestInfo(
		listOf("profile", "logo"),
		emptyMap(),
		fileData
	)

}
