package com.ecwid.apiclient.v3.dto.customer.result

import com.ecwid.apiclient.v3.dto.common.ApiResultDTO

data class CustomerUpdateResult(
	val updateCount: Int = 0
) : ApiResultDTO
