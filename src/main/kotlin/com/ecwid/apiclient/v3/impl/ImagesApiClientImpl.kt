package com.ecwid.apiclient.v3.impl

import com.ecwid.apiclient.v3.ApiClientHelper
import com.ecwid.apiclient.v3.ImagesApiClient
import com.ecwid.apiclient.v3.dto.images.request.ImagesMainColorsRequest
import com.ecwid.apiclient.v3.dto.images.result.FetchedImagesMainColorsResult

class ImagesApiClientImpl(
	private val apiClientHelper: ApiClientHelper,
) : ImagesApiClient {
	override fun getImagesMainColors(request: ImagesMainColorsRequest): FetchedImagesMainColorsResult {
		return apiClientHelper.makeObjectResultRequest<FetchedImagesMainColorsResult>(request)
	}
}
