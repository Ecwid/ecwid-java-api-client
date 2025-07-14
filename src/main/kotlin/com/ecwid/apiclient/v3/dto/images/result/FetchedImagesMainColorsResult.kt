package com.ecwid.apiclient.v3.dto.images.result

import com.ecwid.apiclient.v3.dto.common.ApiResultDTO

data class FetchedImagesMainColorsResult(
	val result: Map<String, FetchedImageMainColors>,
) : ApiResultDTO


data class FetchedImageMainColors(
	val colors: List<String>,
)
