package com.ecwid.apiclient.v3.impl

import com.ecwid.apiclient.v3.ApiClientHelper
import com.ecwid.apiclient.v3.ReportApiClient
import com.ecwid.apiclient.v3.dto.report.request.ReportRequest
import com.ecwid.apiclient.v3.dto.report.result.FetchedReportResponse

class ReportApiClientImpl(
	private val apiClientHelper: ApiClientHelper
) : ReportApiClient {

	override fun fetchReport(request: ReportRequest) =
		apiClientHelper.makeObjectResultRequest<FetchedReportResponse>(request)

}
