package com.paixao.labs.myapplication.ui.sheet

import androidx.lifecycle.ViewModel
import com.paixao.labs.myapplication.domain.Attributes
import com.paixao.labs.myapplication.domain.CharacterSheet
import com.paixao.labs.myapplication.domain.JobClass
import com.paixao.labs.myapplication.domain.Race
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

internal class SheetViewModel : ViewModel() {
    private val _characterSheet: MutableStateFlow<CharacterSheet> =
        MutableStateFlow(mockedHero())

    fun retrieveSheet() = _characterSheet.asStateFlow()

    fun mockedHero() = CharacterSheet(
        name = "Konnagan",
        jobClass = JobClass.Ranger,
        alignment = "Leal e bom", level = 1,
        race = Race.Human,
        attributes = Attributes(
            strength = 14,
            dexterity = 18,
            constitution = 12,
            intelligence = 12,
            wisdom = 14,
            charisma = 10
        )
    )
}
