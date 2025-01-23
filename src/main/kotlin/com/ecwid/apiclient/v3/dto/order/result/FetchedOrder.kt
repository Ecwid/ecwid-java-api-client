package com.ecwid.apiclient.v3.dto.order.result

import com.ecwid.apiclient.v3.dto.common.ApiFetchedDTO
import com.ecwid.apiclient.v3.dto.common.ApiFetchedDTO.ModifyKind
import com.ecwid.apiclient.v3.dto.common.ApiResultDTO
import com.ecwid.apiclient.v3.dto.common.ExtendedOrderTax
import com.ecwid.apiclient.v3.dto.common.OrderedStringToListStringMap
import com.ecwid.apiclient.v3.dto.common.OrderedStringToStringMap
import com.ecwid.apiclient.v3.dto.order.enums.*
import com.ecwid.apiclient.v3.dto.order.request.UpdatedOrder
import java.util.*

data class FetchedOrder(
	val id: String? = null,
	val internalId: Long? = null,

	val orderNumber: Int = 0,
	val vendorOrderNumber: String? = null,
	val publicUid: String? = null,
	val email: String? = null,
	val ipAddress: String? = null,
	val hidden: Boolean? = null,
	val createDate: Date? = null,
	val createTimestamp: Long? = null, // TODO Figure out how to test
	val updateDate: Date? = null,
	val updateTimestamp: Long? = null, // TODO Figure out how to test

	val refererUrl: String? = null,
	val refererId: String? = null,
	val globalReferer: String? = null,
	val affiliateId: String? = null,
	val additionalInfo: OrderedStringToStringMap? = null,
	val extraFields: OrderedStringToStringMap? = null, // TODO Figure out how to test
	val orderExtraFields: List<ExtraFieldsInfo>? = null,

	val orderComments: String? = null,
	val privateAdminNotes: String? = null,

	val fulfillmentStatus: OrderFulfillmentStatus? = null,
	val externalFulfillment: Boolean? = null,
	val externalOrderId: String? = null,
	val latestShipDate: Date? = null,
	val latestDeliveryDate: Date? = null,
	val trackingNumber: String? = null,
	val trackingUrl: String? = null,
	val pickupTime: Date? = null,

	val paymentStatus: OrderPaymentStatus? = null,
	val paymentMethod: String? = null,
	val paymentModule: String? = null,
	val paymentParams: OrderedStringToStringMap? = null,
	val paymentMessage: String? = null,
	val paymentSubtype: String? = null,
	val creditCardStatus: CreditCardStatus? = null,
	val externalTransactionId: String? = null,
	val externalTransactionUrl: String? = null,
	val referenceTransactionId: String? = null,
	val ticket: Int? = null,
	val ebayId: String? = null,

	val customerId: Int? = null, // TODO Figure out how to test
	val customerGroup: String? = null,
	val customerGroupId: Long? = null,
	val acceptMarketing: Boolean? = null,

	val giftCardRedemption: Double? = null,
	val totalBeforeGiftCardRedemption: Double? = null,
	val giftCardDoubleSpending: Boolean? = null,
	val giftCardCode: String? = null,
	val giftCardId: Int? = null,
	val giftCardUuid: String? = null,

	val total: Double? = null,
	val totalWithoutTax: Double? = null,
	val subtotal: Double? = null,
	val subtotalWithoutTax: Double? = null,
	val usdTotal: Double? = null, // TODO Figure out how to test

	val tax: Double? = null,
	val availableTaxes: List<Taxes>? = null,
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
	val customDiscount: List<Double>? = null,

	val items: List<OrderItem>? = null,

	val billingPerson: PersonInfo? = null,
	val shippingPerson: PersonInfo? = null,

	val shippingOption: ShippingOption? = null,
	val availableShippingOptions: List<ShippingOption>? = null,
	val taxesOnShipping: List<BaseOrderItemTax> = listOf(),
	val handlingFee: HandlingFee? = null,
	val shipments: List<Shipment>? = null,

	val customSurcharges: List<Surcharge> = listOf(),

	val refundedAmount: Double? = null,
	val refunds: List<RefundInfo>? = null,

	val utmData: UtmData? = null,
	val invoices: List<TaxInvoice>? = null,
	val predictedPackage: List<PredictedPackage>? = null,

	val pricesIncludeTax: Boolean? = null,
	val disableAllCustomerNotifications: Boolean? = null,
	val externalOrderData: ExternalOrderData? = null,
	val paymentReference: String? = null,
	val shippingLabelAvailableForShipment: Boolean = false,
	val loyalty: Loyalty? = null,
	val customerFiscalCode: String? = null,
	val lang: String? = null,

	) : ApiFetchedDTO, ApiResultDTO {

	data class CreditCardStatus(
		val avsMessage: String? = null,
		val cvvMessage: String? = null
	)

	data class DiscountInfo(
		val value: Double? = null,
		val type: DiscountType? = null,
		val base: DiscountBase? = null,
		val orderTotal: Double? = null,
		val description: String? = null,
		val appliesToProducts: List<Int>? = null,
		val appliesToItems: List<Long>? = null,
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
		val categories: List<Long>? = null
	)

	data class OrderItem(
		val id: Long? = null, // TODO Figure out how to test

		val productId: Int? = null,
		val categoryId: Long? = null,

		val price: Double? = null,
		val productPrice: Double? = null,
		val isCustomerSetPrice: Boolean? = null,
		val selectedPrice: SelectedPrice? = null,
		val priceWithoutTax: Double? = null,
		val shipping: Double? = null,
		val tax: Double? = null,
		val fixedShippingRate: Double? = null,
		val couponAmount: Double? = null,

		val sku: String? = null,
		val name: String? = null,
		val nameTranslated: OrderedStringToStringMap? = null,
		val shortDescription: String? = null,
		val shortDescriptionTranslated: OrderedStringToStringMap? = null,
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
		val giftCard: Boolean? = null,

		val recurringChargeSettings: RecurringChargeSettings? = null,
		val subscriptionId: Long? = null,

		val selectedOptions: List<OrderItemSelectedOption>? = null,
		val combinationId: Int? = null,
		val files: List<OrderItemProductFile>? = null,
		val taxable: Boolean? = null,
		val taxes: List<OrderItemTax>? = null,
		val dimensions: ProductDimensions? = null,
		val discountsAllowed: Boolean? = null,
		val discounts: List<OrderItemDiscounts>? = null,
		val externalReferenceId: String? = null,
		val isPreorder: Boolean? = null,
		val attributes: List<OrderItemAttributeValue>? = null
	)

	data class RecurringChargeSettings(
		val recurringInterval: RecurringSubscriptionInterval = RecurringSubscriptionInterval.MONTH,
		val recurringIntervalCount: Int = 1,
		val subscriptionPriceWithSignUpFee: Double? = null,
		val signUpFee: Double? = null
	)

	data class OrderItemSelectedOption(
		val name: String? = null,
		val nameTranslated: OrderedStringToStringMap? = null,
		val type: ProductOptionType? = null,
		val value: String? = null,
		val valueTranslated: OrderedStringToStringMap? = null,
		val valuesArray: List<String>? = null,
		val valuesArrayTranslated: OrderedStringToListStringMap? = null,
		val selections: List<OrderItemSelectionInfo>? = null,
		val files: List<OrderItemOptionFile>? = null
	)

	data class OrderItemSelectionInfo(
		val selectionTitle: String? = null,
		val selectionModifier: Double? = null,
		val selectionModifierType: PriceModifierType? = null
	)

	data class BaseOrderItemTax(
		override val name: String? = null,
		override val value: Double? = null,
		override val total: Double? = null,
		override val includeInPrice: Boolean? = null,
		val taxType: OrderItemTaxType? = null,
	) : ExtendedOrderTax

	data class OrderItemTax(
		override val name: String? = null,
		override val value: Double? = null,
		override val total: Double? = null,
		val taxOnDiscountedSubtotal: Double? = null,
		val taxOnShipping: Double? = null,
		override val includeInPrice: Boolean? = null,
		val sourceTaxRateId: Int? = null,
		val sourceTaxRateType: RateType? = null,
		val taxType: OrderItemTaxType? = null,
	) : ExtendedOrderTax {
		enum class RateType {
			AUTO,
			MANUAL,
			CUSTOM,
			LEGACY
		}
	}


	data class HandlingFeeTax(
		override val name: String? = null,
		override val value: Double? = null,
		override val total: Double? = null,
		override val includeInPrice: Boolean? = null,
	) : ExtendedOrderTax

	data class ProductDimensions(
		val length: Double? = null,
		val width: Double? = null,
		val height: Double? = null
	)

	data class OrderItemDiscounts(
		val discountInfo: DiscountInfo? = null,
		val total: Double? = null
	)

	data class OrderItemOptionFile(
		val id: Int? = null,
		val name: String? = null,
		val size: Int? = null,
		val url: String? = null
	)

	data class OrderItemAttributeValue(
		val id: Int? = null,
		val name: String? = null,
		val nameTranslated: HashMap<String, String>? = null,
		val value: String? = null,
		val valueTranslated: HashMap<String, String>? = null,
	)

	data class OrderItemProductFile(
		val productFileId: Long? = null,
		val maxDownloads: Int? = null,
		val remainingDownloads: Int? = null,
		val expire: Date? = null,
		val name: String? = null,
		val description: String? = null,
		val size: Long? = null,
		val adminUrl: String? = null,
		val customerUrl: String? = null
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
		val shippingMethodId: String? = null,
		val shippingCarrierName: String? = null,
		val shippingMethodName: String? = null,
		val shippingRate: Double? = null,
		val shippingRateWithoutTax: Double = 0.0,
		val estimatedTransitTime: String? = null,
		val isPickup: Boolean? = null,
		val pickupInstruction: String? = null,
		val fulfillmentType: FulfillmentType? = null,
		val locationId: String? = null,
		val localizedLabel: String? = null,
		val isShippingLimit: Boolean? = null,
		val scheduled: Boolean? = null,
		val scheduledTimePrecisionType: ScheduledTimePrecisionType? = null,
	)

	data class HandlingFee(
		val name: String? = null,
		val value: Double? = null,
		val valueWithoutTax: Double = 0.0,
		val description: String? = null,
		val taxes: List<HandlingFeeTax> = listOf()
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

	data class Surcharge(
		val id: String = "",
		val value: Double = 0.0,
		val type: SurchargeType = SurchargeType.PERCENT,
		val total: Double = 0.0,
		val totalWithoutTax: Double = 0.0,
		val description: String = "",
		val descriptionTranslated: String? = null,
		val taxable: Boolean = true,
		val taxes: List<BaseOrderItemTax> = listOf()
	)

	data class SelectedPrice(
		val value: Double? = null
	)

	data class Shipment(
		val id: String? = null,
		val created: String? = null,
		val shipFrom: PersonInfo? = null,
		val shipTo: PersonInfo? = null,
		val parcel: Parcel? = null,
		val shippingService: ShippingServiceInfo? = null,
		val tracking: TrackingInfo? = null,
		val label: ShippingLabelInfo? = null
	)

	data class Parcel(
		val length: Double? = null,
		val width: Double? = null,
		val height: Double? = null,
		val weight: Double? = null,
		val dimensionUnit: DimensionUnit? = null,
		val weightUnit: WeightUnit? = null,
		val template: ParcelTemplate? = null
	)

	enum class WeightUnit {
		CARAT, GRAM, OUNCE, POUND, KILOGRAM
	}

	enum class DimensionUnit {
		MM, CM, IN, YD
	}

	enum class ParcelTemplate {
		USPS_FlatRateEnvelope,
		USPS_FlatRatePaddedEnvelope,
		USPS_FlatRateLegalEnvelope,
		USPS_FlatRateWindowEnvelope,
		USPS_FlatRateGiftCardEnvelope,
		USPS_FlatRateCardboardEnvelope,
		USPS_SmallFlatRateBox,
		USPS_SmallFlatRateEnvelope,
		USPS_MediumFlatRateBox1,
		USPS_MediumFlatRateBox2,
		USPS_LargeFlatRateBox,
		USPS_LargeFlatRateBoardGameBox,
		USPS_LargeVideoFlatRateBox,
		USPS_APOFlatRateBox,
		USPS_RegionalRateBoxA1,
		USPS_RegionalRateBoxA2,
		USPS_RegionalRateBoxB1,
		USPS_RegionalRateBoxB2,
		USPS_SoftPack
	}

	data class ShippingServiceInfo(
		val carrier: String? = null,
		val carrierName: String? = null,
		val carrierServiceCode: String? = null,
		val carrierServiceName: String? = null
	)

	data class TrackingInfo(
		val trackingNumber: String? = null,
		val trackingUrl: String? = null,
		val estimatedDays: Int? = null
	)

	data class ShippingLabelInfo(
		val labelUrl: String? = null,
		val labelFileType: LabelFileType? = null,
		val commercialInvoiceUrl: String? = null,
		val billingTransactionId: String? = null
	)

	enum class LabelFileType {
		PNG, PDF, PDF_4x6, ZPLII
	}

	data class ExtraFieldsInfo(
		val customerInputType: String? = null,
		val title: String? = null,
		val id: String? = null,
		val value: String? = null,
		val orderDetailsDisplaySection: String? = null,
		val orderBy: String? = null
	)

	data class Taxes(
		val id: Int? = null,
		val name: String? = null,
		val enabled: Boolean? = null,
		val includeInPrice: Boolean? = null,
		val useShippingAddress: Boolean? = null,
		val taxShipping: Boolean? = null,
		val appliedByDefault: Boolean? = null,
		val defaultTax: Double? = null,
		val rules: List<TaxRule>? = null
	)

	data class TaxRule(
		val zoneId: String? = null,
		val tax: Double? = null
	)

	data class TaxInvoice(
		val internalId: Long? = null,
		val id: String? = null,
		val created: String? = null,
		val link: String? = null,
		val type: Type? = null
	) {
		enum class Type {
			SALE,
			FULL_CANCEL
		}
	}

	data class PredictedPackage(
		val height: Double? = null,
		val width: Double? = null,
		val length: Double? = null,
		val weight: Double? = null,
		val declaredValue: Double? = null
	)

	override fun getModifyKind() = ModifyKind.ReadWrite(UpdatedOrder::class)

	data class ExternalOrderData(
		val externalFulfillment: Boolean? = null,
		val externalOrderId: String? = null,
		val refererId: String? = null,
		val platformSpecificFields: HashMap<String, String>? = null,
		val refererChannel: String? = null
	)

	data class Loyalty(
		val earned: Double? = null,
		val redemption: LoyaltyRedemption? = null,
		val balance: Double? = null
	)

	data class LoyaltyRedemption(
		val id: String? = null,
		val amount: Double? = null,
		val cancelled: Boolean? = null,
	)
}
