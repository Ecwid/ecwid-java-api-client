package com.ecwid.apiclient.v3.dto.instantsite.redirects.request

import com.ecwid.apiclient.v3.dto.common.ApiUpdatedDTO
import com.ecwid.apiclient.v3.dto.instantsite.redirects.result.FetchedInstantSiteRedirect
data class UpdatedInstantSiteRedirect(
	val fromUrl: String? = null,
	val toUrl: String? = null,
) : ApiUpdatedDTO {
	override fun getModifyKind() = ApiUpdatedDTO.ModifyKind.ReadWrite(FetchedInstantSiteRedirect::class)
}
