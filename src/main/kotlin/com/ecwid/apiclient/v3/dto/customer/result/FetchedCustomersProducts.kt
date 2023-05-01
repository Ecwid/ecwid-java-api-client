package com.ecwid.apiclient.v3.dto.customer.result

import com.ecwid.apiclient.v3.dto.common.ApiFetchedDTO

data class FetchedCustomersProducts(
	val productId: Int = 0,
	val productName: String = "",
) : ApiFetchedDTO {

	override fun getModifyKind() = ApiFetchedDTO.ModifyKind.ReadOnly
}
