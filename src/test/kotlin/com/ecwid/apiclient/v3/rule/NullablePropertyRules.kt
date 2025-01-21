package com.ecwid.apiclient.v3.rule

import com.ecwid.apiclient.v3.dto.batch.request.CreateBatchRequest
import com.ecwid.apiclient.v3.dto.batch.request.CreateBatchRequestWithIds
import com.ecwid.apiclient.v3.dto.batch.result.GetEscapedBatchResult
import com.ecwid.apiclient.v3.dto.batch.result.GetTypedBatchResult
import com.ecwid.apiclient.v3.dto.cart.result.CartUpdateResult
import com.ecwid.apiclient.v3.dto.cart.result.ConvertCartToOrderResult
import com.ecwid.apiclient.v3.dto.customergroup.request.CustomerGroupsSearchRequest
import com.ecwid.apiclient.v3.dto.instantsite.redirects.request.InstantSiteRedirectsGetForExactPathRequest
import com.ecwid.apiclient.v3.dto.instantsite.redirects.request.InstantSiteRedirectsSearchRequest
import com.ecwid.apiclient.v3.dto.order.result.DeletedOrder
import com.ecwid.apiclient.v3.dto.payment.PaymentAppRequest
import com.ecwid.apiclient.v3.dto.product.request.ProductInventoryUpdateRequest
import com.ecwid.apiclient.v3.dto.product.request.ProductUpdateRequest
import com.ecwid.apiclient.v3.dto.product.result.GetProductFiltersResult
import com.ecwid.apiclient.v3.dto.product.result.ProductInventoryUpdateResult
import com.ecwid.apiclient.v3.dto.productreview.request.UpdatedProductReviewStatus
import com.ecwid.apiclient.v3.dto.profile.request.StoreProfileRequest
import com.ecwid.apiclient.v3.dto.profile.result.FetchedLatestStats
import com.ecwid.apiclient.v3.dto.report.request.ReportAdviceRequest
import com.ecwid.apiclient.v3.dto.report.request.ReportRequest
import com.ecwid.apiclient.v3.dto.report.result.FetchedReportAdviceResponse
import com.ecwid.apiclient.v3.dto.report.result.FetchedReportResponse
import com.ecwid.apiclient.v3.dto.storage.result.FetchedStorageData
import com.ecwid.apiclient.v3.dto.variation.request.ProductVariationsRequest
import com.ecwid.apiclient.v3.rule.NullablePropertyRule.AllowNullable
import com.ecwid.apiclient.v3.rule.NullablePropertyRule.IgnoreNullable
import com.ecwid.apiclient.v3.rule.nullablepropertyrules.*
import kotlin.reflect.KProperty1

val otherNullablePropertyRules: List<NullablePropertyRule<*, *>> = listOf(

	AllowNullable(ProductInventoryUpdateRequest::checkLowStockNotification),
	AllowNullable(ProductUpdateRequest::checkLowStockNotification),
	AllowNullable(ProductUpdateRequest::rebuildVariationsOnOptionsUpdate),
	AllowNullable(ProductUpdateRequest::keepOptionDisplaySettings),

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
	AllowNullable(ReportRequest::orderByMetric),
	AllowNullable(ReportRequest::orderDirection),
	AllowNullable(ReportRequest::limit),
	AllowNullable(ReportRequest::offset),
	AllowNullable(ReportRequest::storefrontPlatform),

	AllowNullable(ReportAdviceRequest::startedFrom),
	AllowNullable(ReportAdviceRequest::endedAt),
	AllowNullable(ReportAdviceRequest::timeScaleValue),
	AllowNullable(ReportAdviceRequest::comparePeriod),
	AllowNullable(ReportAdviceRequest::firstDayOfWeek),
	AllowNullable(ReportAdviceRequest::orderByMetric),
	AllowNullable(ReportAdviceRequest::orderDirection),
	AllowNullable(ReportAdviceRequest::limit),
	AllowNullable(ReportAdviceRequest::offset),
	AllowNullable(ReportAdviceRequest::storefrontPlatform),

	AllowNullable(FetchedReportResponse::timeScaleValue),
	AllowNullable(FetchedReportResponse::comparePeriod),
	AllowNullable(FetchedReportResponse::firstDayOfWeek),
	AllowNullable(FetchedReportResponse::orderByMetric),
	AllowNullable(FetchedReportResponse::orderDirection),
	AllowNullable(FetchedReportResponse::limit),
	AllowNullable(FetchedReportResponse::offset),
	AllowNullable(FetchedReportResponse::itemCount),
	AllowNullable(FetchedReportResponse::dataset),
	AllowNullable(FetchedReportResponse::comparePeriodAggregatedData),
	AllowNullable(FetchedReportResponse::comparePeriodDataset),
	AllowNullable(FetchedReportResponse::additionalData),

	AllowNullable(FetchedReportAdviceResponse::tip),

	AllowNullable(FetchedReportResponse.FetchedDataset::startTimeStamp),
	AllowNullable(FetchedReportResponse.FetchedDataset::endTimeStamp),
	AllowNullable(FetchedReportResponse.FetchedDataset::percentage),
	AllowNullable(FetchedReportResponse.FetchedDataset::comparePeriodStartTimeStamp),
	AllowNullable(FetchedReportResponse.FetchedDataset::comparePeriodEndTimeStamp),
	AllowNullable(FetchedReportResponse.FetchedDataset::additionalData),

	AllowNullable(FetchedReportResponse.FetchedCustomerData::customerId),
	AllowNullable(FetchedReportResponse.FetchedCustomerData::customerEmail),
	AllowNullable(FetchedReportResponse.FetchedCustomerData::customerPhone),

	AllowNullable(FetchedReportResponse.FetchedAdditionalData.AdditionalInventoryData::sku),
	AllowNullable(FetchedReportResponse.FetchedAdditionalData.AdditionalInventoryData::imageUrl),
	AllowNullable(FetchedReportResponse.FetchedAdditionalData.AdditionalInventoryData::thumbnailUrl),
	AllowNullable(FetchedReportResponse.FetchedAdditionalData.AdditionalInventoryData::exampleOrder),

	AllowNullable(FetchedReportResponse.FetchedAdditionalData.AdditionalProductData::productName),
	AllowNullable(FetchedReportResponse.FetchedAdditionalData.AdditionalProductData::productUrl),
	AllowNullable(FetchedReportResponse.FetchedAdditionalData.AdditionalProductData::productEditUrl),
	AllowNullable(FetchedReportResponse.FetchedAdditionalData.AdditionalProductData::productSku),
	AllowNullable(FetchedReportResponse.FetchedAdditionalData.AdditionalProductData::productImageUrl),
	AllowNullable(FetchedReportResponse.FetchedAdditionalData.AdditionalProductData::productThumbnailUrl),

	AllowNullable(FetchedReportResponse.FetchedAdditionalData.AdditionalCouponData::couponName),

	AllowNullable(FetchedReportResponse.FetchedAdditionalData.AdditionalAbandonedCartData::autoAbandonedSalesRecovery),

	AllowNullable(FetchedReportResponse.FetchedAdditionalData.AdditionalShippingData::shippingMethodName),
	AllowNullable(FetchedReportResponse.FetchedAdditionalData.AdditionalShippingData::fulfilmentType),

	AllowNullable(FetchedReportResponse.FetchedAdditionalData.AdditionalLandingData::landingUrl),

	AllowNullable(FetchedReportResponse.FetchedAdditionalData.AdditionalCategoryData::categoryName),

	AllowNullable(CreateBatchRequest::groupId),
	AllowNullable(CreateBatchRequestWithIds::groupId),

	AllowNullable(InstantSiteRedirectsSearchRequest::keyword),
	AllowNullable(InstantSiteRedirectsSearchRequest::limit),
	AllowNullable(InstantSiteRedirectsSearchRequest::offset),

	AllowNullable(InstantSiteRedirectsGetForExactPathRequest::exactPath),

	AllowNullable(UpdatedProductReviewStatus::status),

	AllowNullable(CustomerGroupsSearchRequest::keyword),
	AllowNullable(CustomerGroupsSearchRequest::customerGroupIds),
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
	slugInfoRequestNullablePropertyRules,
	fetchedProductReviewNullablePropertyRules,
	productReviewMassUpdateRequestNullablePropertyRules,
	productReviewSearchRequestNullablePropertyRules,
	productReviewCountersUpdateRequestNullablePropertyRules,
	fetchedCustomersConfigNullablePropertyRules,
	brandsSearchRequestNullablePropertyRules,
	fetchedBrandNullablePropertyRules,
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
