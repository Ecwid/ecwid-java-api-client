package com.ecwid.apiclient.v3.dto.images.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.httptransport.HttpBody
import com.ecwid.apiclient.v3.impl.RequestInfo

data class ImagesMainColorsRequest(
	val imagesMainColorsRequest: ImagesMainColorsRequestBody = ImagesMainColorsRequestBody()
) : ApiRequest {
	override fun toRequestInfo(): RequestInfo {
		return RequestInfo.createPostRequest(
			pathSegments = listOf(
				"images",
				"main-colors",
			),
			httpBody = HttpBody.JsonBody(
				obj = imagesMainColorsRequest,
			)
		)
	}
}

data class ImagesMainColorsRequestBody(
	val imageUrls: List<String> = emptyList(),
	val colorsCount: Int = 5,
)
