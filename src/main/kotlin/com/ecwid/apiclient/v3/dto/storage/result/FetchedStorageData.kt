package com.ecwid.apiclient.v3.dto.storage.result

import com.ecwid.apiclient.v3.dto.common.ApiFetchedDTO
import com.ecwid.apiclient.v3.dto.storage.request.UpdatedStorageData

data class FetchedStorageData(
	val key: String = "",
	val value: String? = null,
) : ApiFetchedDTO {

	override fun getModifyKind() = ApiFetchedDTO.ModifyKind.ReadWrite(UpdatedStorageData::class)

}
