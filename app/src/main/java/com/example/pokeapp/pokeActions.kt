package com.example.pokeapp

import com.example.pokeapp.room.PokeModel

interface PokeActions
{
    fun onClickCard(pokeModel: PokeModel)
}