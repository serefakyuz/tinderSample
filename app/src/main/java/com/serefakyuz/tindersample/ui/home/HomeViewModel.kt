package com.serefakyuz.tindersample.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.serefakyuz.tindersample.common.doOnFailure
import com.serefakyuz.tindersample.common.doOnLoading
import com.serefakyuz.tindersample.common.doOnSuccess
import com.serefakyuz.tindersample.model.charachter.ui.CharacterUiModel
import com.serefakyuz.tindersample.repository.CharacterUseCase
import com.serefakyuz.tindersample.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val characterUseCase: CharacterUseCase
): BaseViewModel(){

    private val _characterListResponse:MutableLiveData<List<CharacterUiModel>> = MutableLiveData()
    val characterListResponse:LiveData<List<CharacterUiModel>> = _characterListResponse


    init {
        Log.e("TAGTAG", "INIT" )
        getCharacterList(0)
    }


    private fun getCharacterList(page: Int) = viewModelScope.launch {
        characterUseCase.getCharacterList(page)
            .doOnSuccess {
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