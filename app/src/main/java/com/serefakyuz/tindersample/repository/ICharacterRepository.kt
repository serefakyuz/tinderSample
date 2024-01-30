package com.serefakyuz.tindersample.repository

import com.serefakyuz.tindersample.common.Result
import com.serefakyuz.tindersample.model.charachter.CharacterListResponse
import kotlinx.coroutines.flow.Flow

interface ICharacterRepository {
    suspend fun getCharacterList(page: Int): Flow<Result<CharacterListResponse>>
}