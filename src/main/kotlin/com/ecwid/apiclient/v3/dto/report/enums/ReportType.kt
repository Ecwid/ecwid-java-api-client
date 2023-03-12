package com.ecwid.apiclient.v3.dto.report.enums

import com.google.gson.annotations.SerializedName

enum class ReportType(val alias: String) {
	@SerializedName("allTraffic")
	ALL_TRAFFIC("allTraffic"),

	@SerializedName("newVsReturningVisitors")
	NEW_VS_RETURNING_VISITORS("newVsReturningVisitors"),
}
