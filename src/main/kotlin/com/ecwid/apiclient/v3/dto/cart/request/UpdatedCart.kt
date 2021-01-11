package com.ecwid.apiclient.v3.dto.cart.request

data class UpdatedCart(
		val hidden: Boolean? = null,
		val taxesOnShipping: List<TaxOnShipping>? = null
) {

	data class TaxOnShipping(
			val name: String? = null,
			val value: Double? = null,
			val total: Double? = null
	)
}
