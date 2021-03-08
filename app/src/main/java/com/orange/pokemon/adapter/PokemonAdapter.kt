package com.orange.pokemon.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.orange.pokemon.R
import com.orange.pokemon.data.PokemonEntyty
import com.orange.pokemon.databinding.PokemonItemBinding


class PokemonAdapter :
            ListAdapter<PokemonEntyty, PokemonAdapter.PokemonViewholder>(PokemonDiffUtils()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewholder {
        val binding = PokemonItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PokemonViewholder(binding)
    }

    override fun onBindViewHolder(holder: PokemonViewholder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class PokemonViewholder(val binding: PokemonItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(PokemonEntity: PokemonEntyty) {
            with(binding) {
                tvName.text = PokemonEntity.name
                tvCategory.text = PokemonEntity.category
                tvHeight.text = PokemonEntity.height
            }
            Glide.with(binding.rowImg)
                .load(PokemonEntity.imageurl)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_android_black_24dp)
                .circleCrop()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(binding.rowImg)
        }
    }

}




class PokemonDiffUtils : DiffUtil.ItemCallback<PokemonEntyty>() {

    override fun areItemsTheSame(
        oldItem: PokemonEntyty,
        newItem: PokemonEntyty
    ) = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: PokemonEntyty,
        newItem: PokemonEntyty
    ) = oldItem == newItem
}
