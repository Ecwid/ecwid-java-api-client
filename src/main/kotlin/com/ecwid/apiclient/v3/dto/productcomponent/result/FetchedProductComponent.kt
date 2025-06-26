package com.ecwid.apiclient.v3.dto.productcomponent.result

import com.ecwid.apiclient.v3.dto.common.ApiFetchedDTO
import com.ecwid.apiclient.v3.dto.common.ApiResultDTO

data class FetchedProductComponent(
	val productId: Long = 0L,
	val combinationId: Long? = null,
	val quantity: Int = 0,
): ApiFetchedDTO, ApiResultDTO {

	override fun getModifyKind() = ApiFetchedDTO.ModifyKind.ReadOnly

}
