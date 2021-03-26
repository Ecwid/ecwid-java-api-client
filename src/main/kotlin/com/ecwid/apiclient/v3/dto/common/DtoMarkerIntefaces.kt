package com.ecwid.apiclient.v3.dto.common

import kotlin.reflect.KClass

interface ApiFetchedDTO {

	fun getKind(): DTOKind

	sealed class DTOKind {
		object ReadOnly: DTOKind()
		data class ReadWrite(val updatedDTOClass: KClass<out ApiUpdatedDTO>) : DTOKind()
	}

}

interface ApiUpdatedDTO {

	fun getKind(): DTOKind

	sealed class DTOKind {
		data class ReadWrite(val fetchedDTOClass: KClass<out ApiFetchedDTO>) : DTOKind()
	}

}

interface ApiRequestDTO
interface ApiResultDTO
