package com.ecwid.apiclient.v3.dto.report.enums

enum class ComparePeriod(val alias: String) {
	PREVIOUS_PERIOD("previousPeriod"),
	SIMILAR_PERIOD_IN_PREVIOUS_WEEK("similarPeriodInPreviousWeek"),
	SIMILAR_PERIOD_IN_PREVIOUS_MONTH("similarPeriodInPreviousMonth"),
	SIMILAR_PERIOD_IN_PREVIOUS_YEAR("similarPeriodInPreviousYear"),
	NO_COMPARE_PERIOD("noComparePeriod");
}
