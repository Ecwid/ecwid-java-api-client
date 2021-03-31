package com.ecwid.apiclient.v3.dto.customer.result

import com.ecwid.apiclient.v3.dto.common.ApiResultDTO
import java.util.*

data class DeletedCustomer(
		val id: Int = 0,
		val date: Date = Date()
) : ApiResultDTO
