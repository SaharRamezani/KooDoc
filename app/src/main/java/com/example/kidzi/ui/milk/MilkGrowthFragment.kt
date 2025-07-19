package com.example.kidzi.ui.milk

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.kidzi.databinding.FragmentMilGrowthBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MilkGrowthFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentMilGrowthBinding.inflate(inflater, container, false)

        binding.btnBack.setOnClickListener {findNavController().popBackStack()}
        binding.btnMilk.setOnClickListener { findNavController().navigate(MilkGrowthFragmentDirections.actionMilGrowthFragmentToMilkIntroFragment()) }
        binding.btnGrowth.setOnClickListener { findNavController().navigate(MilkGrowthFragmentDirections.actionMilGrowthFragmentToGrowthMainFragment()) }


        return binding.root
    }

}