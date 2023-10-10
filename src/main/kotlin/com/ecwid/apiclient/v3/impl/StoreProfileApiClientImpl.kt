package com.ecwid.apiclient.v3.impl

import com.ecwid.apiclient.v3.ApiClientHelper
import com.ecwid.apiclient.v3.StoreProfileApiClient
import com.ecwid.apiclient.v3.dto.common.PartialResult
import com.ecwid.apiclient.v3.dto.profile.request.*
import com.ecwid.apiclient.v3.dto.profile.result.*
import kotlin.reflect.KClass

internal class StoreProfileApiClientImpl(
	private val apiClientHelper: ApiClientHelper
) : StoreProfileApiClient {
	override fun getStoreProfile(request: StoreProfileRequest) =
		apiClientHelper.makeObjectResultRequest<FetchedStoreProfile>(request)

	override fun updateStoreProfile(request: StoreProfileUpdateRequest) =
		apiClientHelper.makeObjectResultRequest<StoreProfileUpdateResult>(request)

	override fun getLatestStats(request: LatestStatsRequest) =
		apiClientHelper.makeObjectResultRequest<FetchedLatestStats>(request)

	override fun uploadStoreLogo(request: StoreLogoUploadRequest): StoreLogoUploadResult =
		apiClientHelper.makeObjectResultRequest(request)

	override fun removeStoreLogo(request: StoreLogoRemoveRequest): StoreLogoRemoveResult =
		apiClientHelper.makeObjectResultRequest(request)

	override fun uploadInvoiceLogo(request: InvoiceLogoUploadRequest): InvoiceLogoUploadResult =
		apiClientHelper.makeObjectResultRequest(request)

	override fun removeInvoiceLogo(request: InvoiceLogoRemoveRequest): InvoiceLogoRemoveResult =
		apiClientHelper.makeObjectResultRequest(request)

	override fun uploadEmailLogo(request: EmailLogoUploadRequest): EmailLogoUploadResult =
		apiClientHelper.makeObjectResultRequest(request)

	override fun removeEmailLogo(request: EmailLogoRemoveRequest): EmailLogoRemoveResult =
		apiClientHelper.makeObjectResultRequest(request)

	override fun searchExtrafieldConfigs(request: ExtrafieldConfigSearchRequest): ExtrafieldConfigSearchResult =
		apiClientHelper.makeObjectResultRequest(request)

	override fun getExtrafieldConfigDetails(request: ExtrafieldConfigDetailsRequest): FetchedExtrafieldConfig =
		apiClientHelper.makeObjectResultRequest(request)

	override fun createExtrafieldConfig(request: ExtrafieldConfigCreateRequest): ExtrafieldConfigCreateResult =
		apiClientHelper.makeObjectResultRequest(request)

	override fun updateExtrafieldConfig(request: ExtrafieldConfigUpdateRequest): ExtrafieldConfigUpdateResult =
		apiClientHelper.makeObjectResultRequest(request)

	override fun deleteExtrafieldConfig(request: ExtrafieldConfigDeleteRequest): ExtrafieldConfigDeleteResult =
		apiClientHelper.makeObjectResultRequest(request)

	override fun getShippingOptions(request: ShippingOptionsRequest): ShippingOptionsResult =
		apiClientHelper.makeObjectResultRequest(request)

	override fun getPaymentOptions(request: PaymentOptionsRequest): PaymentOptionsResult =
		apiClientHelper.makeObjectResultRequest(request)

	override fun createPaymentOption(request: PaymentOptionCreateRequest): PaymentOptionCreateResult =
		apiClientHelper.makeObjectResultRequest(request)

	override fun deletePaymentOption(request: PaymentOptionDeleteRequest): PaymentOptionDeleteResult =
		apiClientHelper.makeObjectResultRequest(request)

	override fun updatePaymentOption(request: PaymentOptionUpdateRequest): PaymentOptionUpdateResult =
		apiClientHelper.makeObjectResultRequest(request)

	override fun searchOrderStatusesSettings(request: OrderStatusSettingsSearchRequest): OrderStatusSettingsSearchResult =
		apiClientHelper.makeObjectResultRequest(request)

	override fun getOrderStatusSettingsDetails(request: OrderStatusSettingsDetailsRequest): FetchedOrderStatusSettings =
		apiClientHelper.makeObjectResultRequest(request)

	override fun updateOrderStatusSettings(request: OrderStatusSettingsUpdateRequest): OrderStatusSettingsUpdateResult =
		apiClientHelper.makeObjectResultRequest(request)

	override fun <Result : PartialResult<FetchedStoreProfile>> getStoreProfile(request: StoreProfileRequest, resultClass: KClass<Result>): Result {
		return apiClientHelper.makeObjectPartialResultRequest(request, resultClass)
	}
}
