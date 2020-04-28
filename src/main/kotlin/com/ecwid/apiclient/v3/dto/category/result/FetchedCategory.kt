package com.ecwid.apiclient.v3.dto.category.result

import com.ecwid.apiclient.v3.dto.common.LocalizedValueMap
import com.ecwid.apiclient.v3.dto.common.PictureInfo

data class FetchedCategory(
		val id: Int = 0,
		val parentId: Int? = null,
		val orderBy: Int? = null,
		val name: String = "",
		val isSampleCategory: Boolean? = null,
		val nameTranslated: LocalizedValueMap? = null,
		val description: String? = null,
		val descriptionTranslated: LocalizedValueMap? = null,
		val enabled: Boolean = true,
		val productIds: List<Int>? = null,

		val hdThumbnailUrl: String? = null,
		val thumbnailUrl: String? = null,
		val imageUrl: String? = null, // TODO Cannot test due to bug https://track.ecwid.com/youtrack/issue/ECWID-53222 
		val originalImageUrl: String? = null,
		val originalImage: PictureInfo? = null,
		val url: String? = null,
		val productCount: Int? = null,
		val enabledProductCount: Int? = null
)
