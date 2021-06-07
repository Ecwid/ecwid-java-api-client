package com.ecwid.apiclient.v3.impl

import com.ecwid.apiclient.v3.ApiClientHelper
import com.ecwid.apiclient.v3.ProductTypesApiClient
import com.ecwid.apiclient.v3.dto.producttype.request.*
import com.ecwid.apiclient.v3.dto.producttype.result.*

internal class ProductTypesApiClientImpl(
		private val apiClientHelper: ApiClientHelper
) : ProductTypesApiClient {

	override fun getAllProductTypes(request: ProductTypesGetAllRequest) = apiClientHelper.makeObjectResultRequest<ProductTypesGetAllResult>(request)
	override fun getProductTypeDetails(request: ProductTypeDetailsRequest) = apiClientHelper.makeObjectResultRequest<FetchedProductType>(request)
	override fun createProductType(request: ProductTypeCreateRequest) = apiClientHelper.makeObjectResultRequest<ProductTypeCreateResult>(request)
	override fun updateProductType(request: ProductTypeUpdateRequest) = apiClientHelper.makeObjectResultRequest<ProductTypeUpdateResult>(request)
	override fun deleteProductType(request: ProductTypeDeleteRequest) = apiClientHelper.makeObjectResultRequest<ProductTypeDeleteResult>(request)

}
