package com.ecwid.apiclient.v3.dto.payment

import com.ecwid.apiclient.v3.dto.order.result.FetchedOrder

data class PaymentAppRequest(
	val storeId: Int,
	val returnUrl: String,
	val merchantAppSettings: MerchantAppSettings,
	val cart: Cart,
	var token: String,
	val lang: String
) {
	data class MerchantAppSettings(
		val settings : Map<String, String?>?
	)

	data class Cart(
		val currency: String,
		val order: FetchedOrder
	)
}
