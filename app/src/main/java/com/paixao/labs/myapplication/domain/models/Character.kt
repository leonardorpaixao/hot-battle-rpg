package com.paixao.labs.myapplication.domain.models

import com.paixao.labs.myapplication.domain.models.Attribute.Agility
import com.paixao.labs.myapplication.domain.models.Attribute.Charisma
import com.paixao.labs.myapplication.domain.models.Attribute.Constitution
import com.paixao.labs.myapplication.domain.models.Attribute.Intelligence
import com.paixao.labs.myapplication.domain.models.Attribute.Strength
import com.paixao.labs.myapplication.domain.models.Attribute.Wisdom

data class Character(
    val name: String = "",
    val jobClass: JobClass = JobClass.Cleric,
    val level: Int = 0,
    val race: Race = Race.Human,
    val alignment: String = "",
    val attributes: Attributes = Attributes(),
    val id: String,
) {
    companion object {
        val new = Character(id = "")
    }
}

data class Attributes(
    val strength: Strength = Strength(0),
    val agility: Agility = Agility(0),
    val constitution: Constitution = Constitution(0),
    val intelligence: Intelligence = Intelligence(0),
    val wisdom: Wisdom = Wisdom(0),
    val charisma: Charisma = Charisma(0),
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
