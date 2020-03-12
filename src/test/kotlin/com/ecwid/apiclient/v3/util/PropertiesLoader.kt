package com.ecwid.apiclient.v3.util

import java.util.*

class PropertiesLoader {

	companion object {

		val apiHost: String
		val apiPort: Int
		val storeId: Int
		val apiToken: String

		init {
			val resource = PropertiesLoader::class.java.getResourceAsStream("/test.properties")
					?: throw IllegalStateException("File test.properties not found in test/resources dir. Please copy test/resources/test.properties.sample and configure it")

			val p = Properties()
			p.load(resource)

			val apiHostStr = p.getProperty("apiHost")
			apiHost = if (apiHostStr.isNullOrEmpty()) {
				"app.ecwid.com"
			} else {
				apiHostStr
			}

			val apiPortStr = p.getProperty("apiPort")
			apiPort = if (apiHostStr.isNullOrEmpty()) {
				443
			} else {
				apiPortStr.toInt()
			}

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
