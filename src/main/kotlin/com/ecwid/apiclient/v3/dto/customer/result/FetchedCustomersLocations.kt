package com.ecwid.apiclient.v3.dto.customer.result

import com.ecwid.apiclient.v3.dto.common.ApiFetchedDTO

data class FetchedCustomersLocations(
	val country: String = "",
	val countryCode: String = "",
) : ApiFetchedDTO {
	override fun getModifyKind() = ApiFetchedDTO.ModifyKind.ReadOnly

}
