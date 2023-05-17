package com.ecwid.apiclient.v3.converter

import com.ecwid.apiclient.v3.dto.instantsite.redirects.request.UpdatedInstantSiteRedirect
import com.ecwid.apiclient.v3.dto.instantsite.redirects.result.FetchedInstantSiteRedirect

fun FetchedInstantSiteRedirect.toUpdated() : UpdatedInstantSiteRedirect {
	return UpdatedInstantSiteRedirect(
		fromUrl = fromUrl,
		toUrl = toUrl,
	)
}
