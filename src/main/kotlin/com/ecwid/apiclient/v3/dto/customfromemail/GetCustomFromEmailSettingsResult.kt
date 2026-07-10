package com.ecwid.apiclient.v3.dto.customfromemail

import com.ecwid.apiclient.v3.dto.common.ApiResultDTO

data class GetCustomFromEmailSettingsResult(
	val currentEmail: CurrentEmail?,
	val emailOnValidation: EmailOnValidation?,
) : ApiResultDTO

data class CurrentEmail(
	val email: String,
)

data class EmailOnValidation(
	val email: String,
	val confirmedViaLink: Boolean,
	val secondsUntilNextConfirmationEmailAvaialble: Int,
	val domainVerificationStarted: Boolean,
)
