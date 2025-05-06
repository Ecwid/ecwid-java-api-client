package com.ecwid.apiclient.v3.impl

import com.ecwid.apiclient.v3.ApiClientHelper
import com.ecwid.apiclient.v3.ProductReviewsApiClient
import com.ecwid.apiclient.v3.dto.productreview.request.*
import com.ecwid.apiclient.v3.dto.productreview.result.*

class ProductReviewsApiClientImpl(
	private val apiClientHelper: ApiClientHelper,
) : ProductReviewsApiClient {
	override fun searchProductReviews(request: ProductReviewSearchRequest) =
		apiClientHelper.makeObjectResultRequest<ProductReviewSearchResult>(request)

	override fun searchProductReviewsAsSequence(request: ProductReviewSearchRequest) =
		apiClientHelper.makeObjectResultRequest<Sequence<FetchedProductReview>>(request)

	override fun getProductReviewDetails(request: ProductReviewDetailsRequest) =
		apiClientHelper.makeObjectResultRequest<FetchedProductReview>(request)

	override fun updateProductReviewStatus(request: ProductReviewUpdateStatusRequest) =
		apiClientHelper.makeObjectResultRequest<ProductReviewUpdateStatusResult>(request)

	override fun deleteProductReview(request: ProductReviewDeleteRequest) =
		apiClientHelper.makeObjectResultRequest<ProductReviewDeleteResult>(request)

	override fun massUpdateProductReview(request: ProductReviewMassUpdateRequest) =
		apiClientHelper.makeObjectResultRequest<ProductReviewMassUpdateResult>(request)

	override fun getProductReviewsFiltersData(request: ProductReviewFiltersDataRequest) =
		apiClientHelper.makeObjectResultRequest<ProductReviewFiltersDataResult>(request)

	override fun updateProductReviewCounters(request: ProductReviewCountersUpdateRequest) =
		apiClientHelper.makeObjectResultRequest<ProductReviewUpdateCountersResult>(request)

}
