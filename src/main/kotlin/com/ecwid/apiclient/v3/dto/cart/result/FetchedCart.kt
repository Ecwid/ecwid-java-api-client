package com.ecwid.apiclient.v3.dto.cart.result

import com.ecwid.apiclient.v3.dto.cart.CartStringToStringMap
import com.ecwid.apiclient.v3.dto.cart.request.UpdatedCart
import com.ecwid.apiclient.v3.dto.common.ApiFetchedDTO
import com.ecwid.apiclient.v3.dto.common.ApiFetchedDTO.ModifyKind
import com.ecwid.apiclient.v3.dto.common.BaseOrderTax
import com.ecwid.apiclient.v3.dto.order.enums.*
import java.util.*

data class FetchedCart(
	val cartId: String = "",
	val email: String? = null,
	val ipAddress: String? = null,
	val hidden: Boolean? = null,

	val createDate: Date? = null,
	val createTimestamp: Int? = null,
	val updateDate: Date? = null,
	val updateTimestamp: Int? = null,

	val refererUrl: String? = null,
	val globalReferer: String? = null,
	val affiliateId: String? = null,
	val additionalInfo: CartStringToStringMap? = null,
	val orderComments: String? = null,
	val trackingNumber: String? = null,
	val trackingUrl: String? = null,

	val paymentMethod: String? = null,
	val paymentModule: String? = null,
	val paymentParams: CartStringToStringMap? = null,
	val paymentMessage: String? = null,

	val creditCardStatus: CreditCardStatus? = null,
	val externalTransactionId: String? = null,

	val recoveredOrderId: Int? = null,
	val recoveredPublicUid: String? = null,
	val recoveryEmailSentTimestamp: Date? = null,

	val customerId: Int? = null,
	val customerGroupId: Int? = null,
	val customerGroup: String? = null,

	val total: Double? = null,
	val subtotal: Double? = null,
	val usdTotal: Double? = null,

	val giftCardCode: String? = null,
	val giftCardId: Int? = null,
	val giftCardUuid: String? = null,
	val giftCardRedemption: Double? = null,
	val totalBeforeGiftCardRedemption: Double? = null,
	val giftCardDoubleSpending: Boolean? = null,

	val tax: Double? = null,
	val customerTaxExempt: Boolean? = null,
	val customerTaxId: String? = null,
	val customerTaxIdValid: Boolean? = null,
	val reversedTaxApplied: Boolean? = null,
	val taxesOnShipping: List<TaxOnShipping>? = null,

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

	val shippingOption: ShippingOptionInfo? = null,
	val handlingFee: HandlingFeeInfo? = null,

	val utmData: UtmData? = null,

	val acceptMarketing: Boolean? = null
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

	data class OrderItemDiscountInfo(
		val value: Double? = null,
		val type: DiscountType? = null,
		val base: DiscountBase? = null,
		val orderTotal: Double? = null
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
		val applicationLimit: DiscountCouponApplicationLimit? = null,
		val creationDate: Date? = null,
		val orderCount: Int? = null,
		val catalogLimit: DiscountCouponCatalogLimit? = null
	)

	data class DiscountCouponCatalogLimit(
		val products: List<Int>? = null,
		val categories: List<Long>? = null
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
		val couponAmount: Double? = null,

		val sku: String? = null,
		val name: String? = null,
		val shortDescription: String? = null,
		val quantity: Int? = null,
		val quantityInStock: Int? = null,
		val weight: Double? = null,
		val imageUrl: String? = null,

		val isShippingRequired: Boolean? = null,
		val trackQuantity: Boolean? = null,
		val fixedShippingRateOnly: Boolean? = null,
		val digital: Boolean? = null,
		val couponApplied: Boolean? = null,
		val giftCard: Boolean? = null,

		val selectedOptions: List<OrderItemOption>? = null,
		val combinationId: Int? = null,
		val taxes: List<OrderItemTax>? = null,
		val files: List<OrderItemProductFile>? = null,
		val dimensions: ProductDimensions? = null,
		val discounts: List<OrderItemDiscounts>? = null,
		val externalReferenceId: String? = null
	)

	data class OrderItemOption(
		val name: String? = null,
		val type: ProductOptionType? = null,
		val value: String? = null,
		val valuesArray: List<String>? = null,
		val files: List<OrderItemOptionFile>? = null,
		val selections: List<SelectionInfo>? = null
	)

	data class SelectionInfo(
		val selectionTitle: String? = null,
		val selectionModifier: Double? = null,
		val selectionModifierType: PriceModifierType? = null
	)

	data class OrderItemTax(
		override val name: String? = null,
		override val value: Double? = null,
		override val total: Double? = null,
		val taxType: OrderItemTaxType? = null,
		val includeInPrice: Boolean? = null,
		val taxOnDiscountedSubtotal: Double? = null,
		val taxOnShipping: Double? = null
	) : BaseOrderTax

	data class OrderItemProductFile(
		val productFileId: Int? = null,
		val maxDownloads: Int? = null,
		val remainingDownloads: Int? = null,
		val expire: Date? = null,
		val name: String? = null,
		val description: String? = null,
		val size: Int? = null,
		val adminUrl: String? = null,
		val customerUrl: String? = null
	)

	data class ProductDimensions(
		val length: Double? = null,
		val width: Double? = null,
		val height: Double? = null
	)

	data class OrderItemDiscounts(
		val discountInfo: OrderItemDiscountInfo? = null,
		val total: Double? = null
	)

	data class OrderItemOptionFile(
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
		val countryName: String? = null,
		val postalCode: String? = null,
		val stateOrProvinceCode: String? = null,
		val stateOrProvinceName: String? = null,
		val phone: String? = null
	)

	data class ShippingOptionInfo(
		val shippingMethodId: String? = null,
		val shippingCarrierName: String? = null,
		val shippingMethodName: String? = null,
		val shippingRate: Double? = null,
		val estimatedTransitTime: String? = null,
		val isPickup: Boolean? = null,
		val pickupInstruction: String? = null
	)

	data class HandlingFeeInfo(
		val name: String? = null,
		val value: Double? = null,
		val description: String? = null
	)

	data class TaxOnShipping(
		override val name: String? = null,
		override val value: Double? = null,
		override val total: Double? = null
	) : BaseOrderTax

	data class UtmData(
		val source: String? = null,
		val campaign: String? = null,
		val medium: String? = null,
		val mcEid: String? = null,
		val mcCid: String? = null
	)

	override fun getModifyKind() = ModifyKind.ReadWrite(UpdatedCart::class)
}
