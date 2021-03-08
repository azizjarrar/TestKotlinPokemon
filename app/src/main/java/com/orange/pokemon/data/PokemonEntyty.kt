package com.orange.pokemon.data


import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "pokemon_table")
//each pokemon data
data class PokemonEntyty(
    @PrimaryKey(autoGenerate = true)
    var id:Int = 0,
    val imageurl: String,
    val name: String,
    val category: String,
    val height: String
)