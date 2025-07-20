package com.example.kidzi.ui.milk

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.kidzi.R
import com.example.kidzi.databinding.FragmentMilkGrowthBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MilkGrowthFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentMilkGrowthBinding.inflate(inflater, container, false)

        binding.btnBack.setOnClickListener { findNavController().navigate(MilkGrowthFragmentDirections.actionMilkGrowthFragmentToMainFragment()) }
        binding.btnMilk.setOnClickListener { findNavController().navigate(MilkGrowthFragmentDirections.actionMilkGrowthFragmentToMilkIntroFragment()) }
        binding.btnGrowth.setOnClickListener { findNavController().navigate(MilkGrowthFragmentDirections.actionMilkGrowthFragmentToGrowthMainFragment()) }

        return binding.root
    }

}