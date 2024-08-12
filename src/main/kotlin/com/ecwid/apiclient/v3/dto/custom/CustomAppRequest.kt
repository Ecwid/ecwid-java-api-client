package com.ecwid.apiclient.v3.dto.custom

import com.ecwid.apiclient.v3.dto.cart.result.FetchedCart
import com.ecwid.apiclient.v3.dto.common.*
import com.ecwid.apiclient.v3.dto.order.enums.*
import com.ecwid.apiclient.v3.dto.product.enums.AttributeValueLocation
import com.ecwid.apiclient.v3.dto.producttype.enums.AttributeType
import com.ecwid.apiclient.v3.dto.variation.result.FetchedVariation
import java.util.*

data class CustomAppRequest(
	val storeId: Int? = null,
	val merchantAppSettings: MerchantAppSettings? = null,
	val cart: Cart? = null,
	val lang: String? = null
) : ApiRequestDTO {
	data class Cart(
		val id: String? = null,
		val orderNumber: Int? = null,
		val vendorOrderNumber: String? = null,
		val subtotal: Double? = null,
		val ipAddress: String? = null,
		val couponDiscount: Double? = null,
		val paymentStatus: String? = null,
		val fulfillmentStatus: String? = null,
		val refererUrl: String? = null,
		val orderComments: String? = null,
		val volumeDiscount: Double? = null,
		val membershipBasedDiscount: Double? = null,
		val totalAndMembershipBasedDiscount: Double? = null,
		val discount: Double? = null,
		val customerGroupId: Int? = null,
		val customerGroup: String? = null,
		val customerId: Int? = null,
		val customerEmail: String? = null,
		val discountCoupon: DiscountCouponInfo? = null,
		val discountInfo: List<Discount>? = null,
		val handlingFee: HandlingFeeInfo? = null,
		val hidden: Boolean? = null,
		val items: List<OrderItem>? = null,
		val shippingAddress: ShippingAddress? = null,
		val originAddress: OriginAddress? = null,
		val weight: Double? = null,
		val weightUnit: String? = null,
		val dimensionUnit: String? = null,
		val currency: String? = null,
		val predictedPackages: List<ShippingPackage>? = null,
		val paymentMethod: String? = null,
		val extraFields: Map<String, String>? = null
	)

	data class OrderItem(
		val weight: Double? = null,
		val price: Double? = null,
		val amount: Int? = null,
		val productId: Int? = null,
		val combinationId: Long? = null,
		val name: String? = null,
		val categoryId: Long? = null,
		val sku: String? = null,
		val selectedOptions: List<OrderItemOption>? = null,
		val dimensions: ProductDimensions? = null,
		val productPrice: Double? = null,
		val categoryIds: List<Long>? = null,
		val categories: List<Category>? = null,
		val quantity: Int? = null,
		val unlimited: Boolean? = null,
		val inStock: Boolean? = null,
		val priceInProductList: Double? = null,
		val isShippingRequired: Boolean? = null,
		val productClassId: Int? = null,
		val enabled: Boolean? = null,
		val warningLimit: Int? = null,
		val fixedShippingRateOnly: Boolean? = null,
		val fixedShippingRate: Double? = null,

		val options: List<ProductOption>? = null,
		val wholesalePrices: List<WholesalePriceEntry>? = null,
		val compareToPrice: Double? = null,
		val url: String? = null,
		val created: String? = null,
		val updated: String? = null,
		val createTimestamp: Long? = null,
		val updateTimestamp: Long? = null,
		val defaultCombinationId: Int? = null,
		val imageUrl: String? = null,
		val thumbnailUrl: String? = null,
		val smallThumbnailUrl: String? = null,
		val hdThumbnailUrl: String? = null,
		val originalImageUrl: String? = null,
		val originalImage: PictureInfo? = null,
		val borderInfo: BorderInfo? = null,
		val galleryImages: List<GalleryImage>? = null,
		val defaultCategoryId: Long? = null,
		val seoTitle: String? = null,
		val seoDescription: String? = null,
		val favorites: FavoritesInfo? = null,
		val attributes: List<AttributeValue>? = null,
		val relatedProducts: RelatedProducts? = null,
		val combinations: List<FetchedVariation>? = null,
		val showOnFrontpage: Int? = null,
		val discountsAllowed: Boolean? = null,
		val externalReferenceId: String? = null
	)

	data class Category(
		val id: Long? = null,
		val enabled: Boolean? = null
	)

	data class ShippingPackage(
		val length: Double? = null,
		val width: Double? = null,
		val height: Double? = null,
		val weight: Double? = null,
		val declaredValue: Double? = null
	)

	data class ShippingOption(
		val shippingCarrierName: String? = null,
		val shippingMethodName: String? = null,
		val shippingRate: Double? = null,
		val estimatedTransitTime: String? = null,
		val isPickup: Boolean? = null,
		val pickupInstruction: String? = null
	)

	data class ShippingAddress(
		val street: String? = null,
		val city: String? = null,
		val countryCode: String? = null,
		val postalCode: String? = null,
		val stateOrProvinceCode: String? = null,
		val stateOrProvinceName: String? = null
	)

	data class ProductOption(
		val type: String? = null,
		val name: String? = null,
		val defaultChoice: Int? = null,
		val required: Boolean? = null,
		val choices: List<ProductOptionChoice>? = null
	)

	data class ProductOptionChoice(
		val text: String? = null,
		val priceModifier: Double? = null,
		val priceModifierType: String? = null
	)

	data class RelatedProducts(
		val productIds: List<Int>? = null,
		val relatedCategory: RelatedCategory? = null
	)

	data class RelatedCategory(
		val enabled: Boolean? = null,
		val categoryId: Long? = null,
		val productCount: Int? = null
	)

	data class OriginAddress(
		val street: String? = null,
		val city: String? = null,
		val countryCode: String? = null,
		val postalCode: String? = null,
		val stateOrProvinceCode: String? = null
	)

	class MerchantAppSettings(
		settings: Map<String, String>? = null
	) : OrderedStringToStringMap(settings)

	data class Discount(
		val orderTotal: Double? = null,
		val value: Double? = null,
		val type: DiscountType? = null,
		val base: DiscountBase? = null,
		val membershipId: Int? = null,
		val description: String? = null
	)

	enum class DiscountType {
		ABS, PERCENT
	}

	enum class DiscountBase {
		ON_TOTAL, ON_MEMBERSHIP, ON_TOTAL_AND_MEMBERSHIP, CUSTOM
	}

	data class FavoritesInfo(
		val count: Int? = null,
		val displayedCount: String? = null
	)

	data class WholesalePriceEntry(
		val quantity: Int? = null,
		val price: Double? = null
	)

	data class BorderInfo(
		val dominatingColor: Color? = null,
		val homogeneity: Boolean? = false
	)

	data class Color(
		val red: Int? = null,
		val green: Int? = null,
		val blue: Int? = null,
		val alpha: Int? = null
	)

	data class DiscountCouponInfo(
		val id: Long? = null,
		val ownerId: Long? = null,
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
		@Deprecated(
			"This field is added for backward compatibility only. Don't use it.",
			replaceWith = ReplaceWith("catalogLimit")
		)
		val legacyCatalogLimit: DiscountCouponCatalogLimit? = null,
		val catalogLimit: DiscountCouponCatalogLimit? = null,
		val repeatCustomerOnly: Boolean? = null,
		val newCustomerOnly: Boolean? = null,
		val parentId: Int? = null,
		val customerId: Long? = null,
		val cartId: Int? = null,
		val updateDate: Date? = null,
	)

	data class DiscountCouponCatalogLimit(
		val products: List<Int>? = null,
		val categories: List<Long>? = null
	)

	data class HandlingFeeInfo(
		val name: String? = null,
		val value: Double? = null,
		val description: String? = null
	)

	data class OrderItemOption(
		val name: String? = null,
		val nameTranslated: OrderedStringToStringMap? = null,
		val type: ProductOptionType? = null,
		val value: String? = null,
		val valueTranslated: OrderedStringToStringMap? = null,
		val valuesArray: List<String>? = null,
		val valuesArrayTranslated: OrderedStringToListStringMap? = null,
		val files: List<FetchedCart.OrderItemOptionFile>? = null,
		val selections: List<FetchedCart.SelectionInfo>? = null
	)

	data class GalleryImage(
		val id: Long = 0,
		val orderBy: Int = 0,
		val alt: String? = null,
		val width: Int = 0,
		val height: Int = 0,
		val url: String = "",
		val thumbnail: String? = null,
		val originalImageUrl: String = "",
		val imageUrl: String = "",
		val hdThumbnailUrl: String = "",
		val thumbnailUrl: String = "",
		val smallThumbnailUrl: String = "",
		val borderInfo: BorderInfo? = null
	)

	data class AttributeValue(
		override val id: Int? = null,
		override val name: String? = null,
		override val type: AttributeType? = null,
		override val value: String? = null,
		override val valueTranslated: LocalizedValueMap? = null,
		override val show: AttributeValueLocation? = null
	) : FetchedAttributeValue

	data class ProductDimensions(
		val length: Double? = null,
		val width: Double? = null,
		val height: Double? = null
	)

}
