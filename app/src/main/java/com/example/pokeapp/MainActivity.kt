package com.example.pokeapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pokeapp.adapters.AdapterListPokemon
import com.example.pokeapp.databinding.ActivityMainBinding
import com.example.pokeapp.room.PokeModel
import com.example.pokeapp.sheets.NewPokemonSheet
import com.example.pokeapp.viewModel.PokeViewModel


class MainActivity : AppCompatActivity(), PokeActions {

    private lateinit var binding: ActivityMainBinding
    private lateinit var pokeViewModel: PokeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        pokeViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(PokeViewModel::class.java)

        binding.btnNewPokemon.setOnClickListener{
            globalData.mode = 0
            NewPokemonSheet().show(supportFragmentManager, "new pokemon tag")
        }

        setRecyclerView()
    }


    private fun setRecyclerView()
    {
        val main = this
        pokeViewModel.listPokemons.observe(this){
            binding.recyClerListPokemons.apply {
                layoutManager = GridLayoutManager(applicationContext, 2)
                adapter = AdapterListPokemon(it, main)

            }
        }
    }

    override fun onClickCard(pokeModel: PokeModel) {
        globalData.pokeModel = pokeModel;
        globalData.mode = 1
        NewPokemonSheet().show(supportFragmentManager, "edit pokemon tag")
    }
}