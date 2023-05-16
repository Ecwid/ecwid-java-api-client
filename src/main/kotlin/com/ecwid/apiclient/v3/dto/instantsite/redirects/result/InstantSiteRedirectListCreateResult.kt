package com.ecwid.apiclient.v3.dto.instantsite.redirects.result

import com.ecwid.apiclient.v3.dto.common.ApiResultDTO

data class InstantSiteRedirectListCreateResult(
	val createCount: Int = 0,
	val existsCount: Int = 0,
) : ApiResultDTO
