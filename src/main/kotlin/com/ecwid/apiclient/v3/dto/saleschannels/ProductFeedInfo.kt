package com.ecwid.apiclient.v3.dto.saleschannels

import com.ecwid.apiclient.v3.dto.common.ApiFetchedDTO
import com.ecwid.apiclient.v3.dto.common.ApiFetchedDTO.DTOKind
import java.util.*

data class ProductFeedInfo(
		val productsProcessed: Int = 0,
		val productsIncluded: Int = 0,
		val lastGenerated: Date = Date(),
		val nextGenerated: Date = Date()
) : ApiFetchedDTO {

	override fun getKind() = DTOKind.ReadOnly

}

