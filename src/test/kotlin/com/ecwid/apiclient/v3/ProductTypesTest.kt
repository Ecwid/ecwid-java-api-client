package com.ecwid.apiclient.v3

import com.ecwid.apiclient.v3.converter.toUpdated
import com.ecwid.apiclient.v3.dto.producttype.enums.AttributeDisplayType
import com.ecwid.apiclient.v3.dto.producttype.enums.AttributeType
import com.ecwid.apiclient.v3.dto.producttype.request.*
import com.ecwid.apiclient.v3.dto.producttype.result.FetchedProductType
import com.ecwid.apiclient.v3.exception.EcwidApiException
import com.ecwid.apiclient.v3.util.randomAlphanumeric
import com.ecwid.apiclient.v3.util.randomEnumValue
import com.ecwid.apiclient.v3.util.randomOf
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test


class ProductTypesTest: BaseEntityTest() {

	@BeforeEach
	override fun beforeEach() {
		super.beforeEach()

		// We need to start from scratch each time
		removeAllProductTypes()
	}

	@Test
	fun testProductTypeLifecycle() {
		// Creating new product type
		val productTypeCreateRequest = ProductTypeCreateRequest(
				newProductType = generateTestProductTypeForCreate()
		)
		val productTypeCreateResult = apiClient.createProductType(productTypeCreateRequest)
		assertTrue(productTypeCreateResult.id > 0)

		// Checking that product type was successfully created with necessary parameters
		val productTypeDetailsRequest = ProductTypeDetailsRequest(productTypeId = productTypeCreateResult.id)
		val productTypeDetails1 = apiClient.getProductTypeDetails(productTypeDetailsRequest)
		assertEquals(productTypeCreateRequest.newProductType, productTypeDetails1.toUpdated().cleanupAttributeIds())

		// Renaming and replacing all product type attributes
		val productTypeUpdateRequest = ProductTypeUpdateRequest(
				productTypeId = productTypeDetails1.id,
				updatedProductType = generateTestProductTypeForUpdate(productTypeDetails1)
		)
		val productTypeUpdateResult1 = apiClient.updateProductType(productTypeUpdateRequest)
		assertEquals(1, productTypeUpdateResult1.updateCount)

		// Checking that product type was successfully updated with necessary parameters and
		// product attributes ware just renamed not recreated
		val productTypeDetails2 = apiClient.getProductTypeDetails(productTypeDetailsRequest)
		assertEquals(productTypeUpdateRequest.updatedProductType, productTypeDetails2.toUpdated())
		productTypeDetails1.attributes?.forEachIndexed { index, attribute ->
			assertEquals(attribute.id, productTypeDetails2.attributes?.get(index)?.id)
		}

		// Deleting product type
		val productTypeDeleteRequest = ProductTypeDeleteRequest(productTypeId = productTypeDetails1.id)
		val productTypeDeleteResult = apiClient.deleteProductType(productTypeDeleteRequest)
		assertEquals(1, productTypeDeleteResult.deleteCount)

		// Checking that deleted product type is not accessible anymore
		try {
			apiClient.getProductTypeDetails(productTypeDetailsRequest)
		} catch (e: EcwidApiException) {
			assertEquals(404, e.statusCode)
			assertEquals("Product class #${productTypeCreateResult.id} not found", e.message)
		}
	}

	@Test
	fun testGetAllProductTypes() {
		// Create three product types additionally to always existing “General” product type
		for (i in 1..3) {
			val productTypeCreateRequest = ProductTypeCreateRequest(
					newProductType = UpdatedProductType(
							name = "Product type " + randomAlphanumeric(8)
					)
			)
			val productTypeCreateResult = apiClient.createProductType(productTypeCreateRequest)
			assertTrue(productTypeCreateResult.id > 0)
		}

		// Trying to request all product types 
		val customerGroupsSearchResult = apiClient.getAllProductTypes(ProductTypesGetAllRequest())
		assertEquals(3 + 1, customerGroupsSearchResult.size) // “General” product type always exists in every store
	}

	private fun removeAllProductTypes() {
		apiClient
				.getAllProductTypes(ProductTypesGetAllRequest())
				.map(FetchedProductType::id)
				.filter { productTypeId -> productTypeId > 0 } // We cannot delete “General” product type
				.forEach { productTypeId ->
					apiClient.deleteProductType(ProductTypeDeleteRequest(productTypeId))
				}
	}

}

private fun generateTestProductTypeForCreate(): UpdatedProductType {
	return UpdatedProductType(
			name = randomOf("Shirts & Tops", "Books", "Tablet Computers"),
			attributes = AttributeType.values().map(::generateProductAttribute) 
	)
}

private fun generateTestProductTypeForUpdate(source: FetchedProductType): UpdatedProductType {
	return UpdatedProductType(
			name = "Product type " + randomAlphanumeric(8),
			attributes = source.attributes?.map { attribute ->
				attribute.toUpdated().copy(
						name = "Updated " + attribute.name
				)
			}
	)
}

private fun generateProductAttribute(attributeType: AttributeType): UpdatedProductType.Attribute {
	return UpdatedProductType.Attribute(
			name = "Attribute ${attributeType.name}: ${randomAlphanumeric(8)}",
			type = attributeType,
			show = randomEnumValue<AttributeDisplayType>()
	)
}

private fun UpdatedProductType.cleanupAttributeIds(): UpdatedProductType {
	return this.copy(
			attributes = attributes?.map { attribute -> attribute.copy(id = null) }
	)
}
