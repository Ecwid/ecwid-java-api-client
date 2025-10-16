package com.ecwid.apiclient.v3


import com.ecwid.apiclient.v3.util.createSecurePatterns
import com.ecwid.apiclient.v3.util.maskLogString
import com.ecwid.apiclient.v3.util.maskSensitive
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MaskUtilsUnitTest {

	@Test
	fun testMaskLogKeyValueString() {
		val logString =
			"token=secret_RandomToken0jwOrgYc5sSKBYcvO0DbP; PasswordCredentials(email=test@example.com, password=1234567890)"
		val securePatterns = createSecurePatterns()

		val maskedLogString = logString.maskLogString(securePatterns)
		val expectedMaskedLogString = "token=sec***DbP; PasswordCredentials(email=tes***com, password=12***890)"
		assertEquals(expectedMaskedLogString, maskedLogString)
	}

	@Test
	fun testMaskLogJsonString() {
		val logString =
			"""{"billingPerson":{"email":"alexis@ecwid.com","firstName":"John","lastName":"Smith","phone":"123467890"}}"""
		val securePatterns = createSecurePatterns()

		val maskedLogString = logString.maskLogString(securePatterns)
		val expectedMaskedLogString =
			"""{"billingPerson":{"email":"ale***com","firstName":"***","lastName":"***","phone":"***"}}"""
		assertEquals(expectedMaskedLogString, maskedLogString)
	}

	@Test
	fun testMaskLogKeyValueStringWithNameParameter() {
		val logString =
			"UpdatedProduct(name={unmasked}, " +
				"attributes=[AttributeValue(name={unmasked})], " +
				"options=[RadioOption(name={unmasked})], " +
				"billingPerson=BillingPerson(name={unmasked}), " +
				"shippingAddresses=[ShippingAddress(name={unmasked}), " +
				"ShippingAddress(name={unmasked})], " +
				"personInfo=PersonInfo(name={unmasked}))"
		val securePatterns = createSecurePatterns()
		val maskedLogString = logString.maskLogString(securePatterns)
		val expectedMaskedLogString =
			"UpdatedProduct(name={unmasked}, " +
				"attributes=[AttributeValue(name={unmasked})], " +
				"options=[RadioOption(name={unmasked})], " +
				"billingPerson=BillingPerson(name={u***ed}), " +
				"shippingAddresses=[ShippingAddress(name={u***ed}), " +
				"ShippingAddress(name={u***ed})], " +
				"personInfo=PersonInfo(name={u***ed}))"
		assertEquals(expectedMaskedLogString, maskedLogString)
	}

	@Test
	fun testMaskSensitive() {
		assertEquals("te***ng", "test string".maskSensitive(4))
		assertEquals("***", "test string".maskSensitive(8))
	}

}
