package com.ecwid.apiclient.v3.impl

import com.ecwid.apiclient.v3.ApiClientHelper
import com.ecwid.apiclient.v3.BrandsApiClient
import com.ecwid.apiclient.v3.dto.brand.request.BrandsSearchRequest
import com.ecwid.apiclient.v3.dto.brand.result.BrandsSearchResult
import com.ecwid.apiclient.v3.dto.common.PartialResult
import kotlin.reflect.KClass

internal class BrandsApiClientImpl(
	private val apiClientHelper: ApiClientHelper,
): BrandsApiClient {

	override fun searchBrands(request: BrandsSearchRequest.ByFilters) =
		apiClientHelper.makeObjectResultRequest<BrandsSearchResult>(request)

	override fun <Result : PartialResult<BrandsSearchResult>> searchBrands(
		request: BrandsSearchRequest.ByFilters,
		resultClass: KClass<Result>
	): Result {
		return apiClientHelper.makeObjectPartialResultRequest(
			request = request,
			resultClass = resultClass,
		)
	}
}
