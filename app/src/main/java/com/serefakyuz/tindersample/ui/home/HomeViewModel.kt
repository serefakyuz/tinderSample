package com.serefakyuz.tindersample.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.serefakyuz.tindersample.common.doOnFailure
import com.serefakyuz.tindersample.common.doOnLoading
import com.serefakyuz.tindersample.common.doOnSuccess
import com.serefakyuz.tindersample.model.charachter.CharacterListResponse
import com.serefakyuz.tindersample.model.charachter.mapper.CharacterMapper
import com.serefakyuz.tindersample.model.charachter.ui.CharacterUiModel
import com.serefakyuz.tindersample.repository.CharacterUseCase
import com.serefakyuz.tindersample.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val characterUseCase: CharacterUseCase,
    private val characterMapper: CharacterMapper
): BaseViewModel(){

    private val _characterListResponse:MutableLiveData<CharacterListResponse> = MutableLiveData()
    val characterListResponse:LiveData<CharacterListResponse> = _characterListResponse

    val characterList: ArrayList<CharacterUiModel> = arrayListOf()


    init {
        getCharacterList(1)
    }

    fun loadMore(){
        val info = characterListResponse.value?.info
        info?.getNextPage()?.let {
            getCharacterList(it)
        }
    }

    private fun getCharacterList(page: Int) = viewModelScope.launch {
        characterUseCase.getCharacterList(page)
            .doOnSuccess {
                characterMapper.mapFrom(it)?.let { list ->
                    characterList.addAll(list)
                }
                _characterListResponse.value = it

            }
            .doOnFailure {
                _errorModel.value = it
            }
            .doOnLoading {
                _isLoading.value = true
            }.collect{

            Log.e("TAGTAG", "getCharacterList: Collect: $it" )
            }
    }
}