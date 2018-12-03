package util

import java.util.*

class PropertiesLoader {

	companion object {

		val storeId: Int
		val apiToken: String

		init {
			val resource = PropertiesLoader::class.java.getResourceAsStream("../test.properties")

			val p = Properties()
			p.load(resource)

			val storeIdStr = p.getProperty("storeId")
			if (storeIdStr.isNullOrEmpty()) {
				throw IllegalStateException("Parameter storeId is required")
			}

			val apiToken = p.getProperty("apiToken")
			if (apiToken.isNullOrEmpty()) {
				throw IllegalStateException("Parameter apiToken is required")
			}

			val storeId = storeIdStr.toIntOrNull()
			if (storeId == null || storeId <= 0) {
				throw IllegalStateException("Parameter storeId must be a valid Store ID")
			}

			Companion.storeId = storeId 
			Companion.apiToken = apiToken 
		}

	}

}