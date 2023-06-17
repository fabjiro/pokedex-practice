package com.example.pokeapp.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemons")
data class PokeModel(
    @PrimaryKey(autoGenerate = true)  @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "name")  val name: String,
    @ColumnInfo(name = "url")  val url: String,
    @ColumnInfo(name = "description")  val dec: String,
)