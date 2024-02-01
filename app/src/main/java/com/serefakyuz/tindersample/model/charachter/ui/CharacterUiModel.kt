package com.serefakyuz.tindersample.model.charachter.ui

import android.util.Log
import com.serefakyuz.tindersample.model.charachter.CharacterItem
import com.serefakyuz.tindersample.model.charachter.Status

data class CharacterUiModel(
    val character: CharacterItem
): ICharacterUIModel{
    override fun getImageUrl(): String = character.image?:""

    override fun getName(): String = character.name?:""

    override fun getStatus(): Status {
        return try {
            Status.safeValueOf(character.status?.trim()?.lowercase()?:"")
        }catch (ex: Exception){
            Log.e("CharacterUiModel", "Status  Null", )
            Status.UNKNOWN
        }
    }

    override fun getLocation(): String = character.location?.name?:""

}