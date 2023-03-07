package com.ecwid.apiclient.v3.impl

import com.ecwid.apiclient.v3.ApiClientHelper
import com.ecwid.apiclient.v3.ReportsApiClient
import com.ecwid.apiclient.v3.dto.report.request.ReportRequest
import com.ecwid.apiclient.v3.dto.report.result.FetchedReportResponse

class ReportsApiClientImpl(
	private val apiClientHelper: ApiClientHelper
) : ReportsApiClient {

	override fun fetchReport(request: ReportRequest) =
		apiClientHelper.makeObjectResultRequest<FetchedReportResponse>(request)

}
