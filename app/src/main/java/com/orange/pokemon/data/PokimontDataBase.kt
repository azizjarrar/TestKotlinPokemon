package com.orange.pokemon.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [PokemonEntyty::class],
    version = 1
)

abstract class PokimontDataBase : RoomDatabase() {

    abstract fun getPokimonDao(): PokimonDao

    companion object {

        private var instance: PokimontDataBase? = null

        fun getInstance(context: Context): PokimontDataBase {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context,
                    PokimontDataBase::class.java,
                    "pok_db"
                ).build()
            }
            return instance!!
        }
    }


}