package com.ecwid.apiclient.v3.dto.report.enums

import com.google.gson.annotations.SerializedName

enum class FirstDayOfWeek(val alias: String) {
	@SerializedName("monday")
	MONDAY("monday"),

	@SerializedName("sunday")
	SUNDAY("sunday");
}
