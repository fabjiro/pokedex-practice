package com.example.pokeapp.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.pokeapp.repository.PokeRepository
import com.example.pokeapp.room.PokeModel
import com.example.pokeapp.room.RoomDabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PokeViewModel (application: Application) : AndroidViewModel(application) {

    val listPokemons  : LiveData<List<PokeModel>>
    val repository : PokeRepository

    init {
        val pokeDao = RoomDabase.getDatabase(application).pokeDao()
        repository = PokeRepository(pokeDao)
        listPokemons = repository.GetAllPokemons()
    }
    fun insertPokemon(poke: PokeModel) =
        viewModelScope.launch(Dispatchers.IO) { repository.savePokemon(poke) }

    fun deleteSelectedPokemon(poke: PokeModel) = viewModelScope.launch (Dispatchers.IO) {
        repository.deletePokemon(poke)
    }

    fun updatePokemon(poke: PokeModel) = viewModelScope.launch(Dispatchers.IO) { repository.updatePokemon(poke) }
}