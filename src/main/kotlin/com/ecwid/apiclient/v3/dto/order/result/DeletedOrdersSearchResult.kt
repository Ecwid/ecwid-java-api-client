package com.ecwid.apiclient.v3.dto.order.result

data class DeletedOrdersSearchResult(
	var items: List<DeletedOrder> = listOf(),
	var count: Int = 0,
	var total: Int = 0,
	var limit: Int = 0,
	var offset: Int = 0
)