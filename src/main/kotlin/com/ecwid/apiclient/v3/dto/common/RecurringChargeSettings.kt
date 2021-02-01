package com.ecwid.apiclient.v3.dto.common

data class RecurringChargeSettings(
		val recurringInterval: RecurringSubscriptionInterval = RecurringSubscriptionInterval.MONTH,
		val recurringIntervalCount: Int = 1
)

enum class RecurringSubscriptionInterval {
	DAY, WEEK, MONTH, YEAR
}
