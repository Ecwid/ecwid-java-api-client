package com.ecwid.apiclient.v3.dto.category.result

import com.ecwid.apiclient.v3.dto.common.ApiResultDTO

data class CategoryDeleteResult(
	val deleteCount: Int = 0
) : ApiResultDTO
