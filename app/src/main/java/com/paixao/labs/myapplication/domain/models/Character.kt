package com.paixao.labs.myapplication.domain.models

data class Character(
    val name: String = "",
    val jobClass: JobClass = JobClass.Cleric,
    val level: Int = 0,
    val race: Race = Race.Human,
    val alignment: String = "",
    val attributes: Attributes = Attributes(),
    val id: String
)

data class Attributes(
    val strength: Int = 0,
    val agility: Int = 0,
    val constitution: Int = 0,
    val intelligence: Int = 0,
    val wisdom: Int = 0,
    val charisma: Int = 0,
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
