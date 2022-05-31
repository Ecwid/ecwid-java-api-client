package com.ecwid.apiclient.v3.impl

import com.ecwid.apiclient.v3.ApiClientHelper
import com.ecwid.apiclient.v3.ApplicationStorageApiClient
import com.ecwid.apiclient.v3.dto.storage.request.*
import com.ecwid.apiclient.v3.dto.storage.result.*

internal class ApplicationStorageApiClientImpl(
	private val apiClientHelper: ApiClientHelper,
) : ApplicationStorageApiClient {

	override fun getStorageEntity(request: StorageEntityRequest) =
		apiClientHelper.makeObjectResultRequest<FetchedStorageData>(request)

	override fun getAllStorageEntities(request: StorageEntitiesRequest) =
		apiClientHelper.makeObjectResultRequest<StorageEntitiesResult>(request)

	override fun createOrUpdateStorageEntity(request: StorageEntityUpdateRequest) =
		apiClientHelper.makeObjectResultRequest<StorageEntityUpdateResult>(request)

	override fun deleteStorageEntity(request: StorageEntityDeleteRequest) =
		apiClientHelper.makeObjectResultRequest<StorageEntityDeleteResult>(request)

}
