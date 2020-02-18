package com.ecwid.apiclient.v3.dto.cart.request

import com.ecwid.apiclient.v3.dto.order.enums.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


data class OrderForCalculate(
        var email: String? = null,
        var ipAddress: String? = null,
        var customerId: Int? = null,
        var discountCoupon: DiscountCouponInfo? = null,
        var items: List<OrderItem>? = null,
        var billingPerson: PersonInfo? = null,
        var shippingPerson: PersonInfo? = null,
        var discountInfo: List<DiscountInfo>? = null
) {
    data class DiscountInfo(
            var value: Double? = null,
            var type: DiscountType? = null,
            var base: DiscountBase? = null,
            var orderTotal: Double? = null,
            var description: String? = null
    )

    data class OrderItemDiscountInfo(
            var value: Double? = null,
            var type: DiscountType? = null,
            var base: DiscountBase? = null,
            var orderTotal: Double? = null
    )

    data class DiscountCouponInfo(
            var name: String? = null,
            var code: String? = null,
            var discountType: DiscountCouponType? = null,
            var status: DiscountCouponStatus? = null,
            var discount: Double? = null,
            var launchDate: Date? = null,
            var expirationDate: Date? = null,
            var totalLimit: Double? = null,
            var usesLimit: DiscountCouponUsesLimit? = null,
            var applicationLimit: DiscountCouponApplicationLimit? = null,
            var creationDate: Date? = null,
            var orderCount: Int? = null,
            var catalogLimit: DiscountCouponCatalogLimit? = null
    )

    data class DiscountCouponCatalogLimit(
            var products: List<Int>? = null,
            var categories: List<Int>? = null
    )

    data class OrderItem(
            var id: Int? = null,

            var productId: Int? = null,
            var categoryId: Int? = null,

            var price: Double? = null,
            var productPrice: Double? = null,
            var shipping: Double? = null,
            var tax: Double? = null,
            var fixedShippingRate: Double? = null,
            var couponAmount: Double? = null,

            var sku: String? = null,
            var name: String? = null,
            var shortDescription: String? = null,
            var quantity: Int? = null,
            var quantityInStock: Int? = null,
            var weight: Double? = null,
            var imageUrl: String? = null,

            var isShippingRequired: Boolean? = null,
            var trackQuantity: Boolean? = null,
            var fixedShippingRateOnly: Boolean? = null,
            var digital: Boolean? = null,
            var couponApplied: Boolean? = null,

            var selectedOptions: List<OrderItemOption>? = null,
            var taxes: List<OrderItemTax>? = null,
            var files: List<OrderItemProductFile>? = null,
            var dimensions: ProductDimensions? = null,
            var discounts: List<OrderItemDiscounts>? = null
    )

    data class OrderItemOption(
            var name: String? = null,
            var type: ProductOptionType? = null,
            var value: String? = null,
            var valuesArray: List<String>? = null,
            var files: List<OrderItemOptinonFile>? = null,
            var selections: List<SelectionInfo>? = null
    ) {
        companion object {

            private val DATE_OPTION_FORMAT = ThreadLocal.withInitial<DateFormat> {
                SimpleDateFormat("yyyy-MM-dd")
            }

            fun createForChoiceOption(name: String, selection: String, files: List<OrderItemOptinonFile>?): OrderItemOption {
                return OrderItemOption(
                        name = name,
                        type = ProductOptionType.CHOICE,
                        value = selection,
                        valuesArray = listOf(selection),
                        files = files,
                        selections = listOf(
                                SelectionInfo(
                                        selectionTitle = selection,
                                        selectionModifier = 0.0, // Does not make sense for this option type but required
                                        selectionModifierType = PriceModifierType.ABSOLUTE // Does not make sense for this option type but required
                                )
                        )
                )
            }

            fun createForChoicesOption(name: String, selections: List<SelectionInfo>, files: List<OrderItemOptinonFile>?): OrderItemOption {
                return OrderItemOption(
                        name = name,
                        type = ProductOptionType.CHOICES,
                        valuesArray = selections.map { it.selectionTitle ?: "" },
                        files = files,
                        selections = selections
                )
            }

            fun createForTextOption(name: String, value: String): OrderItemOption {
                return OrderItemOption(
                        name = name,
                        type = ProductOptionType.TEXT,
                        value = value,
                        valuesArray = listOf(value),
                        selections = null
                )
            }

            fun createForDateOption(name: String, date: Date): OrderItemOption {
                return OrderItemOption(
                        name = name,
                        type = ProductOptionType.DATE,
                        value = DATE_OPTION_FORMAT.get().format(date)
                )
            }

            fun createForFilesOption(name: String): OrderItemOption {
                return OrderItemOption(
                        name = name,
                        type = ProductOptionType.FILES
                )
            }
        }
    }

    data class SelectionInfo(
            var selectionTitle: String? = null,
            var selectionModifier: Double? = null,
            var selectionModifierType: PriceModifierType? = null
    )

    data class OrderItemTax(
            var name: String? = null,
            var value: Double? = null,
            var total: Double? = null,
            var taxOnDiscountedSubtotal: Double? = null,
            var taxOnShipping: Double? = null,
            var includeInPrice: Boolean? = null
    )

    data class OrderItemProductFile(
            var productFileId: Int? = null,
            var maxDownloads: Int? = null,
            var remainingDownloads: Int? = null,
            var expire: Date? = null,
            var name: String? = null,
            var description: String? = null,
            var size: Int? = null,
            var adminUrl: String? = null,
            var customerUrl: String? = null
    )

    data class ProductDimensions(
            var length: Double? = null,
            var width: Double? = null,
            var height: Double? = null
    )

    data class OrderItemDiscounts(
            var discountInfo: OrderItemDiscountInfo? = null,
            var total: Double? = null
    )

    data class OrderItemOptinonFile(
            var id: Int? = null,
            var name: String? = null,
            var size: Int? = null,
            var url: String? = null
    )

    data class PersonInfo(
            var name: String? = null,
            var companyName: String? = null,
            var street: String? = null,
            var city: String? = null,
            var countryCode: String? = null,
            var countryName: String? = null,
            var postalCode: String? = null,
            var stateOrProvinceCode: String? = null,
            var stateOrProvinceName: String? = null,
            var phone: String? = null
    )
}