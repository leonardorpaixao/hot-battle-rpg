package com.paixao.labs.myapplication.data.models

data class UserResponse(
    val id: String = "",
    val name: String = "",
    val characters: List<CharacterResponse> = emptyList(),
    val master: MasterResponse? = null,
)

data class CharacterResponse(
    val id: String = "",
    val name: String = "",
    val jobClass: String = "",
    val level: Int = 0,
    val race: String = "",
    val alignment: String = "",
    val attributes: AttributesResponse = AttributesResponse(),
)

data class AttributesResponse(
    val strength: Int = 0,
    val agility: Int = 0,
    val constitution: Int = 0,
    val intelligence: Int = 0,
    val wisdom: Int = 0,
    val charisma: Int = 0,
)

data class MasterResponse(
    val id: String = "",
    val name: String = "",
    val tables: List<TableResponse> = emptyList(),
)

data class TableResponse(
    val tableCode: String = "",
    val adventureName: String = "",
    val lore: String = "",
    val adventureSystem: String = "",
    val masterId: String = ""
)
