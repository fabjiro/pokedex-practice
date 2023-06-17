package com.example.pokeapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.example.pokeapp.room.PokeModel
import com.example.pokeapp.room.pokeDao

class PokeRepository constructor(
    private val pokeDao: pokeDao
)
{
    fun GetAllPokemons(): LiveData<List<PokeModel>> = pokeDao.getAllPokemons().asLiveData()

    suspend fun savePokemon(pokemon: PokeModel){
        pokeDao.savePokemon(pokemon)
    }

    suspend fun deletePokemon(pokemon: PokeModel) {
        pokeDao.deletePokemon(pokemon)
    }

    suspend fun updatePokemon(pokemon: PokeModel){
        pokeDao.updatePokemon(pokemon)
    }
}