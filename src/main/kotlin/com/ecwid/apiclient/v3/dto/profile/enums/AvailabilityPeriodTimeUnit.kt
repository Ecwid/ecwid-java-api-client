package com.ecwid.apiclient.v3.dto.profile.enums

@Suppress("unused")
enum class AvailabilityPeriodTimeUnit(val valueInMinutes: Int) {
	DAYS(24 * 60),
	MONTHS(30 * 24 * 60);
}
