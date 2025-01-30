package com.ecwid.apiclient.v3.dto.profile.enums

private const val DAYS_IN_WEEK = 7
private const val SINGLE_UNIT = 1
private const val MONTHS_IN_CENTURY = 1200

@Suppress("unused")
enum class AvailabilityPeriodType(val unitCount: Int, val unit: AvailabilityPeriodTimeUnit?) {
	SEVEN_DAYS(DAYS_IN_WEEK, AvailabilityPeriodTimeUnit.DAYS),
	ONE_MONTH(SINGLE_UNIT, AvailabilityPeriodTimeUnit.MONTHS),
	UNLIMITED(MONTHS_IN_CENTURY, AvailabilityPeriodTimeUnit.MONTHS)
}
