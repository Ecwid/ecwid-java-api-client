package com.ecwid.apiclient.v3.converter

import com.ecwid.apiclient.v3.dto.storage.request.UpdatedStorageData
import com.ecwid.apiclient.v3.dto.storage.result.FetchedStorageData

fun FetchedStorageData.toUpdated(): UpdatedStorageData {
	return UpdatedStorageData(key = key, value = value)
}
