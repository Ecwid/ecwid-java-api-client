package com.ecwid.apiclient.v3.dto.customergroup.request

import com.ecwid.apiclient.v3.dto.common.ApiUpdatedDTO
import com.ecwid.apiclient.v3.dto.common.ApiUpdatedDTO.ModifyKind
import com.ecwid.apiclient.v3.dto.customergroup.result.FetchedCustomerGroup

data class UpdatedCustomerGroup(
	val name: String = ""
) : ApiUpdatedDTO {

	override fun getModifyKind() = ModifyKind.ReadWrite(FetchedCustomerGroup::class)
}
