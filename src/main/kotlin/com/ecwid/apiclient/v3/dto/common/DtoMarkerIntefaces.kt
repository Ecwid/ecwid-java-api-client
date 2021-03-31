package com.ecwid.apiclient.v3.dto.common

import kotlin.reflect.KClass

interface ApiFetchedDTO {

	fun getModifyKind(): ModifyKind

	sealed class ModifyKind {
		object ReadOnly: ModifyKind()
		data class ReadWrite(val updatedDTOClass: KClass<out ApiUpdatedDTO>) : ModifyKind()
	}

}

interface ApiUpdatedDTO {

	fun getModifyKind(): ModifyKind

	sealed class ModifyKind {
		data class ReadWrite(val fetchedDTOClass: KClass<out ApiFetchedDTO>) : ModifyKind()
	}

}

interface ApiRequestDTO
interface ApiResultDTO
