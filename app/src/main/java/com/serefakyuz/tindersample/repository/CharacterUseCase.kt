package com.serefakyuz.tindersample.repository

import com.serefakyuz.tindersample.common.Result
import com.serefakyuz.tindersample.common.map
import com.serefakyuz.tindersample.model.charachter.CharacterListResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CharacterUseCase @Inject constructor(private val repository: ICharacterRepository){

    suspend fun getCharacterList(page: Int): Flow<Result<CharacterListResponse>> {
        return repository.getCharacterList(page).map { result->
            result.map {
                it
            }
        }
    }
}