package com.ecwid.apiclient.v3.converter

import com.ecwid.apiclient.v3.dto.cart.request.UpdatedCart
import com.ecwid.apiclient.v3.dto.cart.result.FetchedCart

fun FetchedCart.toUpdated(): UpdatedCart {
	return UpdatedCart(
			hidden = hidden,
			taxesOnShipping = taxesOnShipping?.map(FetchedCart.TaxOnShipping::toUpdated)
	)
}

fun FetchedCart.TaxOnShipping.toUpdated(): UpdatedCart.TaxOnShipping {
	return UpdatedCart.TaxOnShipping(
			name = name,
			value = value,
			total = total
	)
}
