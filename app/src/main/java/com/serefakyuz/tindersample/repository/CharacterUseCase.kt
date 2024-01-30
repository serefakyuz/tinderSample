package com.serefakyuz.tindersample.repository

import com.serefakyuz.tindersample.common.Result
import com.serefakyuz.tindersample.common.map
import com.serefakyuz.tindersample.model.charachter.mapper.CharacterMapper
import com.serefakyuz.tindersample.model.charachter.ui.CharacterUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CharacterUseCase @Inject constructor(private val repository: ICharacterRepository,
    private val characterMapper: CharacterMapper){

    suspend fun getCharacterList(page: Int): Flow<Result<List<CharacterUiModel>?>> {
        return repository.getCharacterList(page).map { result->
            result.map {
                characterMapper.mapFrom(it)
            }
        }
    }
}