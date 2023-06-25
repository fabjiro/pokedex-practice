package com.example.pokeapp.models

import com.google.gson.annotations.SerializedName

data class Pokemon(
    val name: String,
    val sprites: Sprites
)

data class Sprites (
    val front_default: String,
    val other: Other
)

data class Other (
    @SerializedName("official-artwork")
    val officialArtwork: OfficialArtwork
)

data class OfficialArtwork (
    val front_default: String
)