package com.ecwid.apiclient.v3.dto.customergroup.result

import com.ecwid.apiclient.v3.dto.common.ApiFetchedDTO

data class FetchedCustomerGroup(
		val id: Int = 0,
		val name: String = ""
) : ApiFetchedDTO
