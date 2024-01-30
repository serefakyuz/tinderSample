package com.serefakyuz.tindersample.di

import com.serefakyuz.tindersample.repository.CharacterRepository
import com.serefakyuz.tindersample.repository.ICharacterRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun providesCharacterRepository(
        movieRepositoryImp: CharacterRepository
    ):ICharacterRepository
}