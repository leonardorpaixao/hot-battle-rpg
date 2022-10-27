package com.paixao.labs.myapplication.domain.models

data class User(
    val id: String = "",
    val name: String = "",
    val characters: List<Character> = emptyList(),
    val master: Master? = null,
)

data class Master(
    val id: String = "",
    val name: String = "",
    val tables: List<Table> = emptyList(),
)

data class Table(
    val tableCode: String = "",
    val adventureName: String = "",
    val adventureSystem: String = "",
    val lore: String = "",
    val masterId: String = "",
    val masterName: String = ""
)