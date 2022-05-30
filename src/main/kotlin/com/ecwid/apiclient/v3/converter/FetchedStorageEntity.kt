package com.ecwid.apiclient.v3.converter

import com.ecwid.apiclient.v3.dto.storage.request.UpdatedStorageEntity
import com.ecwid.apiclient.v3.dto.storage.result.FetchedStorageEntity

fun FetchedStorageEntity.toUpdated(): UpdatedStorageEntity {
	return UpdatedStorageEntity(key = key, value = value)
}
