package com.paixao.labs.myapplication.domain

data class CharacterSheet(
    val name: String,
    val jobClass: JobClass,
    val level: Int,
    val race: Race,
    val alignment: String,
    val attributes: Attributes
)

data class Attributes(
    val strength: Int,
    val dexterity: Int,
    val constitution: Int,
    val intelligence: Int,
    val wisdom: Int,
    val charisma: Int
)

enum class JobClass(val value: String) {
    Barbarian("Bárbaro"),
    Bard("Bardo"),
    Cleric("Clérigo"),
    Druid("Druida"),
    Fighter("Lutador"),
    Monk("Monge"),
    Paladin("Paladino"),
    Ranger("Ranger"),
    Rogue("Ladino"),
    Sorcerer("Feiticeiro"),
    Wizard("Mago")
}

enum class Race(val value: String) {
    DragonBorn("Dracônico"),
    Dwarf("Anão"),
    Elf("Elfo"),
    Gnome("Gnomo"),
    HalfElf("Meio-Elfo"),
    Halfling("Halfling"),
    HalfOrc("Meio Orc"),
    Human("Humano")
}
