package com.ecwid.apiclient.v3.dto.cart.request

import com.ecwid.apiclient.v3.dto.common.ApiRequestDTO
import com.ecwid.apiclient.v3.dto.common.BaseOrderTax
import com.ecwid.apiclient.v3.dto.order.enums.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

data class OrderForCalculate(
	val email: String? = null,
	val ipAddress: String? = null,
	val customerId: Int? = null,
	val customerTaxExempt: Boolean? = null,
	val customerTaxId: String? = null,
	val reversedTaxApplied: Boolean? = null,
	val discountCoupon: DiscountCouponInfo? = null,
	val items: List<OrderItem>? = null,
	val billingPerson: PersonInfo? = null,
	val shippingPerson: PersonInfo? = null,
	val discountInfo: List<DiscountInfo>? = null,
	val customSurcharges: List<CustomSurcharge>? = null,
	val shippingOption: ShippingOption? = null,
	val handlingFee: HandlingFee? = null,
	val paymentOptionsDetails: PaymentOption? = null,
	val giftCardCode: String? = null,
	val giftCardId: Int? = null,
	val giftCardUuid: String? = null,
	val giftCardTransactionOrderId: Int? = null,
	val giftCardRedemption: Double? = null,
	val totalBeforeGiftCardRedemption: Double? = null,
	val giftCardDoubleSpending: Boolean? = null,
) : ApiRequestDTO {

	data class DiscountInfo(
		val value: Double? = null,
		val type: DiscountType? = null,
		val base: DiscountBase? = null,
		val orderTotal: Double? = null,
		val description: String? = null,
		val appliesToProducts: List<Int>? = null,
		val appliesToItems: List<Long>? = null,
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
		val needCalculateWholesalePrice: Boolean? = null,
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
		val discounts: List<OrderItemDiscounts>? = null
	)

	data class OrderItemOption(
		val name: String? = null,
		val type: ProductOptionType? = null,
		val value: String? = null,
		val valuesArray: List<String>? = null,
		val files: List<OrderItemOptionFile>? = null,
		val selections: List<SelectionInfo>? = null
	) {
		companion object {

			private val DATE_OPTION_FORMAT = ThreadLocal.withInitial<DateFormat> {
				SimpleDateFormat("yyyy-MM-dd")
			}

			fun createForChoiceOption(
				name: String,
				selection: String,
				files: List<OrderItemOptionFile>?
			): OrderItemOption {
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

			fun createForChoicesOption(
				name: String,
				selections: List<SelectionInfo>,
				files: List<OrderItemOptionFile>?
			): OrderItemOption {
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
		val selectionTitle: String? = null,
		val selectionModifier: Double? = null,
		val selectionModifierType: PriceModifierType? = null
	)

	data class OrderItemTax(
		override val name: String? = null,
		override val value: Double? = null,
		override val total: Double? = null,
		val includeInPrice: Boolean? = null,
		val taxType: OrderItemTaxType? = null,
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

	data class CustomSurcharge(
		val id: String? = null,
		val value: Double? = null,
		val type: SurchargeType = SurchargeType.ABSOLUTE,
		val description: String? = null,
		val taxable: Boolean = false,
	)

	data class ShippingOption(
		val shippingMethodId: String? = null,
		val shippingMethodName: String? = null,
		val shippingRate: Double? = null,
		val pickupInstruction: String? = null,
		val fulfillmentType: FulfillmentType = FulfillmentType.SHIPPING,
	)

	data class HandlingFee(
		val name: String? = null,
		val value: Double? = null,
		val description: String? = null,
		val taxes: List<HandlingFeeTax>? = null,
	)

	data class HandlingFeeTax(
		val name: String? = null,
		val value: Double? = null,
		val total: Double? = null,
	)

	data class PaymentOption(
		val paymentId: String? = null,
	)
}
