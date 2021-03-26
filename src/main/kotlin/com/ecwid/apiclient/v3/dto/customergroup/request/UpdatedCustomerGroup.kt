package com.ecwid.apiclient.v3.dto.customergroup.request

import com.ecwid.apiclient.v3.dto.common.ApiUpdatedDTO
import com.ecwid.apiclient.v3.dto.common.ApiUpdatedDTO.DTOKind
import com.ecwid.apiclient.v3.dto.customergroup.result.FetchedCustomerGroup

data class UpdatedCustomerGroup(
		val name: String = ""
) : ApiUpdatedDTO {

	override fun getKind() = DTOKind.ReadWrite(FetchedCustomerGroup::class)

}
