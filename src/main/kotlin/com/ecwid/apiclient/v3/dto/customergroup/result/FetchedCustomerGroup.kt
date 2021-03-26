package com.ecwid.apiclient.v3.dto.customergroup.result

import com.ecwid.apiclient.v3.dto.common.ApiFetchedDTO
import com.ecwid.apiclient.v3.dto.common.ApiFetchedDTO.DTOKind
import com.ecwid.apiclient.v3.dto.customergroup.request.UpdatedCustomerGroup

data class FetchedCustomerGroup(
		val id: Int = 0,
		val name: String = ""
) : ApiFetchedDTO {

	override fun getKind() = DTOKind.ReadWrite(UpdatedCustomerGroup::class)

}
