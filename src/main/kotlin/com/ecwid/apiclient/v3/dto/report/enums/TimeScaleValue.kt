package com.ecwid.apiclient.v3.dto.report.enums

import com.google.gson.annotations.SerializedName

enum class TimeScaleValue(val alias: String) {
	@SerializedName("hour")
	HOUR("hour"),

	@SerializedName("day")
	DAY("day"),

	@SerializedName("week")
	WEEK("week"),

	@SerializedName("month")
	MONTH("month"),

	@SerializedName("year")
	YEAR("year");
}
