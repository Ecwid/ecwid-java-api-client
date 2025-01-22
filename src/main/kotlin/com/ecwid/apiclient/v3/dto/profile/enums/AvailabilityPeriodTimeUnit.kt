package com.ecwid.apiclient.v3.dto.profile.enums

private const val MINUTES_IN_DAY = 1_440 // 24 hours * 60 minutes
private const val MINUTES_IN_MONTH = 43_200 // 30 * MINUTES_IN_DAY

@Suppress("unused")
enum class AvailabilityPeriodTimeUnit(val valueInMinutes: Int) {
	DAYS(MINUTES_IN_DAY),
	MONTHS(MINUTES_IN_MONTH)
}
