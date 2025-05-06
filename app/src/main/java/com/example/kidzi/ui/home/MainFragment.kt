package com.example.kidzi.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.kidzi.R
import com.example.kidzi.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentMainBinding.inflate(inflater, container, false)

        binding.btnAccount.setOnClickListener { findNavController().navigate(MainFragmentDirections.actionMainFragmentToAccountFragment()) }
        binding.btnCalculator.setOnClickListener {
            findNavController().navigate(
                MainFragmentDirections.actionMainFragmentToCalculatorFragment()
            )
        }
        binding.btnVaccine.setOnClickListener { findNavController().navigate(MainFragmentDirections.actionMainFragmentToVaccineMainFragment()) }
        binding.btnAlert.setOnClickListener { findNavController().navigate(MainFragmentDirections.actionMainFragmentToToxicMainFragment()) }
        binding.btnSymptoms.setOnClickListener { findNavController().navigate(MainFragmentDirections.actionMainFragmentToSymptomsFragment()) }
        binding.btnFood.setOnClickListener { findNavController().navigate(MainFragmentDirections.actionMainFragmentToMilGrowthFragment()) }

        return binding.root
    }
}