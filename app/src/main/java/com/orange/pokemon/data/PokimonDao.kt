package com.orange.pokemon.data
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query

@Dao
interface PokimonDao {


    @Insert
    suspend fun InsertONe(obj:PokemonEntyty)
    // get all pokemon
    @Query("SELECT * FROM pokemon_table")
    suspend fun getALLPokimon() : List<PokemonEntyty>
    @Insert
    suspend fun insertAll(entities: List<PokemonEntyty>)
}
