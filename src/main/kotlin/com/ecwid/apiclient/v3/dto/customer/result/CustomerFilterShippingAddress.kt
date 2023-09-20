package com.ecwid.apiclient.v3.dto.customer.result

import com.ecwid.apiclient.v3.dto.common.ApiFetchedDTO

data class CustomerFilterShippingAddress(
	val street: String? = null,
	val city: String? = null,
	val countryCode: String? = null,
	val countryName: String? = null,
) : ApiFetchedDTO {
	override fun getModifyKind() = ApiFetchedDTO.ModifyKind.ReadOnly
}
