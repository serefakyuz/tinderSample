package com.serefakyuz.tindersample.model.charachter.ui

import com.serefakyuz.tindersample.model.charachter.Status

interface ICharacterUIModel {
    fun getImageUrl(): String
    fun getName(): String
    fun getStatus(): Status
    fun getLocation(): String
}