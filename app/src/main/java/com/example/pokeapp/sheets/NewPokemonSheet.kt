package com.example.pokeapp.sheets

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.example.pokeapp.databinding.FragmentNewPokemonSheetBinding
import com.example.pokeapp.globalData
import com.example.pokeapp.room.PokeModel
import com.example.pokeapp.viewModel.PokeViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment



class NewPokemonSheet : BottomSheetDialogFragment() {

    private  lateinit var binding: FragmentNewPokemonSheetBinding;
    private lateinit var pokeViewModel: PokeViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pokeViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication())).get(PokeViewModel::class.java)

        if(globalData.mode == 0) {
            binding.title.setText("Nuevo pokemon")
            binding.btnDelete.isVisible = false
        } else if (globalData.mode == 1 && globalData.pokeModel != null) {
            binding.title.setText("Editar pokemon")

            binding.btnDelete.isVisible = true
            binding.nameField.setText(globalData.pokeModel?.name)
            binding.urlField.setText(globalData.pokeModel?.url)
            binding.descField.setText(globalData.pokeModel?.dec)
        }

        binding.btnSave.setOnClickListener { saveAction() }
        binding.btnDelete.setOnClickListener { deleteAction() }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewPokemonSheetBinding.inflate(inflater, container, false)
        return binding.root
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