package com.ecwid.apiclient.v3.dto.profile.result

import com.ecwid.apiclient.v3.dto.common.ApiResultDTO

data class StoreProfileUpdateResult(
		val updateCount: Int = 0,
		val success: Boolean = true
) : ApiResultDTO
