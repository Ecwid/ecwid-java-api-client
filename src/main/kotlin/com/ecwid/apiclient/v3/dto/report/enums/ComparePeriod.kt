package com.ecwid.apiclient.v3.dto.report.enums

import com.google.gson.annotations.SerializedName

enum class ComparePeriod(val alias: String) {
	@SerializedName("previousPeriod")
	PREVIOUS_PERIOD("previousPeriod"),

	@SerializedName("similarPeriodInPreviousWeek")
	SIMILAR_PERIOD_IN_PREVIOUS_WEEK("similarPeriodInPreviousWeek"),

	@SerializedName("similarPeriodInPreviousMonth")
	SIMILAR_PERIOD_IN_PREVIOUS_MONTH("similarPeriodInPreviousMonth"),

	@SerializedName("similarPeriodInPreviousYear")
	SIMILAR_PERIOD_IN_PREVIOUS_YEAR("similarPeriodInPreviousYear"),

	@SerializedName("noComparePeriod")
	NO_COMPARE_PERIOD("noComparePeriod");
}
