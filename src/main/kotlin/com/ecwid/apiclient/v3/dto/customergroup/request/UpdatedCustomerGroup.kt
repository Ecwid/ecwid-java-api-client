package com.ecwid.apiclient.v3.dto.customergroup.request

import com.ecwid.apiclient.v3.dto.common.ApiUpdatedDTO

data class UpdatedCustomerGroup(
		val name: String = ""
) : ApiUpdatedDTO
