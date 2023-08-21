package com.ecwid.apiclient.v3.dto.customer.request

import com.ecwid.apiclient.v3.dto.common.ApiRequestDTO
import com.ecwid.apiclient.v3.dto.customer.enums.SelectMode

data class MassUpdateCustomer(
	val ids: List<Int>? = null,
	val mode: SelectMode = SelectMode.ALL,
	val delete: Boolean? = null,
	val customer: UpdatedCustomer? = null
) : ApiRequestDTO {

	data class UpdatedCustomer(
		val customerGroupId: Int? = null,
		val taxExempt: Boolean? = null,
		val acceptMarketing: Boolean? = null,
	)
}
