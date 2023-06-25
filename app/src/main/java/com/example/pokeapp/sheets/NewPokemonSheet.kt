package com.example.pokeapp.sheets

import PokeApiService
import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.example.pokeapp.databinding.FragmentNewPokemonSheetBinding
import com.example.pokeapp.globalData
import com.example.pokeapp.models.Pokemon
import com.example.pokeapp.room.PokeModel
import com.example.pokeapp.viewModel.PokeViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class NewPokemonSheet : BottomSheetDialogFragment() {

    private  lateinit var binding: FragmentNewPokemonSheetBinding;
    private lateinit var pokeViewModel: PokeViewModel
    private lateinit var pokeApiService: PokeApiService

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pokeViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication())).get(PokeViewModel::class.java)

        if(globalData.mode == 0) {
            binding.title.setText("Nuevo pokemon")
            binding.btnDelete.isVisible = false
            binding.idPoke.isVisible = true
            binding.nameField.isEnabled = false
            binding.urlField.isEnabled = false
        } else if (globalData.mode == 1 && globalData.pokeModel != null) {
            binding.title.setText("Editar pokemon")
            binding.idPoke.isVisible = false
            binding.btnDelete.isVisible = true
            binding.nameField.setText(globalData.pokeModel?.name)
            binding.urlField.setText(globalData.pokeModel?.url)
            binding.descField.setText(globalData.pokeModel?.dec)
        }

        binding.btnSave.setOnClickListener { saveAction() }
        binding.btnDelete.setOnClickListener { deleteAction() }

        binding.idPoke.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                binding.nameField.setText("")
                binding.urlField.setText("")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if(s != null) {
                    val newText = s.toString()
                    val number: Int? = newText.toIntOrNull()
                    if(number != null){
                        getPokemonData(number)
                    }
                }
            }
        })

        val retrofit = Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        pokeApiService = retrofit.create(PokeApiService::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewPokemonSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun getPokemonData(pokemonId: Int) {
        val call = pokeApiService.getPokemonById(pokemonId)

        call.enqueue(object : Callback<Pokemon> {
            override fun onResponse(call: Call<Pokemon>, response: Response<Pokemon>) {
                if (response.isSuccessful) {
                    val pokemon = response.body()
                    // Aquí puedes hacer algo con los datos del Pokémon
                    if (pokemon != null) {
                        binding.nameField.setText(pokemon.name)
                        binding.urlField.setText(pokemon.sprites.other.officialArtwork.front_default)
                    }
                }
            }

            override fun onFailure(call: Call<Pokemon>, t: Throwable) {

            }
        })
    }


    private fun deleteAction() {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Desea eliminar este pokemon")
        builder.setMessage("Esta accion no podra ser revertida")
        builder.setCancelable(false)
        builder.setPositiveButton("Aceptar") { dialog, which ->
            if(globalData.pokeModel != null) {
                pokeViewModel.deleteSelectedPokemon(globalData.pokeModel!!)
                dismiss()
            }
        }
        builder.setNegativeButton("Cancelar") { dialog, which ->
            dialog.dismiss()
        }
        val dialog = builder.create()
        dialog.show()
    }

    private fun saveAction() {
        val name = binding.nameField.text.toString()
        val url = binding.urlField.text.toString()
        val desc = binding.descField.text.toString()


        if(globalData.mode == 0) {
            pokeViewModel.insertPokemon(PokeModel(0,name,url, desc))
        } else if (globalData.mode == 1 && globalData.pokeModel != null){
            pokeViewModel.updatePokemon(PokeModel(globalData.pokeModel!!.id,name,url, desc))
        }

        binding.nameField.setText("")
        binding.urlField.setText("")
        binding.descField.setText("")
        dismiss()
    }

}