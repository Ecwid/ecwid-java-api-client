package com.ecwid.apiclient.v3

import com.ecwid.apiclient.v3.dto.brand.request.BrandsSearchRequest
import com.ecwid.apiclient.v3.dto.brand.result.BrandsSearchResult
import com.ecwid.apiclient.v3.dto.common.PartialResult
import kotlin.reflect.KClass

// Brands
// https://api-docs.ecwid.com/reference/product-brands
interface BrandsApiClient {
	fun searchBrands(request: BrandsSearchRequest): BrandsSearchResult
	fun <Result> searchBrands(request: BrandsSearchRequest, resultClass: KClass<Result>): Result
		where Result : PartialResult<BrandsSearchResult>
}

@Suppress("EXTENSION_SHADOWED_BY_MEMBER")
inline fun <reified Result: PartialResult<BrandsSearchResult>> BrandsApiClient.searchBrands(
	request: BrandsSearchRequest
): Result {
	return searchBrands(request, resultClass = Result::class)
}
