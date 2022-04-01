package com.ecwid.apiclient.v3.dto.profile.result

import com.ecwid.apiclient.v3.dto.common.ApiResultDTO

data class ExtrafieldConfigSearchResult(
	val items: List<FetchedExtrafieldConfig> = listOf(),
) : ApiResultDTO
