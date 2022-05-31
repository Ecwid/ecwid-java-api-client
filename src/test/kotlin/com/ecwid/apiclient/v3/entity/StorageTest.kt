package com.ecwid.apiclient.v3.entity

import com.ecwid.apiclient.v3.converter.toUpdated
import com.ecwid.apiclient.v3.dto.storage.request.*
import com.ecwid.apiclient.v3.jsontransformer.gson.GsonTransformer
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class StorageTest : BaseEntityTest() {

	@BeforeEach
	override fun beforeEach() {
		super.beforeEach()
	}

	@Test
	fun testStorageApiSingleItemCreation() {
		val updateData = StorageApiData(123, "test")
		val key = "key"
		val value = updateData.toJson()

		val updatedStorageEntity = UpdatedStorageData(key = key, value = value)
		val updateRequest = StorageEntityUpdateRequest(updatedStorageEntity)
		val updateResult = apiClient.createOrUpdateStorageEntity(updateRequest)
		assertEquals(1, updateResult.updateCount)

		val storageEntityRequest = StorageEntityRequest(key)
		val storageEntity = apiClient.getStorageEntity(storageEntityRequest)
		assertEquals(updatedStorageEntity, storageEntity.toUpdated())

		val fetchedData = StorageApiData.fromJson(storageEntity.value)
		assertEquals(updateData, fetchedData)

		val deleteRequest = StorageEntityDeleteRequest(key)
		val deleteResult = apiClient.deleteStorageEntity(deleteRequest)
		assertEquals(1, deleteResult.deleteCount)
	}

	@Test
	fun testStorageApiMultipleItemsCreation() {
		val updatedEntities = listOf(
			UpdatedStorageData(key = "key1", value = StorageApiData(123, "test1").toJson()),
			UpdatedStorageData(key = "key2", value = StorageApiData(456, "test2").toJson()),
		)

		updatedEntities.forEach { entity ->
			val updateRequest = StorageEntityUpdateRequest(entity)
			val updateResult = apiClient.createOrUpdateStorageEntity(updateRequest)
			assertEquals(1, updateResult.updateCount)
		}

		val storageEntitiesRequest = StorageEntitiesRequest()
		val storageEntitiesResult = apiClient.getAllStorageEntities(storageEntitiesRequest)
		storageEntitiesResult.forEach { fetchedEntity ->
			val updatedEntity = updatedEntities.find { it.key == fetchedEntity.key }
			assertNotNull(updatedEntity)
			assertEquals(updatedEntity, fetchedEntity.toUpdated())
		}

		updatedEntities.forEach { entity ->
			val deleteRequest = StorageEntityDeleteRequest(entity.key)
			val deleteResult = apiClient.deleteStorageEntity(deleteRequest)
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
