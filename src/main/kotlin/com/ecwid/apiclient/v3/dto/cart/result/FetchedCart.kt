package com.ecwid.apiclient.v3.dto.cart.result

import com.ecwid.apiclient.v3.dto.cart.CartStringToStringMap
import com.ecwid.apiclient.v3.dto.order.enums.*
import java.util.*

data class FetchedCart(
		var cartId: String,

		var email: String? = null,
		var ipAddress: String? = null,
		var hidden: Boolean? = null,

		var createDate: Date? = null,
		var createTimestamp: Int? = null,
		var updateDate: Date? = null,
		var updateTimestamp: Int? = null,

		var refererUrl: String? = null,
		var globalReferer: String? = null,
		var affiliateId: String? = null,
		var additionalInfo: CartStringToStringMap? = null,
		var orderComments: String? = null,
		var trackingNumber: String? = null,

		var paymentMethod: String? = null,
		var paymentModule: String? = null,
		var paymentParams: CartStringToStringMap? = null,
		var paymentMessage: String? = null,

		var creditCardStatus: CreditCardStatus? = null,
		var externalTransactionId: String? = null,

		var recoveredOrderId: Int? = null,
		var recoveryEmailSentTimestamp: Date? = null,

		var customerId: Int? = null,
		var customerGroupId: Int? = null,
		var customerGroup: String? = null,

		var total: Double? = null,
		var subtotal: Double? = null,
		var usdTotal: Double? = null,

		var tax: Double? = null,
		var customerTaxExempt: Boolean? = null,
		var customerTaxId: String? = null,
		var customerTaxIdValid: Boolean? = null,
		var reversedTaxApplied: Boolean? = null,
		var taxesOnShipping: List<TaxOnShipping>? = null,

		var couponDiscount: Double? = null,
		var volumeDiscount: Double? = null,
		var membershipBasedDiscount: Double? = null,
		var totalAndMembershipBasedDiscount: Double? = null,
		var discount: Double? = null,
		var discountInfo: List<DiscountInfo>? = null,
		var discountCoupon: DiscountCouponInfo? = null,

		var items: List<OrderItem>? = null,

		var billingPerson: PersonInfo? = null,
		var shippingPerson: PersonInfo? = null,

		var shippingOption: ShippingOptionInfo? = null,
		var handlingFee: HandlingFeeInfo? = null
) {

	data class CreditCardStatus(
			var avsMessage: String? = null,
			var cvvMessage: String? = null
	)

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
	)

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

	data class ShippingOptionInfo(
			var shippingCarrierName: String? = null,
			var shippingMethodName: String? = null,
			var shippingRate: Double? = null,
			var estimatedTransitTime: String? = null,
			var isPickup: Boolean? = null,
			var pickupInstruction: String? = null
	)

	data class HandlingFeeInfo(
			var name: String? = null,
			var value: Double? = null,
			var description: String? = null
	)

	data class TaxOnShipping(
			var name: String? = null,
			var value: Double? = null,
			var total: Double? = null
	)
}
