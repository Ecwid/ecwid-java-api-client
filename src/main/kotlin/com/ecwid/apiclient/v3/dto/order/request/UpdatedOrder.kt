package com.ecwid.apiclient.v3.dto.order.request

import com.ecwid.apiclient.v3.dto.OrderedStringToStringMap
import com.ecwid.apiclient.v3.dto.order.enums.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


data class UpdatedOrder(
		var email: String? = null,
		var ipAddress: String? = null,
		var hidden: Boolean? = null,
		var createDate: Date? = null,

		var refererUrl: String? = null,
		var globalReferer: String? = null,
		var affiliateId: String? = null,
		var additionalInfo: OrderedStringToStringMap? = null,

		var refererId: String? = null,
		var disableAllCustomerNotifications: Boolean? = null,
		var externalFulfillment: Boolean? = null,
		var externalOrderId: String? = null,
		var latestDeliveryDate: String? = null,
		var latestShipDate: String? = null,

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

		var customerId: Int? = null,
		var customerGroup: String? = null,
		var acceptMarketing: Boolean? = null,

		var total: Double? = null,
		var subtotal: Double? = null,

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
			var name: String? = null,
			var code: String? = null,
			var discountType: DiscountCouponType? = null,
			var status: DiscountCouponStatus? = null,
			var discount: Double? = null,
			var launchDate: Date? = null,
			var expirationDate: Date? = null,
			var totalLimit: Double? = null,
			var usesLimit: DiscountCouponUsesLimit? = null,
			var repeatCustomerOnly: Boolean? = null
			// var catalogLimit: DiscountCouponCatalogLimit? = null // TODO Figure out why saving not works
	)

//	data class DiscountCouponCatalogLimit(
//			var products: List<Int>? = null,
//			var categories: List<Int>? = null
//	)

	data class OrderItem(
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

			var isShippingRequired: Boolean? = null,
			var trackQuantity: Boolean? = null,
			var fixedShippingRateOnly: Boolean? = null,
			var digital: Boolean? = null,
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
			var selections: List<OrderItemSelectionInfo>? = null
	) {

		companion object {

			private val DATE_OPTION_FORMAT = ThreadLocal.withInitial<DateFormat> {
				SimpleDateFormat("yyyy-MM-dd")
			}

			fun createForChoiceOption(name: String, selection: String): OrderItemSelectedOption {
				return OrderItemSelectedOption(
						name = name,
						type = ProductOptionType.CHOICE,
						value = selection,
						valuesArray = listOf(selection),
						selections = listOf(
								OrderItemSelectionInfo(
										selectionTitle = selection,
										selectionModifier = 0.0, // Does not make sense for this option type but required
										selectionModifierType = PriceModifierType.ABSOLUTE // Does not make sense for this option type but required
								)
						)
				)
			}

			fun createForChoicesOption(name: String, selections: List<OrderItemSelectionInfo>): OrderItemSelectedOption {
				return OrderItemSelectedOption(
						name = name,
						type = ProductOptionType.CHOICES,
						valuesArray = selections.map { it.selectionTitle ?: "" },
						selections = selections
				)
			}

			fun createForTextOption(name: String, value: String): OrderItemSelectedOption {
				return OrderItemSelectedOption(
						name = name,
						type = ProductOptionType.TEXT,
						value = value,
						valuesArray = listOf(value),
						selections = null
				)
			}

			fun createForDateOption(name: String, date: Date): OrderItemSelectedOption {
				return OrderItemSelectedOption(
						name = name,
						type = ProductOptionType.DATE,
						value = DATE_OPTION_FORMAT.get().format(date)
				)
			}

			fun createForFilesOption(name: String): OrderItemSelectedOption {
				return OrderItemSelectedOption(
						name = name,
						type = ProductOptionType.FILES
				)
			}

		}

	}

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

	data class PersonInfo(
			var name: String? = null,
			var companyName: String? = null,
			var street: String? = null,
			var city: String? = null,
			var countryCode: String? = null,
			var postalCode: String? = null,
			var stateOrProvinceCode: String? = null,
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

	data class UtmData(
			var source: String? = null,
			val campaign: String? = null,
			val medium: String? = null,
			val mcEid: String? = null,
			val mcCid: String? = null
	)
}
