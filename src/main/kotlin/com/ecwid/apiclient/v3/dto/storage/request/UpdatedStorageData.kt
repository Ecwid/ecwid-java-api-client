package com.ecwid.apiclient.v3.dto.storage.request

import com.ecwid.apiclient.v3.dto.common.ApiUpdatedDTO
import com.ecwid.apiclient.v3.dto.storage.result.FetchedStorageData

data class UpdatedStorageData(
	val key: String = "",
	val value: String = "",
) : ApiUpdatedDTO {

	override fun getModifyKind() = ApiUpdatedDTO.ModifyKind.ReadWrite(FetchedStorageData::class)

}
