package com.ecwid.apiclient.v3.impl

import com.ecwid.apiclient.v3.CouponsApiClient
import com.ecwid.apiclient.v3.dto.coupon.request.*
import com.ecwid.apiclient.v3.dto.coupon.result.*

internal data class CouponsApiClientImpl(
        private val apiClientHelper: ApiClientHelper
) : CouponsApiClient {
    override fun searchCoupons(request: CouponSearchRequest) = apiClientHelper.makeRequest<CouponSearchResult>(request)
    override fun getCouponDetails(request: CouponDetailsRequest) = apiClientHelper.makeRequest<FetchedCoupon>(request)
}