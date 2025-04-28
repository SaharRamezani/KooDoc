package com.example.kidzi.ui.toxic

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.kidzi.R
import com.example.kidzi.databinding.FragmentToxicNoMedBinding

class ToxicNoMedFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentToxicNoMedBinding.inflate(inflater)
        binding.btnBack.setOnClickListener { findNavController().popBackStack() }

        binding.btnDrug.setOnClickListener { findNavController().navigate(ToxicNoMedFragmentDirections.actionToxicNoMedFragmentToToxicDrugFragment()) }
        binding.btnPlants.setOnClickListener { findNavController().navigate(ToxicNoMedFragmentDirections.actionToxicNoMedFragmentToToxicPlantsFragment()) }
        binding.btnSnake.setOnClickListener { findNavController().navigate(ToxicNoMedFragmentDirections.actionToxicNoMedFragmentToToxicStingFragment()) }

        return binding.root
    }

}