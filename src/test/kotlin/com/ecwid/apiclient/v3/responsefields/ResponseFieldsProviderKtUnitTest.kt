package com.ecwid.apiclient.v3.responsefields

import com.ecwid.apiclient.v3.dto.common.ApiResultDTO
import com.ecwid.apiclient.v3.dto.common.LocalizedValueMap
import com.ecwid.apiclient.v3.dto.common.PartialResult
import com.ecwid.apiclient.v3.dto.profile.result.FetchedStoreProfile
import com.ecwid.apiclient.v3.jsontransformer.JsonFieldName
import kotlin.test.*


class ResponseFieldsProviderKtUnitTest {
	@Test
	fun `test success`() {
		data class InstantSiteInfo(
			val ecwidSubdomain: String? = null,
			val ecwidSubdomainSuffix: String? = null
		)

		data class GeneralInfo(
			val storeId: Int,
			val storeUrl: String?,
			val starterSite: InstantSiteInfo?,
			val websitePlatform: FetchedStoreProfile.WebsitePlatform?,
		)

		data class AbandonedSalesSettings(
			val autoAbandonedSalesRecovery: Boolean?,
		)

		data class Settings(
			val abandonedSales: AbandonedSalesSettings?,
			val storeDescription: String?,
			val storeDescriptionTranslated: LocalizedValueMap?,
		)

		data class FeatureTogglesInfo(
			val name: String?,
			val visible: Boolean?,
			val enabled: Boolean?,
		)

		data class StoreProfile(
			val generalInfo: GeneralInfo?,
			val settings: Settings?,
			val featureToggles: List<FeatureTogglesInfo>? = null,
		) : PartialResult<FetchedStoreProfile>

		assertEquals(
			"featureToggles(enabled,name,visible)," +
				"generalInfo(starterSite(ecwidSubdomain,ecwidSubdomainSuffix),storeId,storeUrl,websitePlatform)," +
				"settings(abandonedSales(autoAbandonedSalesRecovery),storeDescription,storeDescriptionTranslated)",
			responseFieldsOf<StoreProfile>().toHttpParameter()
		)
	}

	@Test
	fun `failure - not a data class`() {
		data class GeneralInfo(val storeId: Int)
		class StoreProfileNotDataClass(val generalInfo: GeneralInfo?) : PartialResult<FetchedStoreProfile>

		assertFailsWith<IncompatiblePartialResultClassException.NotDataClass> {
			responseFieldsOf<StoreProfileNotDataClass>()
		}.also {
			assertEquals(StoreProfileNotDataClass::class, it.klass)
		}
	}

	@Test
	fun `failure - type parameters`() {
		data class GeneralInfoWithParam<T>(val storeId: T)
		data class GeneralInfo(val storeId: Int)
		data class StoreProfileWithParam<T : ApiResultDTO>(val generalInfo: GeneralInfo?) : PartialResult<T>
		data class StoreProfile(val generalInfo: GeneralInfoWithParam<Int>?) : PartialResult<FetchedStoreProfile>

		assertFailsWith<IncompatiblePartialResultClassException.HasTypeParameters> {
			responseFieldsOf<StoreProfileWithParam<FetchedStoreProfile>>()
		}.also {
			assertEquals(StoreProfileWithParam::class, it.klass)
		}

		assertFailsWith<IncompatiblePartialResultClassException.HasTypeParameters> {
			responseFieldsOf<StoreProfile>()
		}.also {
			assertEquals(GeneralInfoWithParam::class, it.klass)
		}
	}

	@Test
	fun `failure - extraneous property`() {
		data class GeneralInfo(val storeId: Int, val nonExistingProperty: String)
		data class StoreProfile(val generalInfo: GeneralInfo?) : PartialResult<FetchedStoreProfile>

		assertFailsWith<IncompatiblePartialResultClassException.ExtraneousProperty> {
			responseFieldsOf<StoreProfile>()
		}.also {
			assertEquals(GeneralInfo::nonExistingProperty, it.partialResultProperty)
		}
	}

	@Test
	fun `failure - incompatible types`() {
		data class GeneralInfo(val storeId: String)
		data class StoreProfile(val generalInfo: GeneralInfo?) : PartialResult<FetchedStoreProfile>

		assertFailsWith<IncompatiblePartialResultClassException.IncompatiblePropertyTypes> {
			responseFieldsOf<StoreProfile>()
		}.also {
			assertEquals(GeneralInfo::storeId, it.partialResultProperty)
			assertEquals(FetchedStoreProfile.GeneralInfo::storeId, it.fullResultProperty)
		}
	}

	@Test
	fun `failure - incompatible annotations`() {
		data class GeneralInfo(@JsonFieldName("store_id") val storeId: Int)
		data class StoreProfile(val generalInfo: GeneralInfo?) : PartialResult<FetchedStoreProfile>

		assertFailsWith<IncompatiblePartialResultClassException.IncompatiblePropertyAnnotations> {
			responseFieldsOf<StoreProfile>()
		}.also {
			assertEquals(GeneralInfo::storeId, it.partialResultProperty)
			assertEquals(FetchedStoreProfile.GeneralInfo::storeId, it.fullResultProperty)
		}
	}
}
