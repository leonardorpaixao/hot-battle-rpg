package com.paixao.labs.myapplication.data.models

import com.paixao.labs.myapplication.domain.models.Attributes

data class UserResponse(
    val id: String = "",
    val name: String = "",
    val characters: List<CharacterResponse> = emptyList(),
    val master: MasterResponse? = null,
)

data class CharacterResponse(
    val name: String = "",
    val jobClass: String = "",
    val level: Int = 0,
    val race: String = "",
    val alignment: String = "",
    val attributes: Attributes = Attributes(),
)

data class MasterResponse(
    val id: String = "",
    val name: String = "",
    val tables: List<TableResponse> = emptyList(),
)

data class TableResponse(
    val id: String = "",
    val adventureName: String = "",
)