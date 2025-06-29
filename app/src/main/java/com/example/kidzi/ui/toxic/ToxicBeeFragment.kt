package com.example.kidzi.ui.toxic

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.kidzi.databinding.FragmentToxicBeeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ToxicBeeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentToxicBeeBinding.inflate(inflater)

        binding.btnBack.setOnClickListener { findNavController().popBackStack() }

        return binding.root
    }

}