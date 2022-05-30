package com.ecwid.apiclient.v3.dto.storage.request

import com.ecwid.apiclient.v3.dto.common.ApiUpdatedDTO
import com.ecwid.apiclient.v3.dto.storage.result.FetchedStorageEntity

data class UpdatedStorageEntity(
	val key: String = "",
	val value: String = "",
) : ApiUpdatedDTO {

	override fun getModifyKind() = ApiUpdatedDTO.ModifyKind.ReadWrite(FetchedStorageEntity::class)

}
