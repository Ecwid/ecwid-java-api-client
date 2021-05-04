package com.ecwid.apiclient.v3.dto.order.request

import com.ecwid.apiclient.v3.dto.common.ApiUpdatedDTO
import com.ecwid.apiclient.v3.dto.common.OrderedStringToStringMap
import com.ecwid.apiclient.v3.dto.order.enums.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


data class UpdatedOrder(
		val email: String? = null,
		val ipAddress: String? = null,
		val hidden: Boolean? = null,
		val createDate: Date? = null,

		val refererUrl: String? = null,
		val globalReferer: String? = null,
		val affiliateId: String? = null,
		val additionalInfo: OrderedStringToStringMap? = null,

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

		val customerId: Int? = null,
		val customerGroup: String? = null,
		val acceptMarketing: Boolean? = null,

		val total: Double? = null,
		val subtotal: Double? = null,

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

		val utmData: UtmData? = null,

		val pricesIncludeTax: Boolean? = null,
		val externalOrderData: ExternalOrderData? = null
) : ApiUpdatedDTO {

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
			val name: String? = null,
			val code: String? = null,
			val discountType: DiscountCouponType? = null,
			val status: DiscountCouponStatus? = null,
			val discount: Double? = null,
			val launchDate: Date? = null,
			val expirationDate: Date? = null,
			val totalLimit: Double? = null,
			val usesLimit: DiscountCouponUsesLimit? = null,
			val repeatCustomerOnly: Boolean? = null
			// var catalogLimit: DiscountCouponCatalogLimit? = null // TODO Figure out why saving not works
	)

//	data class DiscountCouponCatalogLimit(
//			val products: List<Int>? = null,
//			val categories: List<Int>? = null
//	)

	data class OrderItem(
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

			val isShippingRequired: Boolean? = null,
			val trackQuantity: Boolean? = null,
			val fixedShippingRateOnly: Boolean? = null,
			val digital: Boolean? = null,
			val couponApplied: Boolean? = null,

			val selectedOptions: List<OrderItemSelectedOption>? = null,
			val taxes: List<OrderItemTax>? = null,
			val dimensions: ProductDimensions? = null,
			val discounts: List<OrderItemDiscounts>? = null
	)

	data class OrderItemSelectedOption(
			val name: String? = null,
			val type: ProductOptionType? = null,
			val value: String? = null,
			val valuesArray: List<String>? = null,
			val selections: List<OrderItemSelectionInfo>? = null
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

	data class PersonInfo(
			val name: String? = null,
			val companyName: String? = null,
			val street: String? = null,
			val city: String? = null,
			val countryCode: String? = null,
			val postalCode: String? = null,
			val stateOrProvinceCode: String? = null,
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

	companion object {
		const val FACEBOOK_ORDER_REFERENCE_ID = "FACEBOOK"
	}
}
