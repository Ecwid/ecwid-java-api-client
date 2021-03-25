package com.ecwid.apiclient.v3.dto.cart.request

import com.ecwid.apiclient.v3.dto.common.ApiUpdatedDTO

data class UpdatedCart(
		val hidden: Boolean? = null,
		val taxesOnShipping: List<TaxOnShipping>? = null
) : ApiUpdatedDTO {

	data class TaxOnShipping(
			val name: String? = null,
			val value: Double? = null,
			val total: Double? = null
	)

}
