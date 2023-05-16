package com.ecwid.apiclient.v3.dto.instantsite.redirects.result

import com.ecwid.apiclient.v3.dto.common.ApiFetchedDTO
import com.ecwid.apiclient.v3.dto.instantsite.redirects.request.UpdatedInstantSiteRedirect

data class FetchedInstantSiteRedirect(
	val id: String = "",
	val fromUrl: String = "",
	val toUrl: String = "",
): ApiFetchedDTO {
	override fun getModifyKind() = ApiFetchedDTO.ModifyKind.ReadWrite(UpdatedInstantSiteRedirect::class)
}
