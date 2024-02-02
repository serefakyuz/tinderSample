package com.serefakyuz.tindersample.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.serefakyuz.tindersample.databinding.ItemCharacterBinding
import com.serefakyuz.tindersample.model.charachter.ui.CharacterUiModel

class CharactersAdapter() :
    ListAdapter<CharacterUiModel, CharactersAdapter.CharacterViewHolder>(CharacterDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCharacterBinding.inflate(inflater, parent, false)
        return CharacterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class CharacterViewHolder(private val binding: ItemCharacterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(characterUiModel: CharacterUiModel) {
            binding.item = characterUiModel
        }
    }

    object CharacterDiffCallback : DiffUtil.ItemCallback<CharacterUiModel>() {
        override fun areItemsTheSame(
            oldItem: CharacterUiModel,
            newItem: CharacterUiModel
        ): Boolean {
            return oldItem.character.id == newItem.character.id
        }

        override fun areContentsTheSame(
            oldItem: CharacterUiModel,
            newItem: CharacterUiModel
        ): Boolean {
            return oldItem == newItem
        }
    }

}