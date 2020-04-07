package com.ecwid.apiclient.v3.util

import java.util.*

private const val DEFAULT_API_HOST = "app.ecwid.com"
private const val DEFAULT_API_PORT = 443

class PropertiesLoader {
	companion object {
		sealed class LoadResult {
			data class Success(val properties: TestProperties) : LoadResult()
			data class Failed(val message: String) : LoadResult()
		}

		private val lazyLoadProperties = lazy {
			val envProperties = fromEnv()
			if (envProperties != null) {
				LoadResult.Success(envProperties)
			} else {
				fromResource("/test.properties")
			}
		}

		fun load(): TestProperties {
			return when (val loadResult = lazyLoadProperties.value) {
				is LoadResult.Success -> loadResult.properties
				is LoadResult.Failed -> throw PropertiesLoadError(loadResult.message)
			}
		}

		private fun fromEnv(): TestProperties? {
			val apiHost = System.getenv("API_HOST") ?: DEFAULT_API_HOST
			val apiPort = System.getenv("API_PORT")?.toIntOrNull() ?: DEFAULT_API_PORT
			val storeId = System.getenv("STORE_ID")?.toIntOrNull()
					?: return null
			val apiToken = System.getenv("API_TOKEN")
					?: return null

			return TestProperties(apiHost, apiPort, storeId, apiToken)
		}

		private fun fromResource(resourceName: String): LoadResult {
			val resource = PropertiesLoader::class.java.getResourceAsStream(resourceName)
					?: return LoadResult.Failed("File $resourceName not found in test/resources dir. Please copy test/resources/test.properties.sample and configure it")

			val p = Properties()
			p.load(resource)

			val apiHostStr = p.getProperty("apiHost")
			val apiHost = if (apiHostStr.isNullOrEmpty()) {
				DEFAULT_API_HOST
			} else {
				apiHostStr
			}

			val apiPort = p.getProperty("apiPort")?.toIntOrNull() ?: DEFAULT_API_PORT

			val storeIdStr = p.getProperty("storeId")
			if (storeIdStr.isNullOrEmpty()) {
				return LoadResult.Failed("Parameter storeId is required")
			}

			val apiToken = p.getProperty("apiToken")
			if (apiToken.isNullOrEmpty()) {
				return LoadResult.Failed("Parameter apiToken is required")
			}

			val storeId = storeIdStr.toIntOrNull()
			if (storeId == null || storeId <= 0) {
				return LoadResult.Failed("Parameter storeId must be a valid Store ID")
			}

			val testProperties = TestProperties(apiHost, apiPort, storeId, apiToken)
			return LoadResult.Success(testProperties)
		}
	}
}
