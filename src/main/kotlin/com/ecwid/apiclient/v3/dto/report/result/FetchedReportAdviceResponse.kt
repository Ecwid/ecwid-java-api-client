package com.ecwid.apiclient.v3.dto.report.result

import com.ecwid.apiclient.v3.dto.common.ApiFetchedDTO

data class FetchedReportAdviceResponse(
	val tip: String? = null
) : ApiFetchedDTO {

	override fun getModifyKind() = ApiFetchedDTO.ModifyKind.ReadOnly

}
