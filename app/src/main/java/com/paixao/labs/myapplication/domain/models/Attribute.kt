package com.paixao.labs.myapplication.domain.models

sealed class Attribute(val value: Int) {
    data class Strength(val attrValue: Int) : Attribute(attrValue)
    data class Agility(val attrValue: Int) : Attribute(attrValue)
    data class Constitution(val attrValue: Int) : Attribute(attrValue)
    data class Intelligence(val attrValue: Int) : Attribute(attrValue)
    data class Wisdom(val attrValue: Int) : Attribute(attrValue)
    data class Charisma(val attrValue: Int) : Attribute(attrValue)
}