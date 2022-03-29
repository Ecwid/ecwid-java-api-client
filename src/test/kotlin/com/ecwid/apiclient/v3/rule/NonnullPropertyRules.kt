package com.ecwid.apiclient.v3.rule

import com.ecwid.apiclient.v3.dto.common.ApiUpdatedDTO
import com.ecwid.apiclient.v3.dto.coupon.request.UpdatedCoupon
import com.ecwid.apiclient.v3.dto.customer.request.UpdatedCustomer
import com.ecwid.apiclient.v3.dto.customergroup.request.UpdatedCustomerGroup
import com.ecwid.apiclient.v3.dto.product.request.UpdatedProduct
import com.ecwid.apiclient.v3.dto.variation.request.UpdatedVariation
import com.ecwid.apiclient.v3.rule.NonnullPropertyRule.AllowNonnull
import com.ecwid.apiclient.v3.rule.NonnullPropertyRule.IgnoreNonnull
import kotlin.reflect.KProperty1

val nonnullPropertyRules: List<NonnullPropertyRule<*, *>> = listOf(
	AllowNonnull(ApiUpdatedDTO.ModifyKind.ReadWrite::fetchedDTOClass),

	AllowNonnull(UpdatedProduct.ProductOption.CheckboxOption::required),
	AllowNonnull(UpdatedProduct.ProductOption.DateOption::required),
	AllowNonnull(UpdatedProduct.ProductOption.FilesOption::required),
	AllowNonnull(UpdatedProduct.ProductOption.RadioOption::required),
	AllowNonnull(UpdatedProduct.ProductOption.SelectOption::required),
	AllowNonnull(UpdatedProduct.ProductOption.SizeOption::required),
	AllowNonnull(UpdatedProduct.ProductOption.TextAreaOption::required),
	AllowNonnull(UpdatedProduct.ProductOption.TextFieldOption::required),

	IgnoreNonnull(UpdatedCoupon::code),
	IgnoreNonnull(UpdatedCoupon::name),

	IgnoreNonnull(UpdatedCustomer::email),

	IgnoreNonnull(UpdatedCustomerGroup::name),

	IgnoreNonnull(UpdatedProduct.ProductImage::id),
	IgnoreNonnull(UpdatedProduct.ProductImage::orderBy),
	IgnoreNonnull(UpdatedProduct.ProductOption.CheckboxOption::choices),
	IgnoreNonnull(UpdatedProduct.ProductOption.CheckboxOption::name),
	IgnoreNonnull(UpdatedProduct.ProductOption.DateOption::name),
	IgnoreNonnull(UpdatedProduct.ProductOption.FilesOption::name),
	IgnoreNonnull(UpdatedProduct.ProductOption.RadioOption::choices),
	IgnoreNonnull(UpdatedProduct.ProductOption.RadioOption::defaultChoice),
	IgnoreNonnull(UpdatedProduct.ProductOption.RadioOption::name),
	IgnoreNonnull(UpdatedProduct.ProductOption.SelectOption::choices),
	IgnoreNonnull(UpdatedProduct.ProductOption.SelectOption::defaultChoice),
	IgnoreNonnull(UpdatedProduct.ProductOption.SelectOption::name),
	IgnoreNonnull(UpdatedProduct.ProductOption.SizeOption::choices),
	IgnoreNonnull(UpdatedProduct.ProductOption.SizeOption::defaultChoice),
	IgnoreNonnull(UpdatedProduct.ProductOption.SizeOption::name),
	IgnoreNonnull(UpdatedProduct.ProductOption.TextAreaOption::name),
	IgnoreNonnull(UpdatedProduct.ProductOption.TextFieldOption::name),
	IgnoreNonnull(UpdatedProduct.ProductOptionChoice::priceModifier),
	IgnoreNonnull(UpdatedProduct.ProductOptionChoice::priceModifierType),
	IgnoreNonnull(UpdatedProduct.ProductOptionChoice::text),
	IgnoreNonnull(UpdatedProduct.RecurringChargeSettings::recurringInterval),
	IgnoreNonnull(UpdatedProduct.RecurringChargeSettings::recurringIntervalCount),
	IgnoreNonnull(UpdatedProduct.TaxInfo::enabledManualTaxes),
	IgnoreNonnull(UpdatedProduct.TaxInfo::taxable),
	IgnoreNonnull(UpdatedProduct.WholesalePrice::price),
	IgnoreNonnull(UpdatedProduct.WholesalePrice::quantity),

	IgnoreNonnull(UpdatedVariation.WholesalePrice::price),
	IgnoreNonnull(UpdatedVariation.WholesalePrice::quantity)
)

sealed class NonnullPropertyRule<T, R>(
	val property: KProperty1<T, R>
) {

	class AllowNonnull<T, R> internal constructor(
		property: KProperty1<T, R>
	) : NonnullPropertyRule<T, R>(property)

	class IgnoreNonnull<T, R> internal constructor(
		property: KProperty1<T, R>
	) : NonnullPropertyRule<T, R>(property)
}
