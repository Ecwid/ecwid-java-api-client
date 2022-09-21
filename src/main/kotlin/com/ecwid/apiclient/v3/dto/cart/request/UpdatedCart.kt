package com.ecwid.apiclient.v3.dto.cart.request

import com.ecwid.apiclient.v3.dto.cart.result.FetchedCart
import com.ecwid.apiclient.v3.dto.common.ApiUpdatedDTO
import com.ecwid.apiclient.v3.dto.common.ApiUpdatedDTO.ModifyKind
import com.ecwid.apiclient.v3.dto.common.BaseOrderTax

data class UpdatedCart(
	val hidden: Boolean? = null,
	val taxesOnShipping: List<TaxOnShipping>? = null
) : ApiUpdatedDTO {

	data class TaxOnShipping(
		override val name: String? = null,
		override val value: Double? = null,
		override val total: Double? = null
	) : BaseOrderTax

	override fun getModifyKind() = ModifyKind.ReadWrite(FetchedCart::class)
}
