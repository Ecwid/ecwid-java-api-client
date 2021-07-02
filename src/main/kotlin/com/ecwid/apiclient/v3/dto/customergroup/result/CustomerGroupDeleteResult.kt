package com.ecwid.apiclient.v3.dto.customergroup.result

import com.ecwid.apiclient.v3.dto.common.ApiResultDTO

data class CustomerGroupDeleteResult(
	val deleteCount: Int = 0
) : ApiResultDTO
