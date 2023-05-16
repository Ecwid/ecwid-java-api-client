package com.ecwid.apiclient.v3.dto.instantsite.redirects.result

import com.ecwid.apiclient.v3.dto.common.ApiResultDTO

data class InstantSiteRedirectListDeleteResult(
	val deleteCount: Int = 0,
) : ApiResultDTO
