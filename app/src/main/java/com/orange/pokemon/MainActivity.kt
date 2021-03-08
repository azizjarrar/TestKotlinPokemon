package com.orange.pokemon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.orange.pokemon.adapter.PokiAdapter
import com.orange.pokemon.data.PokemonEntyty
import com.orange.pokemon.data.PokimonDao
import com.orange.pokemon.data.PokimontDataBase
import com.orange.pokemon.databinding.ActivityMainBinding
import com.orange.pokemon.model.Pokemon
import com.orange.pokemon.networking.ApiService
import com.orange.pokemon.networking.NetworkClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var pokiAdapter: PokiAdapter
    private lateinit var database: PokimontDataBase
    private lateinit var dao: PokimonDao
    private lateinit var PokimonsList: List<Pokemon>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val service = NetworkClient().getRetrofit().create(ApiService::class.java)
        val pokemonAdapter = PokiAdapter()

        //My code
        val recyclerView = findViewById<RecyclerView>(R.id.pokemon_recycler)
        recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
        recyclerView.adapter = pokemonAdapter


        database = PokimontDataBase.getInstance(this)
        dao = database.getPokimonDao()


        service.getAllPokemons().enqueue(object : Callback<List<Pokemon>> {
            override fun onResponse(call: Call<List<Pokemon>>, response: Response<List<Pokemon>>) {
                if (response.isSuccessful) {
                    PokimonsList = response.body()!!
                    GlobalScope.launch {
                        Dispatchers.Main
                        dao.insertAll(PokimonsList.map {
                            PokemonEntyty(
                                name = it.name,
                                height = it.height,
                                category = it.category,
                                imageurl = it.imageurl,
                            )
                        })

                    }

                    getDataFromDB(pokemonAdapter)
                }
            }

            override fun onFailure(call: Call<List<Pokemon>>, t: Throwable) {
                getDataFromDB(pokemonAdapter)
                Log.e(TAG, "onFailure: ", t)
                Toast.makeText(this@MainActivity, "Error", Toast.LENGTH_LONG).show()
            }

        })
    }

    private fun getDataFromDB(adapter: PokiAdapter) {
        GlobalScope.launch {
            //var list: List<PokemonEntyty>? = null
            val list = dao.getALLPokimon()
            runOnUiThread(
                object : Runnable {
                    override fun run() {
                            adapter.submitList(list)
                    }
                }
            )
        }
    }


}