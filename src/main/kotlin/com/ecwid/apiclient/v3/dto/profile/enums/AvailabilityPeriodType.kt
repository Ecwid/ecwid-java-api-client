package com.ecwid.apiclient.v3.dto.profile.enums

@Suppress("unused")
enum class AvailabilityPeriodType(val unitCount: Int, val unit: AvailabilityPeriodTimeUnit?) {
	SEVEN_DAYS(7, AvailabilityPeriodTimeUnit.DAYS),
	ONE_MONTH(1, AvailabilityPeriodTimeUnit.MONTHS),
	UNLIMITED(1200, AvailabilityPeriodTimeUnit.MONTHS);
}
