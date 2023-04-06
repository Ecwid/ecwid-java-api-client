package com.ecwid.apiclient.v3.dto.category.request

import com.ecwid.apiclient.v3.dto.common.ApiRequestDTO

data class CategoryProductIds(val productIds: List<Int> = emptyList()) : ApiRequestDTO
