package com.ecwid.apiclient.v3.impl

import com.ecwid.apiclient.v3.ProductTypesApiClient
import com.ecwid.apiclient.v3.dto.producttype.request.*
import com.ecwid.apiclient.v3.dto.producttype.result.*
import com.ecwid.apiclient.v3.httptransport.HttpBody

internal class ProductTypesApiClientImpl(
		private val apiClientHelper: ApiClientHelper
): ProductTypesApiClient {

	override fun getAllProductTypes(request: ProductTypesGetAllRequest): ProductTypesGetAllResult = apiClientHelper.makeGetRequest<ProductTypesGetAllResult>(
			endpoint = request.toEndpoint(),
			params = mapOf()
	)

	override fun getProductTypeDetails(request: ProductTypeDetailsRequest) = apiClientHelper.makeGetRequest<FetchedProductType>(
			endpoint = request.toEndpoint(),
			params = mapOf()
	)

	override fun createProductType(request: ProductTypeCreateRequest) = apiClientHelper.makePostRequest<ProductTypeCreateResult>(
			endpoint = request.toEndpoint(),
			params = mapOf(),
			httpBody = HttpBody.JsonBody(
					obj = apiClientHelper.serializeJson(request.newProductType)
			)
	)

	override fun updateProductType(request: ProductTypeUpdateRequest) = apiClientHelper.makePutRequest<ProductTypeUpdateResult>(
			endpoint = request.toEndpoint(),
			params = mapOf(),
			httpBody = HttpBody.JsonBody(
					obj = apiClientHelper.serializeJson(request.updatedProductType)
			)
	)

	override fun deleteProductType(request: ProductTypeDeleteRequest) = apiClientHelper.makeDeleteRequest<ProductTypeDeleteResult>(
			endpoint = request.toEndpoint(),
			params = mapOf()
	)

}

private fun ProductTypesGetAllRequest.toEndpoint() = "classes"
private fun ProductTypeDetailsRequest.toEndpoint() = "classes/$productTypeId"
private fun ProductTypeCreateRequest.toEndpoint() = "classes"
private fun ProductTypeUpdateRequest.toEndpoint() = "classes/$productTypeId"
private fun ProductTypeDeleteRequest.toEndpoint(): String = "classes/$productTypeId"
