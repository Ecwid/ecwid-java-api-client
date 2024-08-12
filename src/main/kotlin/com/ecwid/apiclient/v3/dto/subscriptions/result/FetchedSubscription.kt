package com.ecwid.apiclient.v3.dto.subscriptions.result

import com.ecwid.apiclient.v3.dto.common.ApiFetchedDTO
import com.ecwid.apiclient.v3.dto.common.ApiFetchedDTO.ModifyKind
import com.ecwid.apiclient.v3.dto.common.LocalizedValueMap
import com.ecwid.apiclient.v3.dto.subscriptions.enums.PriceModifierType
import com.ecwid.apiclient.v3.dto.subscriptions.enums.ProductOptionType
import com.ecwid.apiclient.v3.dto.subscriptions.enums.SubscriptionInterval
import com.ecwid.apiclient.v3.dto.subscriptions.enums.SubscriptionStatus
import com.ecwid.apiclient.v3.jsontransformer.JsonFieldName
import java.util.*

data class FetchedSubscription(
	val subscriptionId: Int = 0,
	val customerId: Int = 0,
	val status: SubscriptionStatus = SubscriptionStatus.ACTIVE,
	val statusUpdated: Date = Date(),
	val created: Date = Date(),
	val cancelled: Date? = null,
	val nextCharge: Date = Date(),
	val nextChargeFormatted: String = "",
	val createTimestamp: Long? = null,
	val updateTimestamp: Long? = null,
	val chargeSettings: ChargeSettings = ChargeSettings(),
	val paymentMethod: PaymentMethod? = null,
	val orderTemplate: OrderTemplate = OrderTemplate(),
	val orders: List<Orders> = emptyList(),
) : ApiFetchedDTO {

	data class ChargeSettings(
		val recurringInterval: SubscriptionInterval = SubscriptionInterval.MONTH,
		val recurringIntervalCount: Int = 1,
	)

	data class PaymentMethod(
		val creditCardMaskedNumber: String? = null,
		val creditCardBrand: String? = null
	)

	data class OrderTemplate(
		val id: String? = null,
		val email: String? = null,
		val additionalInfo: AdditionalInfo? = null,
		val orderComments: String? = null,
		val paymentMethod: String? = null,
		val paymentModule: String? = null,
		val total: Double? = null,
		val subtotal: Double? = null,
		val usdTotal: Double? = null,
		val tax: Double? = null,
		val customerTaxExempt: Boolean? = null,
		val customerTaxId: String? = null,
		val customerTaxIdValid: Boolean? = null,
		val reversedTaxApplied: Boolean? = null,
		val items: List<OrderItem>? = null,
		val billingPerson: PersonInfo? = null,
		val shippingPerson: PersonInfo? = null,
		val shippingOption: ShippingOption? = null,
		val handlingFee: HandlingFee? = null,
		val pricesIncludeTax: Boolean? = null,
	)

	data class AdditionalInfo(
		val creditCard: String? = null,
		val creditCardExpirationMonth: String? = null,
		val creditCardExpirationYear: String? = null,
		@JsonFieldName("google_customer_id")
		val googleCustomerId: String? = null,
		val stripeCardId: String? = null,
		val stripeCreditCardBrand: String? = null,
		val stripeCreditCardLast4Digit: String? = null,
		val stripeCustomerId: String? = null,
		val stripeFingerprint: String? = null,
		val stripeLiveMode: Boolean? = null,
	)

	data class ShippingOption(
		val shippingMethodName: String? = null,
		val shippingRate: Double? = null,
	)

	data class HandlingFee(
		val name: String? = null,
		val value: Double? = null,
		val description: String? = null,
	)

	data class PersonInfo(
		val name: String? = null,
		val companyName: String? = null,
		val street: String? = null,
		val city: String? = null,
		val countryCode: String? = null,
		val countryName: String? = null,
		val postalCode: String? = null,
		val stateOrProvinceCode: String? = null,
		val stateOrProvinceName: String? = null,
		val phone: String? = null,
	)

	data class OrderItem(
		val id: Int? = null,

		val productId: Int? = null,
		val categoryId: Long? = null,

		val price: Double? = null,
		val productPrice: Double? = null,
		val shipping: Double? = null,
		val tax: Double? = null,
		val fixedShippingRate: Double? = null,

		val sku: String? = null,
		val name: String? = null,
		val nameTranslated: LocalizedValueMap? = null,
		val shortDescription: String? = null,
		val quantity: Int? = null,
		val quantityInStock: Int? = null,
		val weight: Double? = null,

		val trackQuantity: Boolean? = null,
		val fixedShippingRateOnly: Boolean? = null,

		val digital: Boolean? = null,
		val productAvailable: Boolean? = null,

		val imageUrl: String? = null,
		val smallThumbnailUrl: String? = null,
		val hdThumbnailUrl: String? = null,

		val recurringChargeSettings: RecurringChargeSettings? = null,
		val selectedOptions: List<SelectedOptions>? = null,
		val taxes: List<Taxes>? = null,
		val dimensions: Dimensions? = null,
	)

	data class SelectedOptions(
		val name: String? = null,
		val type: ProductOptionType? = null,
		val value: String? = null,
		val valuesArray: List<String>? = null,
		val selections: List<Selections>? = null
	)

	data class Selections(
		val selectionTitle: String? = null,
		val selectionModifier: Double? = null,
		val selectionModifierType: PriceModifierType? = null
	)

	data class Taxes(
		val name: String? = null,
		val value: Double? = null,
		val total: Double? = null,
		val taxOnDiscountedSubtotal: Double? = null,
		val taxOnShipping: Double? = null,
		val includeInPrice: Boolean? = null
	)

	data class Dimensions(
		val length: Double? = null,
		val width: Double? = null,
		val height: Double? = null
	)

	data class RecurringChargeSettings(
		val recurringInterval: SubscriptionInterval = SubscriptionInterval.MONTH,
		val intervalCount: Int = 0,
	)

	data class Orders(
		val id: Long = 0,
		val total: Double = 0.0,
		val createDate: Date = Date(),
	)

	override fun getModifyKind() = ModifyKind.ReadOnly
}
