package com.ecwid.apiclient.v3.dto.payment

import com.ecwid.apiclient.v3.dto.common.OrderedStringToStringMap
import com.ecwid.apiclient.v3.dto.order.result.FetchedOrder

data class PaymentAppRequest(
	val storeId: Int? = null,
	val returnUrl: String? = null,
	val merchantAppSettings: MerchantAppSettings? = null,
	val cart: Cart? = null,
	val token: String? = null,
	val lang: String? = null
) {
	data class MerchantAppSettings(
		val settings : OrderedStringToStringMap? = null
	)

	data class Cart(
		val currency: String? = null,
		val order: FetchedOrder? = null
	)

	fun forLogging() : PaymentAppRequest {
		// we must remove secure data when logging this object
		return this.copy(token = "")
	}
}
