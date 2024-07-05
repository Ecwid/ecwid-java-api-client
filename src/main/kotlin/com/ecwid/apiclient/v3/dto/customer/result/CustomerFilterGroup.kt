package com.ecwid.apiclient.v3.dto.customer.result

import com.ecwid.apiclient.v3.dto.common.ApiFetchedDTO

data class CustomerFilterGroup(
	val id: Int = 0,
	val name: String = "General"
) : ApiFetchedDTO {
	override fun getModifyKind() = ApiFetchedDTO.ModifyKind.ReadOnly
}
