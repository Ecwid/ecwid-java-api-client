package com.ecwid.apiclient.v3.impl

import com.ecwid.apiclient.v3.ApiClientHelper
import com.ecwid.apiclient.v3.ApplicationStorageApiClient
import com.ecwid.apiclient.v3.dto.storage.request.*
import com.ecwid.apiclient.v3.dto.storage.result.*

internal class ApplicationStorageApiClientImpl(
	private val apiClientHelper: ApiClientHelper,
) : ApplicationStorageApiClient {

	override fun getStorageData(request: StorageDataRequest) =
		apiClientHelper.makeObjectResultRequest<FetchedStorageData>(request)

	override fun getAllStorageData(request: AllStorageDataRequest) =
		apiClientHelper.makeObjectResultRequest<AllStorageDataResult>(request)

	override fun createOrUpdateStorageData(request: StorageDataUpdateRequest) =
		apiClientHelper.makeObjectResultRequest<StorageDataUpdateResult>(request)

	override fun deleteStorageData(request: StorageDataDeleteRequest) =
		apiClientHelper.makeObjectResultRequest<StorageDataDeleteResult>(request)

}
