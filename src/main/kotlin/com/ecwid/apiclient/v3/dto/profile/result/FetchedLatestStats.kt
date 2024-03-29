package com.ecwid.apiclient.v3.dto.profile.result

import com.ecwid.apiclient.v3.dto.common.ApiFetchedDTO
import com.ecwid.apiclient.v3.dto.common.ApiFetchedDTO.ModifyKind
import java.util.*

data class FetchedLatestStats(
	val productsUpdated: Date = Date(),
	val ordersUpdated: Date = Date(),
	val profileUpdated: Date = Date(),
	val categoriesUpdated: Date = Date(),
	val discountCouponsUpdated: Date = Date(),
	val abandonedSalesUpdated: Date = Date(),
	val customersUpdated: Date = Date(),
	val productCount: Int? = null,
	val categoryCount: Int? = null,
) : ApiFetchedDTO {

	override fun getModifyKind() = ModifyKind.ReadOnly
}
