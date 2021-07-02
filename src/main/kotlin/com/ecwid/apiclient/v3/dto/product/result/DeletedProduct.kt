package com.ecwid.apiclient.v3.dto.product.result

import com.ecwid.apiclient.v3.dto.common.ApiResultDTO
import java.util.*

data class DeletedProduct(
	val id: Int = 0,
	val date: Date = Date()
) : ApiResultDTO
