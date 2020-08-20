package com.ecwid.apiclient.v3.dto.order.result

import com.ecwid.apiclient.v3.dto.OrderedStringToStringMap
import com.ecwid.apiclient.v3.dto.order.enums.*
import java.util.*

data class FetchedOrder(
		var orderNumber: Int = 0,

		var email: String? = null,
		var ipAddress: String? = null,
		var hidden: Boolean? = null,
		var createDate: Date? = null,
		var createTimestamp: Int? = null, // TODO Figure out how to test
		var updateDate: Date? = null,
		var updateTimestamp: Int? = null, // TODO Figure out how to test

		var refererUrl: String? = null,
		var globalReferer: String? = null,
		var affiliateId: String? = null,
		var additionalInfo: OrderedStringToStringMap? = null,
		var extraFields: OrderedStringToStringMap? = null, // TODO Figure out how to test

		var refererId: String? = null,
		var disableAllCustomerNotifications: Boolean? = null,
		var externalFulfillment: Boolean? = null,
		var externalOrderId: String? = null,

		var orderComments: String? = null,
		var privateAdminNotes: String? = null,

		var fulfillmentStatus: OrderFulfillmentStatus? = null,
		var trackingNumber: String? = null,
		var pickupTime: Date? = null,

		var paymentStatus: OrderPaymentStatus? = null,
		var paymentMethod: String? = null,
		var paymentModule: String? = null,
		var paymentParams: OrderedStringToStringMap? = null,
		var paymentMessage: String? = null,
		var creditCardStatus: CreditCardStatus? = null,
		var externalTransactionId: String? = null,

		var customerId: Int? = null, // TODO Figure out how to test
		var customerGroup: String? = null,
		var acceptMarketing: Boolean? = null,

		var total: Double? = null,
		var subtotal: Double? = null,
		var usdTotal: Double? = null, // TODO Figure out how to test

		var tax: Double? = null,
		var customerTaxExempt: Boolean? = null,
		var customerTaxId: String? = null,
		var customerTaxIdValid: Boolean? = null,
		var reversedTaxApplied: Boolean? = null,

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

		var shippingOption: ShippingOption? = null,
		var handlingFee: HandlingFee? = null,

		var refundedAmount: Double? = null,
		var refunds: List<RefundInfo>? = null,

		var utmData: UtmData? = null

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

	data class DiscountCouponInfo(
			var id: Int? = null, // TODO Figure out how to test
			var name: String? = null,
			var code: String? = null,
			var discountType: DiscountCouponType? = null,
			var status: DiscountCouponStatus? = null,
			var discount: Double? = null,
			var launchDate: Date? = null,
			var expirationDate: Date? = null,
			var totalLimit: Double? = null,
			var usesLimit: DiscountCouponUsesLimit? = null,
			var repeatCustomerOnly: Boolean? = null,
			var creationDate: Date? = null,
			var updateDate: Date? = null,
			var orderCount: Int? = null, // TODO Figure out how to test 
			var catalogLimit: DiscountCouponCatalogLimit? = null, // TODO Figure out how to test
			var applicationLimit: DiscountCouponApplicationLimit? = null // TODO Add to docs?
	)

	data class DiscountCouponCatalogLimit(
			var products: List<Int>? = null,
			var categories: List<Int>? = null
	)

	data class OrderItem(
			var id: Int? = null, // TODO Figure out how to test

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
			var imageUrl: String? = null, // TODO Figure out how to test 

			var isShippingRequired: Boolean? = null,
			var trackQuantity: Boolean? = null,
			var fixedShippingRateOnly: Boolean? = null,
			var digital: Boolean? = null,
			var productAvailable: Boolean? = null, // TODO Probably this field is always true
			var couponApplied: Boolean? = null,

			var selectedOptions: List<OrderItemSelectedOption>? = null,
			var taxes: List<OrderItemTax>? = null,
			var dimensions: ProductDimensions? = null,
			var discounts: List<OrderItemDiscounts>? = null
	)

	data class OrderItemSelectedOption(
			var name: String? = null,
			var type: ProductOptionType? = null,
			var value: String? = null,
			var valuesArray: List<String>? = null,
			var selections: List<OrderItemSelectionInfo>? = null,
			var files: List<OrderItemProductFile>? = null
	)

	data class OrderItemSelectionInfo(
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

	data class ProductDimensions(
			var length: Double? = null,
			var width: Double? = null,
			var height: Double? = null
	)

	data class OrderItemDiscounts(
			var discountInfo: DiscountInfo? = null,
			var total: Double? = null
	)

	data class OrderItemProductFile(
			var id: Int? = null,
			var name: String? = null,
			var size: Int? = null,
			var url: String? = null
	)

	data class PersonInfo(
			var name: String? = null,
			var firstName: String? = null,
			var lastName: String? = null,
			var companyName: String? = null,
			var street: String? = null,
			var city: String? = null,
			var countryCode: String? = null,
			var countryName: String? = null, // TODO Figure out how to test
			var postalCode: String? = null,
			var stateOrProvinceCode: String? = null,
			var stateOrProvinceName: String? = null, // TODO Figure out how to test
			var phone: String? = null
	)

	data class ShippingOption(
			var shippingCarrierName: String? = null,
			var shippingMethodName: String? = null,
			var shippingRate: Double? = null,
			var estimatedTransitTime: String? = null,
			var isPickup: Boolean? = null,
			var pickupInstruction: String? = null
	)

	data class HandlingFee(
			var name: String? = null,
			var value: Double? = null,
			var description: String? = null
	)

	data class RefundInfo(
			var date: Date? = null,
			var source: String? = null,
			var reason: String? = null,
			var amount: Double? = null
	)

	data class UtmData(
			var source: String? = null,
			val campaign: String? = null,
			val medium: String? = null,
			val mcEid: String? = null,
			val mcCid: String? = null
	)
}
