package com.ecwid.apiclient.v3.dto.product.request

import com.ecwid.apiclient.v3.dto.product.enums.*

data class UpdatedProduct(
		val name: String? = null,
		val description: String? = null,
		val sku: String? = null,

		val enabled: Boolean? = null,
		val quantity: Int? = null,
		val unlimited: Boolean? = null,
		val warningLimit: Int? = null,

		val categoryIds: List<Int>? = null,
		val defaultCategoryId: Int? = null,
		val showOnFrontpage: Int? = null,

		val price: Double? = null,
		val wholesalePrices: List<WholesalePrice>? = null,
		val compareToPrice: Double? = null,

		val weight: Double? = null,
		val dimensions: ProductDimensions? = null,
		val shipping: ShippingSettings? = null,
		val isShippingRequired: Boolean? = null,

		val productClassId: Int? = null,
		val attributes: List<AttributeValue>? = null,

		val seoTitle: String? = null,
		val seoDescription: String? = null,

		val options: List<ProductOption>? = null,
		val tax: TaxInfo? = null,
		val relatedProducts: RelatedProducts? = null,

		val media: ProductMedia? = null
) {

	data class WholesalePrice(
			val quantity: Int = 0,
			val price: Double = 0.0
	)

	data class TaxInfo(
			val defaultLocationIncludedTaxRate: Double = 0.0,
			val enabledManualTaxes: List<Int> = listOf()
	)

	sealed class ProductOption constructor(
			val type: ProductOptionType? = null
	) {
		data class SelectOption(
				val name: String = "",
				val choices: List<ProductOptionChoice> = listOf(),
				val defaultChoice: Int = 0,
				val required: Boolean = false
		) : ProductOption(ProductOptionType.SELECT)

		data class RadioOption(
				val name: String = "",
				val choices: List<ProductOptionChoice> = listOf(),
				val defaultChoice: Int = 0,
				val required: Boolean = false
		) : ProductOption(ProductOptionType.RADIO)

		data class CheckboxOption(
				val name: String = "",
				val choices: List<ProductOptionChoice> = listOf(),
				val required: Boolean = false
		) : ProductOption(ProductOptionType.CHECKBOX)

		data class TextFieldOption(
				val name: String = "",
				val required: Boolean = false
		) : ProductOption(ProductOptionType.TEXTFIELD)

		data class TextAreaOption(
				val name: String = "",
				val required: Boolean = false
		) : ProductOption(ProductOptionType.TEXTAREA)

		data class DateOption(
				val name: String = "",
				val required: Boolean = false
		) : ProductOption(ProductOptionType.DATE)

		data class FilesOption(
				val name: String = "",
				val required: Boolean = false
		) : ProductOption(ProductOptionType.FILES)

		companion object {

			fun createSelectOption(
					name: String = "",
					choices: List<ProductOptionChoice> = listOf(),
					defaultChoice: Int = 0,
					required: Boolean = false
			) = SelectOption(
					name = name,
					choices = choices,
					defaultChoice = defaultChoice,
					required = required
			)

			fun createRadioOption(
					name: String = "",
					choices: List<ProductOptionChoice> = listOf(),
					defaultChoice: Int = 0,
					required: Boolean = false
			) = RadioOption(
					name = name,
					choices = choices,
					defaultChoice = defaultChoice,
					required = required
			)

			fun createCheckboxOption(
					name: String = "",
					choices: List<ProductOptionChoice> = listOf()
			) = CheckboxOption(
					name = name,
					choices = choices
			)

			fun createTextFieldOption(
					name: String = "",
					required: Boolean = false
			) = TextFieldOption(
					name = name,
					required = required
			)

			fun createTextAreaOption(
					name: String = "",
					required: Boolean = false
			) = TextAreaOption(
					name = name,
					required = required
			)

			fun createDateOption(
					name: String = "",
					required: Boolean = false
			) = DateOption(
					name = name,
					required = required
			)

			fun createFilesOption(
					name: String = "",
					required: Boolean = false
			) = FilesOption(
					name = name,
					required = required
			)

		}

	}

	data class ProductOptionChoice(
			val text: String = "",
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
			val name: String? = null,
			val alias: AttributeValueAlias? = null,
			val value: String? = null,
			val show: AttributeValueLocation? = null
	) {

		companion object {

			fun createBrandAttributeValue(value: String) = AttributeValue(
					id = null,
					alias = AttributeValueAlias.BRAND,
					value = value,
					show = AttributeValueLocation.DESCR
			)

			fun createUpcAttributeValue(value: String) = AttributeValue(
					id = null,
					alias = AttributeValueAlias.UPC,
					value = value,
					show = AttributeValueLocation.DESCR
			)

			fun createAttributeValue(productAttributeId: Int, value: String, show: AttributeValueLocation) = AttributeValue(
					id = productAttributeId,
					value = value,
					show = show
			)

		}

	}

	data class RelatedProducts(
			val productIds: List<Int>? = null,
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

	data class ProductMedia(
			val images: List<ProductImage>? = null
	)

	data class ProductImage(
			val id: String = "0",
			val orderBy: Int = 0
	)

}