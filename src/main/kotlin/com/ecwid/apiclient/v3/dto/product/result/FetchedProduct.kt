package com.ecwid.apiclient.v3.dto.product.result

import com.ecwid.apiclient.v3.dto.common.*
import com.ecwid.apiclient.v3.dto.common.ApiFetchedDTO.ModifyKind
import com.ecwid.apiclient.v3.dto.product.enums.*
import com.ecwid.apiclient.v3.dto.product.request.UpdatedProduct
import com.ecwid.apiclient.v3.dto.producttype.enums.AttributeType
import com.ecwid.apiclient.v3.dto.variation.result.FetchedVariation
import java.util.*

data class FetchedProduct(
	val id: Int = 0,
	val name: String = "",
	val nameTranslated: LocalizedValueMap? = null,
	val description: String? = null,
	val descriptionTranslated: LocalizedValueMap? = null,
	val sku: String = "",
	val isSampleProduct: Boolean? = null,
	val url: String? = null,
	val customSlug: String? = null,

	val created: Date = Date(),
	val createTimestamp: Int = 0,
	val updated: Date = Date(),
	val updateTimestamp: Int = 0,

	val enabled: Boolean? = null,
	val quantity: Int? = null,
	val locationInventory: Map<String, Int>? = null,
	val outOfStockVisibilityBehaviour: OutOfStockVisibilityBehaviour? = null,
	val unlimited: Boolean? = null,
	val inStock: Boolean? = null,
	val warningLimit: Int? = null,

	val categoryIds: List<Long>? = null,
	val categories: List<CategoryInfo>? = null,
	val defaultCategoryId: Long? = null,
	val showOnFrontpage: Int? = null,

	val price: Double? = null,
	val priceInProductList: Double? = null, // TODO Figure out how to test
	val defaultDisplayedPrice: Double? = null, // TODO Figure out how to test
	val defaultDisplayedPriceFormatted: String? = null, // TODO Figure out how to test
	val costPrice: Double = 0.0,
	val wholesalePrices: List<WholesalePrice>? = null,

	val compareToPrice: Double? = null,

	@Deprecated(
		message = "Use field 'defaultDisplayedCompareToPriceDiscount' instead",
		replaceWith = ReplaceWith("defaultDisplayedCompareToPriceDiscount")
	)
	val compareToPriceDiscount: Double? = null, // TODO Figure out how to test

	@Deprecated(
		message = "Use field 'defaultDisplayedCompareToPriceDiscountFormatted' instead",
		replaceWith = ReplaceWith("defaultDisplayedCompareToPriceDiscountFormatted")
	)
	val compareToPriceDiscountFormatted: String? = null, // TODO Figure out how to test

	@Deprecated(
		message = "Use field 'defaultDisplayedCompareToPriceDiscountPercent' instead",
		replaceWith = ReplaceWith("defaultDisplayedCompareToPriceDiscountPercent")
	)
	val compareToPriceDiscountPercent: Double? = null, // TODO Figure out how to test

	@Deprecated(
		message = "Use field 'defaultDisplayedCompareToPriceDiscountPercentFormatted' instead",
		replaceWith = ReplaceWith("defaultDisplayedCompareToPriceDiscountPercentFormatted")
	)
	val compareToPriceDiscountPercentFormatted: String? = null, // TODO Figure out how to test

	@Deprecated(
		message = "Use field 'defaultDisplayedCompareToPriceFormatted' instead",
		replaceWith = ReplaceWith("defaultDisplayedCompareToPriceFormatted")
	)
	val compareToPriceFormatted: String? = null, // TODO Figure out how to test

	val defaultDisplayedCompareToPrice: Double? = null,
	val defaultDisplayedCompareToPriceFormatted: String? = null,
	val defaultDisplayedCompareToPriceDiscount: Double? = null,
	val defaultDisplayedCompareToPriceDiscountFormatted: String? = null,
	val defaultDisplayedCompareToPriceDiscountPercent: Double? = null,
	val defaultDisplayedCompareToPriceDiscountPercentFormatted: String? = null,

	val lowestPrice: Double? = null,
	val defaultDisplayedLowestPrice: Double? = null,
	val defaultDisplayedLowestPriceFormatted: String? = null,

	val lowestPriceSettings: LowestPriceSettings = LowestPriceSettings(),

	val weight: Double? = null,
	val dimensions: ProductDimensions? = null,
	val volume: Double = 0.0,

	val shippingPreparationTime: ShippingPreparationTime? = null,
	val showDeliveryTimeInStorefront: Boolean? = null,

	val shipping: ShippingSettings? = null,
	val isShippingRequired: Boolean? = null,
	val hasFreeShipping: Boolean? = null,

	val productClassId: Int? = null,
	val attributes: List<AttributeValue>? = null,

	val seoTitle: String? = null,
	val seoTitleTranslated: LocalizedValueMap? = null,
	val seoDescription: String? = null,
	val seoDescriptionTranslated: LocalizedValueMap? = null,

	val options: List<ProductOption>? = null,
	val tax: TaxInfo? = null,
	val relatedProducts: RelatedProducts? = null,

	val originalImage: PictureInfo? = null,
	val galleryImages: List<GalleryImage>? = null,
	val borderInfo: BorderInfo? = null,
	val media: ProductMedia? = null,
	val files: List<ProductFile>? = null,
	val favorites: FavoritesStats? = null, // TODO Figure out how to test

	val defaultCombinationId: Int? = null, // TODO implement combinations support
	val combinations: List<FetchedVariation>? = null, // TODO implement combinations support
	val isGiftCard: Boolean? = null, // TODO from ECWID-67826: this flag is read-only. API for creating gift cards in the plans (BACKLOG-4157)
	val discountsAllowed: Boolean? = null,
	val subtitle: String? = null,
	val ribbon: Ribbon? = null,
	val ribbonTranslated: LocalizedValueMap? = null,
	val subtitleTranslated: LocalizedValueMap? = null,
	val nameYourPriceEnabled: Boolean? = null,
	val customPriceTiers: List<CustomPriceTier>? = null,
	val priceDefaultTier: Int? = null,
	val subscriptionSettings: SubscriptionSettings? = null,
	val googleProductCategory: Int? = null,
	val googleProductCategoryName: String? = null,
	val productCondition: ProductCondition = ProductCondition.NEW,
	val externalReferenceId: String? = null,
	val customsHsTariffCode: String? = null,
	val minPurchaseQuantity: Int? = null,
	val maxPurchaseQuantity: Int? = null,
	val reviewsCollectingAllowed: Boolean? = null,
	val rating: Double? = null,
	val reviewsModerated: Int? = null,
	val reviewsPublished: Int? = null,
) : ApiFetchedDTO, ApiResultDTO {

	data class BorderInfo(
		val dominatingColor: Color = Color(),
		val homogeneity: Boolean = false
	)

	data class Color(
		val red: Int = 0,
		val green: Int = 0,
		val blue: Int = 0,
		val alpha: Int = 0
	)

	data class Ribbon(
		val text: String? = null,
		val color: String? = null
	)

	data class SubscriptionSettings(
		val subscriptionAllowed: Boolean = false,
		val oneTimePurchaseAllowed: Boolean = false,
		val recurringChargeSettings: List<RecurringChargeSettings> = emptyList(),
		val oneTimePurchasePrice: Double? = null,
		val oneTimePurchasePriceFormatted: String? = null,
		val oneTimePurchaseMarkup: Double? = null,
		val oneTimePurchaseMarkupFormatted: String? = null,
		val oneTimePurchaseMarkupPercent: Double? = null,
		val oneTimePurchaseMarkupPercentFormatted: String? = null,
		val displayedOneTimePurchaseMarkupPercent: Double? = null,
		val displayedOneTimePurchaseMarkupPercentFormatted: String? = null
	)

	data class RecurringChargeSettings(
		val recurringInterval: RecurringSubscriptionInterval = RecurringSubscriptionInterval.MONTH,
		val recurringIntervalCount: Int = 1,
		val subscriptionPriceWithSignUpFee: Double? = null,
		val subscriptionPriceWithSignUpFeeFormatted: String? = null,
		val signUpFee: Double? = null,
		val signUpFeeFormatted: String? = null
	)

	data class WholesalePrice(
		val quantity: Int = 0,
		val price: Double = 0.0
	)

	data class CategoryInfo(
		val id: Long = 0L,
		val enabled: Boolean = true
	)

	data class TaxInfo(
		val taxable: Boolean = true,
		val defaultLocationIncludedTaxRate: Double = 0.0,
		val enabledManualTaxes: List<Int> = listOf(),
		val taxClassCode: String = "",
	)

	sealed class ProductOption(
		val type: ProductOptionType? = null
	) {

		abstract val name: String
		abstract val nameTranslated: LocalizedValueMap?
		abstract val required: Boolean

		interface ChoiceBased {
			val choices: List<ProductOptionChoice>
			val defaultChoice: Int?
		}

		data class SelectOption(
			override val name: String = "",
			override val nameTranslated: LocalizedValueMap? = null,
			override val choices: List<ProductOptionChoice> = listOf(),
			override val defaultChoice: Int = 0,
			override val required: Boolean = false
		) : ProductOption(ProductOptionType.SELECT), ChoiceBased

		data class SizeOption(
			override val name: String = "",
			override val nameTranslated: LocalizedValueMap? = null,
			override val choices: List<ProductOptionChoice> = listOf(),
			override val defaultChoice: Int = 0,
			override val required: Boolean = false
		) : ProductOption(ProductOptionType.SIZE), ChoiceBased

		data class RadioOption(
			override val name: String = "",
			override val nameTranslated: LocalizedValueMap? = null,
			override val choices: List<ProductOptionChoice> = listOf(),
			override val defaultChoice: Int = 0,
			override val required: Boolean = false
		) : ProductOption(ProductOptionType.RADIO), ChoiceBased

		data class CheckboxOption(
			override val name: String = "",
			override val nameTranslated: LocalizedValueMap? = null,
			override val choices: List<ProductOptionChoice> = listOf(),
			override val defaultChoice: Int? = null,
			override val required: Boolean = false
		) : ProductOption(ProductOptionType.CHECKBOX), ChoiceBased

		data class TextFieldOption(
			override val name: String = "",
			override val nameTranslated: LocalizedValueMap? = null,
			override val required: Boolean = false
		) : ProductOption(ProductOptionType.TEXTFIELD)

		data class TextAreaOption(
			override val name: String = "",
			override val nameTranslated: LocalizedValueMap? = null,
			override val required: Boolean = false
		) : ProductOption(ProductOptionType.TEXTAREA)

		data class DateOption(
			override val name: String = "",
			override val nameTranslated: LocalizedValueMap? = null,
			override val required: Boolean = false
		) : ProductOption(ProductOptionType.DATE)

		data class FilesOption(
			override val name: String = "",
			override val nameTranslated: LocalizedValueMap? = null,
			override val required: Boolean = false
		) : ProductOption(ProductOptionType.FILES)
	}

	data class ProductOptionChoice(
		val text: String = "",
		val textTranslated: LocalizedValueMap? = null,
		val priceModifier: Double = 0.0,
		val priceModifierType: PriceModifierType = PriceModifierType.ABSOLUTE
	)

	data class ShippingSettings(
		val type: ShippingSettingsType? = null,
		val methodMarkup: Double? = null,
		val flatRate: Double? = null,
		val disabledMethods: List<String>? = null,
		val enabledMethods: List<String>? = null
	)

	data class AttributeValue(
		override val id: Int? = null,
		override val name: String? = null,
		override val type: AttributeType? = null,
		override val value: String? = null,
		override val valueTranslated: LocalizedValueMap? = null,
		override val show: AttributeValueLocation? = null
	) : FetchedAttributeValue

	data class RelatedProducts(
		val productIds: List<Int>? = null,
		val relatedCategory: RelatedCategory? = null
	)

	data class RelatedCategory(
		val enabled: Boolean? = null,
		val categoryId: Long? = null,
		val productCount: Int? = null
	)

	data class ProductDimensions(
		val length: Double? = null,
		val width: Double? = null,
		val height: Double? = null
	)

	data class ShippingPreparationTime(
		val shippingPreparationTimeForInStockItemDays: String? = null,
		val shippingPreparationTimeForOutOfStockItemDays: String? = null,
		val pickupPreparationTimeForInStockItemInMinutes: Int? = null,
		val localDeliveryPreparationTimeForInStockItemInMinutes: Int? = null,
	)

	data class CustomPriceTier(
		val value: Double = 0.0
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
		val borderInfo: BorderInfo? = null,
	)

	data class ProductMedia(
		val images: List<ProductImage> = listOf(),
		val videos: List<ProductVideo> = listOf(),
	)

	data class ProductImage(
		val id: String = "0",
		val orderBy: Int = 0,
		val isMain: Boolean = false,
		val image160pxUrl: String? = null,
		val image400pxUrl: String? = null,
		val image800pxUrl: String? = null,
		val image1500pxUrl: String? = null,
		val imageOriginalUrl: String? = null,
		val alt: FetchedAlt? = null
	)

	data class ProductVideo(
		val id: String = "0",
		val url: String = "",
		val embedHtml: String = "",
		val videoCoverId: String? = null,
		val image160pxUrl: String? = null,
		val image400pxUrl: String? = null,
		val image800pxUrl: String? = null,
		val image1500pxUrl: String? = null,
		val imageOriginalUrl: String? = null,
		val providerName: String? = null,
		val title: String? = null,
	)

	data class ProductFile(
		val id: Int = 0,
		val name: String = "",
		val description: String = "",
		val size: Long = 0,
		val adminUrl: String = ""
	)

	data class FavoritesStats(
		val count: Int? = null,
		val displayedCount: String? = null
	)

	override fun getModifyKind() = ModifyKind.ReadWrite(UpdatedProduct::class)

	data class LowestPriceSettings(
		val lowestPriceEnabled: Boolean = false,
		val manualLowestPrice: Double? = null,
		val defaultDisplayedLowestPrice: Double? = null,
		val defaultDisplayedLowestPriceFormatted: String? = null,
		val automaticLowestPrice: Double? = null,
		val defaultDisplayedAutomaticLowestPrice: Double? = null,
		val defaultDisplayedAutomaticLowestPriceFormatted: String? = null,
	)
}
