package com.serefakyuz.tindersample.repository

import com.serefakyuz.tindersample.api.ApiService
import com.serefakyuz.tindersample.common.Result
import com.serefakyuz.tindersample.common.base.BaseRepository
import com.serefakyuz.tindersample.model.charachter.CharacterListResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CharacterRepository @Inject constructor(
    private val apiService: ApiService
): BaseRepository(), ICharacterRepository {
    override suspend fun getCharacterList(page: Int): Flow<Result<CharacterListResponse>> = safeApiCall {
        apiService.getCharacterList(page)
    }
}