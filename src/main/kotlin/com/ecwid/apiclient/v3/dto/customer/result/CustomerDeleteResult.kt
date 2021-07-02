package com.ecwid.apiclient.v3.dto.customer.result

import com.ecwid.apiclient.v3.dto.common.ApiResultDTO

data class CustomerDeleteResult(
	val deleteCount: Int = 0
) : ApiResultDTO
