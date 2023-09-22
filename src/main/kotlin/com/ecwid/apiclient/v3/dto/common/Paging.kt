package com.ecwid.apiclient.v3.dto.common

interface PagingRequest<Self : PagingRequest<Self>> {
	val offset: Int
	fun copyWithOffset(offset: Int): Self
}

interface PagingResult<Item> {
	val limit: Int
	val items: List<Item>
}

fun <Request : PagingRequest<Request>, Result : PagingResult<Item>, Item> fetchPagesAsItemSequence(
	request: Request,
	serviceMethod: (Request) -> Result,
): Sequence<Item> = sequence {
	@Suppress("NAME_SHADOWING")
	var request = request

	do {
		val result = serviceMethod.invoke(request)
		yieldAll(result.items)
		request = request.copyWithOffset(request.offset + result.items.size)
	} while (result.items.size >= result.limit)
}
