package com.serefakyuz.tindersample.model.charachter.mapper

import com.serefakyuz.tindersample.common.base.Mapper
import com.serefakyuz.tindersample.model.charachter.CharacterItem
import com.serefakyuz.tindersample.model.charachter.CharacterListResponse
import com.serefakyuz.tindersample.model.charachter.ui.CharacterUiModel
import javax.inject.Inject

class CharacterMapper @Inject constructor():
    Mapper<CharacterListResponse?, List<CharacterUiModel>?> {

    override fun mapFrom(from: CharacterListResponse?): List<CharacterUiModel>? {
        return from?.results?.map {
            CharacterUiModel(
                CharacterItem(
                    created = it?.created,
                    episode = it?.episode,
                    gender = it?.gender,
                    id = it?.id,
                    image = it?.image,
                    location = it?.location,
                    name = it?.name,
                    origin = it?.origin,
                    species = it?.species,
                    status = it?.status,
                    type = it?.type,
                    url = it?.url,
                )
            )
        }
    }
}