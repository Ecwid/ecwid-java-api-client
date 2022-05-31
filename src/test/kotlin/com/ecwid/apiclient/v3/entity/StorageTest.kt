package com.ecwid.apiclient.v3.entity

import com.ecwid.apiclient.v3.converter.toUpdated
import com.ecwid.apiclient.v3.dto.storage.request.*
import com.ecwid.apiclient.v3.jsontransformer.gson.GsonTransformer
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class StorageTest : BaseEntityTest() {

	@BeforeEach
	override fun beforeEach() {
		super.beforeEach()
	}

	@Test
	fun testStorageApiSingleItemOperations() {
		val createApiData = StorageApiData(123, "test")
		val key = "key"
		val createValue = createApiData.toJson()
		val createStorageData = UpdatedStorageData(key = key, value = createValue)
		val createRequest = StorageDataCreateRequest(createStorageData)
		val createResult = apiClient.createStorageData(createRequest)
		assertEquals(1, createResult.updateCount)

		val createdDataRequest = StorageDataRequest(key)
		val createdDataResult = apiClient.getStorageData(createdDataRequest)
		assertEquals(createStorageData, createdDataResult.toUpdated())

		val createdData = createdDataResult.value?.let { StorageApiData.fromJson(it) }
		assertEquals(createApiData, createdData)

		val updatedStorageData = UpdatedStorageData(key = key, value = null)
		val updateRequest = StorageDataUpdateRequest(updatedStorageData)
		val updateResult = apiClient.updateStorageData(updateRequest)
		assertEquals(1, updateResult.updateCount)

		val updatedDataRequest = StorageDataRequest(key)
		val updatedDataResult = apiClient.getStorageData(updatedDataRequest)
		assertTrue(updatedDataResult.value.isNullOrEmpty())

		val deleteRequest = StorageDataDeleteRequest(key)
		val deleteResult = apiClient.deleteStorageData(deleteRequest)
		assertEquals(1, deleteResult.deleteCount)
	}

	@Test
	fun testStorageApiMultipleItemsCreation() {
		val updatedEntities = listOf(
			UpdatedStorageData(key = "key1", value = StorageApiData(123, "test1").toJson()),
			UpdatedStorageData(key = "key2", value = StorageApiData(456, "test2").toJson()),
		)

		updatedEntities.forEach { entity ->
			val updateRequest = StorageDataUpdateRequest(entity)
			val updateResult = apiClient.updateStorageData(updateRequest)
			assertEquals(1, updateResult.updateCount)
		}

		val storageEntitiesRequest = AllStorageDataRequest()
		val storageEntitiesResult = apiClient.getAllStorageData(storageEntitiesRequest)
		storageEntitiesResult.forEach { fetchedEntity ->
			val updatedEntity = updatedEntities.find { it.key == fetchedEntity.key }
			assertNotNull(updatedEntity)
			assertEquals(updatedEntity, fetchedEntity.toUpdated())
		}

		updatedEntities.forEach { entity ->
			val deleteRequest = StorageDataDeleteRequest(entity.key)
			val deleteResult = apiClient.deleteStorageData(deleteRequest)
			assertEquals(1, deleteResult.deleteCount)
		}
	}

	private data class StorageApiData(
		val number: Int = 0,
		val string: String = "",
	) {
		fun toJson(): String {
			return transformer.serialize(this, null)
		}

		companion object {
			private val transformer = GsonTransformer(emptyList())

			fun fromJson(json: String): StorageApiData? {
				return transformer.deserialize(json, StorageApiData::class.java)
			}
		}
	}

}
