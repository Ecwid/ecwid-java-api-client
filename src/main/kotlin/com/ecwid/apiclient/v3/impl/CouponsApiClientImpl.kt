package com.ecwid.apiclient.v3.impl

import com.ecwid.apiclient.v3.CouponsApiClient
import com.ecwid.apiclient.v3.dto.coupon.request.*
import com.ecwid.apiclient.v3.dto.coupon.result.*

internal data class CouponsApiClientImpl(
		private val apiClientHelper: ApiClientHelper
) : CouponsApiClient {
	override fun searchCoupons(request: CouponSearchRequest) = apiClientHelper.makeRequest<CouponSearchResult>(request)

	override fun searchCouponsAsSequence(request: CouponSearchRequest) = sequence {
		var offsetRequest = request
		do {
			val searchResult = searchCoupons(offsetRequest)
			yieldAll(searchResult.items)
			offsetRequest = offsetRequest.copy(offset = offsetRequest.offset + searchResult.count)
		} while (searchResult.count >= searchResult.limit)
	}

	override fun getCouponDetails(request: CouponDetailsRequest) = apiClientHelper.makeRequest<FetchedCoupon>(request)
	override fun createCoupon(request: CouponCreateRequest) = apiClientHelper.makeRequest<CouponCreateResult>(request)
	override fun updateCoupon(request: CouponUpdateRequest) = apiClientHelper.makeRequest<CouponUpdateResult>(request)
	override fun deleteCoupon(request: CouponDeleteRequest) = apiClientHelper.makeRequest<CouponDeleteResult>(request)
}
