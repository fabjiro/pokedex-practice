package com.example.pokeapp.room

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface pokeDao {
    @Query("SELECT * from pokemons order by id")
    fun getAllPokemons(): Flow<List<PokeModel>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun savePokemon(pokeModel: PokeModel)

    @Update
    suspend fun updatePokemon(pokeModel: PokeModel)

    @Delete
    suspend fun deletePokemon(pokeModel: PokeModel)
}