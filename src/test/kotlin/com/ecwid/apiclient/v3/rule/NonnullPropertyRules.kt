package com.ecwid.apiclient.v3.rule

import com.ecwid.apiclient.v3.dto.common.ApiUpdatedDTO
import com.ecwid.apiclient.v3.dto.coupon.request.UpdatedCoupon
import com.ecwid.apiclient.v3.dto.customergroup.request.UpdatedCustomerGroup
import com.ecwid.apiclient.v3.dto.product.request.UpdatedProduct
import com.ecwid.apiclient.v3.dto.profile.request.UpdatedStoreProfile
import com.ecwid.apiclient.v3.dto.storage.request.UpdatedStorageData
import com.ecwid.apiclient.v3.dto.variation.request.UpdatedVariation
import com.ecwid.apiclient.v3.rule.NonnullPropertyRule.AllowNonnull
import com.ecwid.apiclient.v3.rule.NonnullPropertyRule.IgnoreNonnull
import kotlin.reflect.KProperty1

val nonnullPropertyRules: List<NonnullPropertyRule<*, *>> = listOf(
	AllowNonnull(ApiUpdatedDTO.ModifyKind.ReadWrite::fetchedDTOClass),

	AllowNonnull(UpdatedProduct.CustomPriceTier::value),

	AllowNonnull(UpdatedStorageData::key),
	AllowNonnull(UpdatedStorageData::value),

	IgnoreNonnull(UpdatedCoupon::code),
	IgnoreNonnull(UpdatedCoupon::name),

	IgnoreNonnull(UpdatedCustomerGroup::name),

	IgnoreNonnull(UpdatedProduct.ProductImage::id),
	IgnoreNonnull(UpdatedProduct.ProductImage::orderBy),
	IgnoreNonnull(UpdatedProduct.ProductOption.CheckboxOption::choices),
	IgnoreNonnull(UpdatedProduct.ProductOption.CheckboxOption::name),
	IgnoreNonnull(UpdatedProduct.ProductOption.DateOption::name),
	IgnoreNonnull(UpdatedProduct.ProductOption.FilesOption::name),
	IgnoreNonnull(UpdatedProduct.ProductOption.RadioOption::choices),
	IgnoreNonnull(UpdatedProduct.ProductOption.RadioOption::name),
	IgnoreNonnull(UpdatedProduct.ProductOption.SelectOption::choices),
	IgnoreNonnull(UpdatedProduct.ProductOption.SelectOption::name),
	IgnoreNonnull(UpdatedProduct.ProductOption.SizeOption::choices),
	IgnoreNonnull(UpdatedProduct.ProductOption.SizeOption::name),
	IgnoreNonnull(UpdatedProduct.ProductOption.TextAreaOption::name),
	IgnoreNonnull(UpdatedProduct.ProductOption.TextFieldOption::name),
	IgnoreNonnull(UpdatedProduct.ProductOptionChoice::text),
	IgnoreNonnull(UpdatedProduct.RecurringChargeSettings::recurringInterval),
	IgnoreNonnull(UpdatedProduct.RecurringChargeSettings::recurringIntervalCount),
	IgnoreNonnull(UpdatedProduct.WholesalePrice::price),
	IgnoreNonnull(UpdatedProduct.WholesalePrice::quantity),

	IgnoreNonnull(UpdatedVariation.WholesalePrice::price),
	IgnoreNonnull(UpdatedVariation.WholesalePrice::quantity),

	AllowNonnull(UpdatedStoreProfile.ProductFilterItem::enabled),
	AllowNonnull(UpdatedStoreProfile.ProductFilterItem::type),
	AllowNonnull(UpdatedStoreProfile.ProductFiltersSettings::enabledInStorefront),
	AllowNonnull(UpdatedStoreProfile.ProductFiltersSettings::filterSections),
	AllowNonnull(UpdatedVariation.RecurringChargeSettings::recurringInterval),
	AllowNonnull(UpdatedVariation.RecurringChargeSettings::recurringIntervalCount),
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
