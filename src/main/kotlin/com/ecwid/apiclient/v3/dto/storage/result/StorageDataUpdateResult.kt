package com.ecwid.apiclient.v3.dto.storage.result

import com.ecwid.apiclient.v3.dto.common.ApiResultDTO

data class StorageDataUpdateResult(
	val updateCount: Int = 0,
) : ApiResultDTO
