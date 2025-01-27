package com.ecwid.apiclient.v3.dto.swatches.result

import com.ecwid.apiclient.v3.dto.common.ApiResultDTO

data class FetchedSwatchColorsResult(
	val colors: List<FetchedSwatchColor> = listOf(),
) : ApiResultDTO
