package com.ecwid.apiclient.v3.util

import java.text.SimpleDateFormat
import java.util.*

internal fun dateToUtc(date: Date): Date {
	val simpleDateFormat = SimpleDateFormat("yyyy-MMM-dd HH:mm:ss")
	simpleDateFormat.timeZone = TimeZone.getTimeZone("UTC")
	val localDateFormat = SimpleDateFormat("yyyy-MMM-dd HH:mm:ss")
	return localDateFormat.parse(simpleDateFormat.format(date))
}
