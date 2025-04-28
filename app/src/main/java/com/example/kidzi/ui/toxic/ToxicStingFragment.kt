package com.example.kidzi.ui.toxic

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.kidzi.R
import com.example.kidzi.databinding.FragmentToxicStingBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ToxicStingFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentToxicStingBinding.inflate(inflater, container, false)

        binding.btnBack.setOnClickListener { findNavController().popBackStack() }
        binding.btnBee.setOnClickListener { findNavController().navigate(ToxicStingFragmentDirections.actionToxicStingFragmentToToxicBeeFragment()) }
        binding.btnSnake.setOnClickListener { findNavController().navigate(ToxicStingFragmentDirections.actionToxicStingFragmentToToxicSnakeFragment()) }


        return binding.root
    }

}