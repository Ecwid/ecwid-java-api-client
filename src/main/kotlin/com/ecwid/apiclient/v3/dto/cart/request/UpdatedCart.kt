package com.ecwid.apiclient.v3.dto.cart.request

data class UpdatedCart(
        var hidden: Boolean? = null,
        var taxesOnShipping: List<TaxOnShipping>? = null
) {

    data class TaxOnShipping(
            var name: String? = null,
            var value: Double? = null,
            var total: Double? = null
    )
}