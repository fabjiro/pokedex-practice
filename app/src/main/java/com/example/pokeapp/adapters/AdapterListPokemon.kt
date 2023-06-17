package com.example.pokeapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.pokeapp.MainActivity
import com.example.pokeapp.PokeActions
import com.example.pokeapp.databinding.CardpokemonBinding
import com.example.pokeapp.globalData
import com.example.pokeapp.room.PokeModel
import com.example.pokeapp.viewModel.PokeViewModel
import com.squareup.picasso.Picasso

class AdapterListPokemon (
    private val pokeItems: List<PokeModel>,
    private val pokeActions: PokeActions,
        ) : RecyclerView.Adapter<AdapterListPokemon.PokeHolder>() {

    inner class PokeHolder(val binding: CardpokemonBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokeHolder {
        val binding = CardpokemonBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return PokeHolder(binding)
    }

    override fun onBindViewHolder(holder: AdapterListPokemon.PokeHolder, position: Int) {
        val item = pokeItems.get(position)
        holder.binding.nombre.setText(item.name)
        Picasso.get().load(item.url).into(holder.binding.imageView);

        holder.binding.card.setOnClickListener{
           pokeActions.onClickCard(pokeItems[position]);
        }
    }

    override fun getItemCount(): Int = pokeItems.size
}