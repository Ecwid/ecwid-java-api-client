package com.ecwid.apiclient.v3

import com.ecwid.apiclient.v3.dto.common.PartialResult
import com.ecwid.apiclient.v3.dto.profile.request.*
import com.ecwid.apiclient.v3.dto.profile.result.*
import kotlin.reflect.KClass

// Store-Profile
// https://api-docs.ecwid.com/reference/store-profile
interface StoreProfileApiClient {
	fun getStoreProfile(request: StoreProfileRequest): FetchedStoreProfile
	fun <Result> getStoreProfile(request: StoreProfileRequest, resultClass: KClass<Result>): Result
		where Result : PartialResult<FetchedStoreProfile>

	fun updateStoreProfile(request: StoreProfileUpdateRequest): StoreProfileUpdateResult
	fun getLatestStats(request: LatestStatsRequest): FetchedLatestStats
	fun getShippingOptions(request: ShippingOptionsRequest): ShippingOptionsResult

	// 	fun addShippingOption()
	fun updateShippingOption(request: ShippingOptionUpdateRequest): ShippingOptionUpdateResult
	fun getPaymentOptions(request: PaymentOptionsRequest): PaymentOptionsResult
	fun createPaymentOption(request: PaymentOptionCreateRequest): PaymentOptionCreateResult
	fun deletePaymentOption(request: PaymentOptionDeleteRequest): PaymentOptionDeleteResult
	fun updatePaymentOption(request: PaymentOptionUpdateRequest): PaymentOptionUpdateResult

	fun uploadStoreLogo(request: StoreLogoUploadRequest): StoreLogoUploadResult
	fun removeStoreLogo(request: StoreLogoRemoveRequest): StoreLogoRemoveResult
	fun uploadInvoiceLogo(request: InvoiceLogoUploadRequest): InvoiceLogoUploadResult
	fun removeInvoiceLogo(request: InvoiceLogoRemoveRequest): InvoiceLogoRemoveResult
	fun uploadEmailLogo(request: EmailLogoUploadRequest): EmailLogoUploadResult
	fun removeEmailLogo(request: EmailLogoRemoveRequest): EmailLogoRemoveResult
	fun searchExtrafieldConfigs(request: ExtrafieldConfigSearchRequest): ExtrafieldConfigSearchResult
	fun getExtrafieldConfigDetails(request: ExtrafieldConfigDetailsRequest): FetchedExtrafieldConfig
	fun createExtrafieldConfig(request: ExtrafieldConfigCreateRequest): ExtrafieldConfigCreateResult
	fun updateExtrafieldConfig(request: ExtrafieldConfigUpdateRequest): ExtrafieldConfigUpdateResult
	fun deleteExtrafieldConfig(request: ExtrafieldConfigDeleteRequest): ExtrafieldConfigDeleteResult
	fun searchOrderStatusesSettings(request: OrderStatusSettingsSearchRequest): OrderStatusSettingsSearchResult
	fun <Result> searchOrderStatusesSettings(request: OrderStatusSettingsSearchRequest, resultClass: KClass<Result>): Result
		where Result : PartialResult<OrderStatusSettingsSearchResult>
	fun getOrderStatusSettingsDetails(request: OrderStatusSettingsDetailsRequest): FetchedOrderStatusSettings
	fun updateOrderStatusSettings(request: OrderStatusSettingsUpdateRequest): OrderStatusSettingsUpdateResult
}

@Suppress("EXTENSION_SHADOWED_BY_MEMBER")
inline fun <reified Result : PartialResult<FetchedStoreProfile>> StoreProfileApiClient.getStoreProfile(request: StoreProfileRequest): Result {
	return getStoreProfile(request, Result::class)
}

@Suppress("EXTENSION_SHADOWED_BY_MEMBER")
inline fun <reified Result : PartialResult<OrderStatusSettingsSearchResult>> StoreProfileApiClient.searchOrderStatusesSettings(request: OrderStatusSettingsSearchRequest): Result {
	return searchOrderStatusesSettings(request, Result::class)
}
