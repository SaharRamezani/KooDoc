package com.example.kidzi.ui.toxic

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.kidzi.databinding.FragmentToxicMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ToxicMainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentToxicMainBinding.inflate(inflater)

        binding.btnBack.setOnClickListener { findNavController().popBackStack() }
        binding.btnMed.setOnClickListener { findNavController().navigate(ToxicMainFragmentDirections.actionToxicMainFragmentToToxicMedFragment()) }
        binding.btnNoMed.setOnClickListener { findNavController().navigate(ToxicMainFragmentDirections.actionToxicMainFragmentToToxicNoMedFragment()) }

        return binding.root
    }

}