package com.ecwid.apiclient.v3.dto.product.request

import com.ecwid.apiclient.v3.dto.common.NullableUpdatedValue
import com.ecwid.apiclient.v3.dto.common.ApiUpdatedDTO
import com.ecwid.apiclient.v3.dto.common.ApiUpdatedDTO.ModifyKind
import com.ecwid.apiclient.v3.dto.common.LocalizedValueMap
import com.ecwid.apiclient.v3.dto.common.ProductCondition
import com.ecwid.apiclient.v3.dto.product.enums.*
import com.ecwid.apiclient.v3.dto.product.result.FetchedProduct

data class UpdatedProduct(
	val name: String? = null,
	val nameTranslated: LocalizedValueMap? = null,
	val description: String? = null,
	val descriptionTranslated: LocalizedValueMap? = null,
	val sku: String? = null,
	val isSampleProduct: Boolean? = null,

	val enabled: Boolean? = null,
	val quantity: Int? = null,
	val outOfStockVisibilityBehaviour: OutOfStockVisibilityBehaviour? = null,
	val unlimited: Boolean? = null,
	val warningLimit: Int? = null,

	val categoryIds: List<Int>? = null,
	val defaultCategoryId: Int? = null,
	val showOnFrontpage: Int? = null,

	val price: Double? = null,
	val costPrice: Double? = null,
	val wholesalePrices: List<WholesalePrice>? = null,
	val compareToPrice: Double? = null,

	val weight: Double? = null,
	val dimensions: ProductDimensions? = null,
	val volume: Double? = null,

	val shippingPreparationTime: ShippingPreparationTime? = null,
	val showDeliveryTimeInStorefront: Boolean? = null,

	val shipping: ShippingSettings? = null,
	val isShippingRequired: Boolean? = null,

	val productClassId: Int? = null,
	val attributes: List<AttributeValue>? = null,

	val seoTitle: String? = null,
	val seoDescription: String? = null,

	val options: List<ProductOption>? = null,
	val tax: TaxInfo? = null,
	val relatedProducts: RelatedProducts? = null,

	val media: ProductMedia? = null,

	val subtitle: String? = null,
	val ribbon: Ribbon? = null,
	val ribbonTranslated: LocalizedValueMap? = null,
	val subtitleTranslated: LocalizedValueMap? = null,
	val nameYourPriceEnabled: Boolean? = null,
	val customPriceTiers: List<CustomPriceTier>? = null,
	val priceDefaultTier: Int? = null,
	val subscriptionSettings: SubscriptionSettings? = null,
	val googleProductCategory: NullableUpdatedValue<Int>? = null,
	val productCondition: ProductCondition? = null,
	val externalReferenceId: String? = null,
	val customsHsTariffCode: String? = null,
) : ApiUpdatedDTO {

	data class Ribbon(
		val text: String? = null,
		val color: String? = null
	)

	data class SubscriptionSettings(
		val subscriptionAllowed: Boolean? = null,
		val oneTimePurchaseAllowed: Boolean? = null,
		val recurringChargeSettings: List<RecurringChargeSettings>? = null,
		val oneTimePurchasePrice: Double? = null,
	)

	data class RecurringChargeSettings(
		val recurringInterval: RecurringSubscriptionInterval = RecurringSubscriptionInterval.MONTH,
		val recurringIntervalCount: Int = 1,
		val subscriptionPriceWithSignUpFee: Double? = null,
	)

	data class WholesalePrice(
		val quantity: Int = 0,
		val price: Double = 0.0
	)

	data class TaxInfo(
		val taxable: Boolean = true,
		val enabledManualTaxes: List<Int>? = null,
		val taxClassCode: String? = null,
	)

	sealed class ProductOption constructor(
		val type: ProductOptionType? = null,
	) {
		abstract val name: String
		abstract val nameTranslated: LocalizedValueMap?

		interface ChoiceBased {
			val choices: List<ProductOptionChoice>
			val defaultChoice: Int?
		}

		data class SelectOption(
			override val name: String = "",
			override val nameTranslated: LocalizedValueMap? = null,
			override val choices: List<ProductOptionChoice> = listOf(),
			override val defaultChoice: Int = 0,
			val required: Boolean = false
		) : ProductOption(ProductOptionType.SELECT), ChoiceBased

		data class SizeOption(
			override val name: String = "",
			override val nameTranslated: LocalizedValueMap? = null,
			override val choices: List<ProductOptionChoice> = listOf(),
			override val defaultChoice: Int = 0,
			val required: Boolean = false
		) : ProductOption(ProductOptionType.SIZE), ChoiceBased

		data class RadioOption(
			override val name: String = "",
			override val nameTranslated: LocalizedValueMap? = null,
			override val choices: List<ProductOptionChoice> = listOf(),
			override val defaultChoice: Int = 0,
			val required: Boolean = false
		) : ProductOption(ProductOptionType.RADIO), ChoiceBased

		data class CheckboxOption(
			override val name: String = "",
			override val nameTranslated: LocalizedValueMap? = null,
			override val choices: List<ProductOptionChoice> = listOf(),
			override val defaultChoice: Int? = null,
			val required: Boolean = false
		) : ProductOption(ProductOptionType.CHECKBOX), ChoiceBased

		data class TextFieldOption(
			override val name: String = "",
			override val nameTranslated: LocalizedValueMap? = null,
			val required: Boolean = false
		) : ProductOption(ProductOptionType.TEXTFIELD)

		data class TextAreaOption(
			override val name: String = "",
			override val nameTranslated: LocalizedValueMap? = null,
			val required: Boolean = false
		) : ProductOption(ProductOptionType.TEXTAREA)

		data class DateOption(
			override val name: String = "",
			override val nameTranslated: LocalizedValueMap? = null,
			val required: Boolean = false
		) : ProductOption(ProductOptionType.DATE)

		data class FilesOption(
			override val name: String = "",
			override val nameTranslated: LocalizedValueMap? = null,
			val required: Boolean = false
		) : ProductOption(ProductOptionType.FILES)

		companion object {

			fun createSelectOption(
				name: String = "",
				nameTranslated: LocalizedValueMap? = null,
				choices: List<ProductOptionChoice> = listOf(),
				defaultChoice: Int = 0,
				required: Boolean = false
			) = SelectOption(
				name = name,
				nameTranslated = nameTranslated,
				choices = choices,
				defaultChoice = defaultChoice,
				required = required
			)

			fun createSizeOption(
				name: String = "",
				nameTranslated: LocalizedValueMap? = null,
				choices: List<ProductOptionChoice> = listOf(),
				defaultChoice: Int = 0,
				required: Boolean = false
			) = SizeOption(
				name = name,
				nameTranslated = nameTranslated,
				choices = choices,
				defaultChoice = defaultChoice,
				required = required
			)

			fun createRadioOption(
				name: String = "",
				nameTranslated: LocalizedValueMap? = null,
				choices: List<ProductOptionChoice> = listOf(),
				defaultChoice: Int = 0,
				required: Boolean = false
			) = RadioOption(
				name = name,
				nameTranslated = nameTranslated,
				choices = choices,
				defaultChoice = defaultChoice,
				required = required
			)

			fun createCheckboxOption(
				name: String = "",
				nameTranslated: LocalizedValueMap? = null,
				choices: List<ProductOptionChoice> = listOf()
			) = CheckboxOption(
				name = name,
				nameTranslated = nameTranslated,
				choices = choices
			)

			fun createTextFieldOption(
				name: String = "",
				nameTranslated: LocalizedValueMap? = null,
				required: Boolean = false
			) = TextFieldOption(
				name = name,
				nameTranslated = nameTranslated,
				required = required
			)

			fun createTextAreaOption(
				name: String = "",
				nameTranslated: LocalizedValueMap? = null,
				required: Boolean = false
			) = TextAreaOption(
				name = name,
				nameTranslated = nameTranslated,
				required = required
			)

			fun createDateOption(
				name: String = "",
				nameTranslated: LocalizedValueMap? = null,
				required: Boolean = false
			) = DateOption(
				name = name,
				nameTranslated = nameTranslated,
				required = required
			)

			fun createFilesOption(
				name: String = "",
				nameTranslated: LocalizedValueMap? = null,
				required: Boolean = false
			) = FilesOption(
				name = name,
				nameTranslated = nameTranslated,
				required = required
			)
		}
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

	data class AttributeValue internal constructor(
		val id: Int? = null,
		val alias: AttributeValueAlias? = null,
		val name: String? = null,
		val value: String? = null
	) {

		companion object {

			fun createBrandAttributeValue(value: String) = AttributeValue(
				id = null,
				alias = AttributeValueAlias.BRAND,
				value = value
			)

			fun createUpcAttributeValue(value: String) = AttributeValue(
				id = null,
				alias = AttributeValueAlias.UPC,
				value = value
			)

			fun createPricePerUnitAttributeValue(value: String) = AttributeValue(
				id = null,
				alias = AttributeValueAlias.PRICE_PER_UNIT,
				value = value
			)

			fun createUnitsInProductAttributeValue(value: String) = AttributeValue(
				id = null,
				alias = AttributeValueAlias.UNITS_IN_PRODUCT,
				value = value
			)

			@Suppress("unused")
			fun createAttributeValue(productAttributeId: Int, value: String) = AttributeValue(
				id = productAttributeId,
				value = value
			)

			@Suppress("unused")
			fun createAttributeValue(name: String, value: String) = AttributeValue(
				name = name,
				value = value
			)
		}
	}

	data class RelatedProducts(
		val productIds: List<Int>? = null,
		val productSkus: List<String>? = null,
		val relatedCategory: RelatedCategory? = null
	)

	data class RelatedCategory(
		val enabled: Boolean? = null,
		val categoryId: Int? = null,
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

	data class ProductMedia(
		val images: List<ProductImage>? = null
	)

	data class ProductImage(
		val id: String = "0",
		val orderBy: Int = 0
	)

	override fun getModifyKind() = ModifyKind.ReadWrite(FetchedProduct::class)
}
