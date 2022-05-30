package com.ecwid.apiclient.v3.dto.storage.result

import com.ecwid.apiclient.v3.dto.common.ApiResultDTO

data class StorageEntityUpdateResult(
	val updateCount: Int = 0,
) : ApiResultDTO
