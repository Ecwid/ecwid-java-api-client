package com.ecwid.apiclient.v3

import com.ecwid.apiclient.v3.dto.profile.request.*
import com.ecwid.apiclient.v3.dto.profile.result.*

// Store-Profile
// https://api-docs.ecwid.com/reference/store-profile
interface StoreProfileApiClient {
	fun getStoreProfile(request: StoreProfileRequest): FetchedStoreProfile
	fun updateStoreProfile(request: StoreProfileUpdateRequest): StoreProfileUpdateResult
	fun getLatestStats(request: LatestStatsRequest): FetchedLatestStats
	fun getShippingOptions(request: ShippingOptionsRequest): ShippingOptionsResult

	// 	fun addShippingOption()
	// 	fun updateShippingOption()
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
	fun getOrderStatusSettingsDetails(request: OrderStatusSettingsDetailsRequest): FetchedOrderStatusSettings
	fun updateOrderStatusSettings(request: OrderStatusSettingsUpdateRequest): OrderStatusSettingsUpdateResult
}
