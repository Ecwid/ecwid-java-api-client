package com.ecwid.apiclient.v3.rule

import com.ecwid.apiclient.v3.dto.batch.request.CreateBatchRequest
import com.ecwid.apiclient.v3.dto.batch.request.CreateBatchRequestWithIds
import com.ecwid.apiclient.v3.dto.batch.result.GetEscapedBatchResult
import com.ecwid.apiclient.v3.dto.batch.result.GetTypedBatchResult
import com.ecwid.apiclient.v3.dto.cart.result.CartUpdateResult
import com.ecwid.apiclient.v3.dto.cart.result.ConvertCartToOrderResult
import com.ecwid.apiclient.v3.dto.instantsite.redirects.request.InstantSiteRedirectsGetForExactPathRequest
import com.ecwid.apiclient.v3.dto.instantsite.redirects.request.InstantSiteRedirectsSearchRequest
import com.ecwid.apiclient.v3.dto.order.result.DeletedOrder
import com.ecwid.apiclient.v3.dto.payment.PaymentAppRequest
import com.ecwid.apiclient.v3.dto.product.request.ProductInventoryUpdateRequest
import com.ecwid.apiclient.v3.dto.product.request.ProductUpdateRequest
import com.ecwid.apiclient.v3.dto.product.result.GetProductFiltersResult
import com.ecwid.apiclient.v3.dto.product.result.ProductInventoryUpdateResult
import com.ecwid.apiclient.v3.dto.profile.request.StoreProfileRequest
import com.ecwid.apiclient.v3.dto.profile.result.FetchedLatestStats
import com.ecwid.apiclient.v3.dto.report.request.ReportRequest
import com.ecwid.apiclient.v3.dto.report.result.FetchedReportResponse
import com.ecwid.apiclient.v3.dto.storage.result.FetchedStorageData
import com.ecwid.apiclient.v3.dto.variation.request.ProductVariationsRequest
import com.ecwid.apiclient.v3.dto.variation.request.UpdateProductVariationRequest
import com.ecwid.apiclient.v3.rule.NullablePropertyRule.AllowNullable
import com.ecwid.apiclient.v3.rule.NullablePropertyRule.IgnoreNullable
import com.ecwid.apiclient.v3.rule.nullablepropertyrules.*
import kotlin.reflect.KProperty1

val otherNullablePropertyRules: List<NullablePropertyRule<*, *>> = listOf(

	AllowNullable(ProductInventoryUpdateRequest::checkLowStockNotification),
	AllowNullable(ProductUpdateRequest::checkLowStockNotification),
	AllowNullable(ProductUpdateRequest::rebuildVariationsOnOptionsUpdate),
	AllowNullable(ProductUpdateRequest::keepOptionDisplaySettings),
	AllowNullable(ProductUpdateRequest::resetLocationInventory),
	AllowNullable(UpdateProductVariationRequest::resetLocationInventory),

	AllowNullable(StoreProfileRequest::lang),

	AllowNullable(ProductVariationsRequest::lang),

	IgnoreNullable(GetEscapedBatchResult::responses),
	IgnoreNullable(GetTypedBatchResult::responses),

	AllowNullable(GetProductFiltersResult.ProductFilters::attributes),
	AllowNullable(GetProductFiltersResult.ProductFilters::categories),
	AllowNullable(GetProductFiltersResult.ProductFilters::inventory),
	AllowNullable(GetProductFiltersResult.ProductFilters::onsale),
	AllowNullable(GetProductFiltersResult.ProductFilters::options),
	AllowNullable(GetProductFiltersResult.ProductFilters::price),

	AllowNullable(FetchedLatestStats::productCount),
	AllowNullable(FetchedLatestStats::categoryCount),

	IgnoreNullable(ConvertCartToOrderResult::id),
	IgnoreNullable(ConvertCartToOrderResult::orderNumber),
	IgnoreNullable(ConvertCartToOrderResult::vendorOrderNumber),

	IgnoreNullable(CartUpdateResult::updateCount),

	IgnoreNullable(DeletedOrder::orderId),

	AllowNullable(PaymentAppRequest.Cart::currency),
	AllowNullable(PaymentAppRequest.Cart::order),
	AllowNullable(PaymentAppRequest::cart),
	AllowNullable(PaymentAppRequest::lang),
	AllowNullable(PaymentAppRequest::merchantAppSettings),
	AllowNullable(PaymentAppRequest::returnUrl),
	AllowNullable(PaymentAppRequest::storeId),
	AllowNullable(PaymentAppRequest::token),

	IgnoreNullable(ProductInventoryUpdateResult::warning),

	AllowNullable(FetchedStorageData::value),

	AllowNullable(ReportRequest::startedFrom),
	AllowNullable(ReportRequest::endedAt),
	AllowNullable(ReportRequest::timeScaleValue),
	AllowNullable(ReportRequest::comparePeriod),
	AllowNullable(ReportRequest::firstDayOfWeek),

	AllowNullable(FetchedReportResponse::timeScaleValue),
	AllowNullable(FetchedReportResponse::comparePeriod),
	AllowNullable(FetchedReportResponse::dataset),
	AllowNullable(FetchedReportResponse::comparePeriodAggregatedData),
	AllowNullable(FetchedReportResponse::comparePeriodDataset),
	AllowNullable(FetchedReportResponse::additionalData),

	AllowNullable(FetchedReportResponse.FetchedDataset::startTimeStamp),
	AllowNullable(FetchedReportResponse.FetchedDataset::endTimeStamp),
	AllowNullable(FetchedReportResponse.FetchedDataset::percentage),
	AllowNullable(FetchedReportResponse.FetchedDataset::comparePeriodStartTimeStamp),
	AllowNullable(FetchedReportResponse.FetchedDataset::comparePeriodEndTimeStamp),
	AllowNullable(FetchedReportResponse.FetchedDataset::additionalData),

	AllowNullable(CreateBatchRequest::groupId),
	AllowNullable(CreateBatchRequestWithIds::groupId),

	AllowNullable(InstantSiteRedirectsSearchRequest::keyword),
	AllowNullable(InstantSiteRedirectsSearchRequest::limit),
	AllowNullable(InstantSiteRedirectsSearchRequest::offset),

	AllowNullable(InstantSiteRedirectsGetForExactPathRequest::exactPath),
)

val nullablePropertyRules: List<NullablePropertyRule<*, *>> = listOf(
	calculateOrderDetailsResultNullablePropertyRules,
	cartsSearchRequestNullablePropertyRules,
	categoriesByPathRequestNullablePropertyRules,
	categoriesSearchRequestNullablePropertyRules,
	categoryDetailsRequestNullablePropertyRules,
	couponSearchRequestNullablePropertyRules,
	customAppRequestNullablePropertyRules,
	customersSearchRequestNullablePropertyRules,
	customersMassUpdatedRequestNullablePropertyRules,
	customersIdsRequestNullablePropertyRules,
	deletedCustomersSearchRequestNullablePropertyRules,
	deletedOrdersSearchRequestNullablePropertyRules,
	deletedProductsSearchRequestNullablePropertyRules,
	fetchedCartNullablePropertyRules,
	fetchedCategoryNullablePropertyRules,
	fetchedCouponNullablePropertyRules,
	fetchedCustomerNullablePropertyRules,
	fetchedExtrafieldConfigNullablePropertyRules,
	fetchedOrderStatusSettingsNullablePropertyRules,
	fetchedOrderNullablePropertyRules,
	fetchedProductNullablePropertyRules,
	fetchedProductTypeNullablePropertyRules,
	fetchedStoreProfileNullablePropertyRules,
	fetchedVariationTypeNullablePropertyRules,
	getProductFiltersRequestNullablePropertyRules,
	orderForCalculateNullablePropertyRules,
	ordersSearchRequestRequestNullablePropertyRules,
	productDetailsRequestNullablePropertyRules,
	productsSearchRequestNullablePropertyRules,
	subscriptionsSearchRequestNullablePropertyRules,
	fetchedSubscriptionsNullablePropertyRules,
	otherNullablePropertyRules,
	fetchedSlugInfoNullablePropertyRules,
	fetchedSlugInfoClassesNullablePropertyRules,
	slugInfoRequestNullablePropertyRules
).flatten()

sealed class NullablePropertyRule<T, R>(
	val property: KProperty1<T, R>
) {

	class AllowNullable<T, R> internal constructor(
		property: KProperty1<T, R>
	) : NullablePropertyRule<T, R>(property)

	class IgnoreNullable<T, R> internal constructor(
		property: KProperty1<T, R>
	) : NullablePropertyRule<T, R>(property)
}
