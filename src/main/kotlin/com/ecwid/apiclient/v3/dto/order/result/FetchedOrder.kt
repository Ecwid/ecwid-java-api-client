package com.ecwid.apiclient.v3.dto.order.result

import com.ecwid.apiclient.v3.dto.common.ApiFetchedDTO
import com.ecwid.apiclient.v3.dto.common.OrderedStringToStringMap
import com.ecwid.apiclient.v3.dto.order.enums.*
import java.util.*

data class FetchedOrder(
		val id: String? = null,

		val orderNumber: Int = 0,
		val email: String? = null,
		val ipAddress: String? = null,
		val hidden: Boolean? = null,
		val createDate: Date? = null,
		val createTimestamp: Int? = null, // TODO Figure out how to test
		val updateDate: Date? = null,
		val updateTimestamp: Int? = null, // TODO Figure out how to test

		val refererUrl: String? = null,
		val globalReferer: String? = null,
		val affiliateId: String? = null,
		val additionalInfo: OrderedStringToStringMap? = null,
		val extraFields: OrderedStringToStringMap? = null, // TODO Figure out how to test

		val orderComments: String? = null,
		val privateAdminNotes: String? = null,

		val fulfillmentStatus: OrderFulfillmentStatus? = null,
		val trackingNumber: String? = null,
		val pickupTime: Date? = null,

		val paymentStatus: OrderPaymentStatus? = null,
		val paymentMethod: String? = null,
		val paymentModule: String? = null,
		val paymentParams: OrderedStringToStringMap? = null,
		val paymentMessage: String? = null,
		val creditCardStatus: CreditCardStatus? = null,
		val externalTransactionId: String? = null,

		val customerId: Int? = null, // TODO Figure out how to test
		val customerGroup: String? = null,
		val acceptMarketing: Boolean? = null,

		val total: Double? = null,
		val subtotal: Double? = null,
		val usdTotal: Double? = null, // TODO Figure out how to test

		val tax: Double? = null,
		val customerTaxExempt: Boolean? = null,
		val customerTaxId: String? = null,
		val customerTaxIdValid: Boolean? = null,
		val reversedTaxApplied: Boolean? = null,

		val couponDiscount: Double? = null,
		val volumeDiscount: Double? = null,
		val membershipBasedDiscount: Double? = null,
		val totalAndMembershipBasedDiscount: Double? = null,
		val discount: Double? = null,
		val discountInfo: List<DiscountInfo>? = null,
		val discountCoupon: DiscountCouponInfo? = null,

		val items: List<OrderItem>? = null,

		val billingPerson: PersonInfo? = null,
		val shippingPerson: PersonInfo? = null,

		val shippingOption: ShippingOption? = null,
		val handlingFee: HandlingFee? = null,

		val refundedAmount: Double? = null,
		val refunds: List<RefundInfo>? = null,

		val utmData: UtmData? = null,

		val pricesIncludeTax: Boolean? = null,
		val externalOrderData: ExternalOrderData? = null
) : ApiFetchedDTO {

	data class CreditCardStatus(
			val avsMessage: String? = null,
			val cvvMessage: String? = null
	)

	data class DiscountInfo(
			val value: Double? = null,
			val type: DiscountType? = null,
			val base: DiscountBase? = null,
			val orderTotal: Double? = null,
			val description: String? = null
	)

	data class DiscountCouponInfo(
			val id: Int? = null, // TODO Figure out how to test
			val name: String? = null,
			val code: String? = null,
			val discountType: DiscountCouponType? = null,
			val status: DiscountCouponStatus? = null,
			val discount: Double? = null,
			val launchDate: Date? = null,
			val expirationDate: Date? = null,
			val totalLimit: Double? = null,
			val usesLimit: DiscountCouponUsesLimit? = null,
			val repeatCustomerOnly: Boolean? = null,
			val creationDate: Date? = null,
			val updateDate: Date? = null,
			val orderCount: Int? = null, // TODO Figure out how to test
			val catalogLimit: DiscountCouponCatalogLimit? = null, // TODO Figure out how to test
			val applicationLimit: DiscountCouponApplicationLimit? = null // TODO Add to docs?
	)

	data class DiscountCouponCatalogLimit(
			val products: List<Int>? = null,
			val categories: List<Int>? = null
	)

	data class OrderItem(
			val id: Int? = null, // TODO Figure out how to test

			val productId: Int? = null,
			val categoryId: Int? = null,

			val price: Double? = null,
			val productPrice: Double? = null,
			val shipping: Double? = null,
			val tax: Double? = null,
			val fixedShippingRate: Double? = null,
			val couponAmount: Double? = null,

			val sku: String? = null,
			val name: String? = null,
			val shortDescription: String? = null,
			val quantity: Int? = null,
			val quantityInStock: Int? = null,
			val weight: Double? = null,
			val imageUrl: String? = null, // TODO Figure out how to test
			val smallThumbnailUrl: String? = null,
			val hdThumbnailUrl: String? = null,

			val isShippingRequired: Boolean? = null,
			val trackQuantity: Boolean? = null,
			val fixedShippingRateOnly: Boolean? = null,
			val digital: Boolean? = null,
			val productAvailable: Boolean? = null, // TODO Probably this field is always true
			val couponApplied: Boolean? = null,

			val recurringChargeSettings: RecurringChargeSettings? = null,
			val subscriptionId: Long? = null,

			val selectedOptions: List<OrderItemSelectedOption>? = null,
			val taxes: List<OrderItemTax>? = null,
			val dimensions: ProductDimensions? = null,
			val discounts: List<OrderItemDiscounts>? = null
	)

	data class RecurringChargeSettings(
			val recurringInterval: RecurringSubscriptionInterval = RecurringSubscriptionInterval.MONTH,
			val recurringIntervalCount: Int = 1
	)

	data class OrderItemSelectedOption(
			val name: String? = null,
			val type: ProductOptionType? = null,
			val value: String? = null,
			val valuesArray: List<String>? = null,
			val selections: List<OrderItemSelectionInfo>? = null,
			val files: List<OrderItemProductFile>? = null
	)

	data class OrderItemSelectionInfo(
			val selectionTitle: String? = null,
			val selectionModifier: Double? = null,
			val selectionModifierType: PriceModifierType? = null
	)

	data class OrderItemTax(
			val name: String? = null,
			val value: Double? = null,
			val total: Double? = null,
			val taxOnDiscountedSubtotal: Double? = null,
			val taxOnShipping: Double? = null,
			val includeInPrice: Boolean? = null
	)

	data class ProductDimensions(
			val length: Double? = null,
			val width: Double? = null,
			val height: Double? = null
	)

	data class OrderItemDiscounts(
			val discountInfo: DiscountInfo? = null,
			val total: Double? = null
	)

	data class OrderItemProductFile(
			val id: Int? = null,
			val name: String? = null,
			val size: Int? = null,
			val url: String? = null
	)

	data class PersonInfo(
			val name: String? = null,
			val firstName: String? = null,
			val lastName: String? = null,
			val companyName: String? = null,
			val street: String? = null,
			val city: String? = null,
			val countryCode: String? = null,
			val countryName: String? = null, // TODO Figure out how to test
			val postalCode: String? = null,
			val stateOrProvinceCode: String? = null,
			val stateOrProvinceName: String? = null, // TODO Figure out how to test
			val phone: String? = null
	)

	data class ShippingOption(
			val shippingCarrierName: String? = null,
			val shippingMethodName: String? = null,
			val shippingRate: Double? = null,
			val estimatedTransitTime: String? = null,
			val isPickup: Boolean? = null,
			val pickupInstruction: String? = null,
			val fulfillmentType: FulfillmentType? = null
	)

	data class HandlingFee(
			val name: String? = null,
			val value: Double? = null,
			val description: String? = null
	)

	data class RefundInfo(
			val date: Date? = null,
			val source: String? = null,
			val reason: String? = null,
			val amount: Double? = null
	)

	data class UtmData(
			val source: String? = null,
			val campaign: String? = null,
			val medium: String? = null,
			val mcEid: String? = null,
			val mcCid: String? = null
	)

	data class ExternalOrderData(
		val externalFulfillment: Boolean? = null,
		val externalOrderId: String? = null,
		val refererId: String? = null,
		val platformSpecificFields: HashMap<String,String>? = null,
		val refererChannel: String? = null
	)
}
