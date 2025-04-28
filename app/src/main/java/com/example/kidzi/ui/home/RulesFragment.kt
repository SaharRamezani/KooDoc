package com.example.kidzi.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.kidzi.R
import com.example.kidzi.databinding.FragmentRulesBinding

// TODO: Rename parameter arguments, choose names that match
class RulesFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentRulesBinding.inflate(inflater)

        binding.btnBack.setOnClickListener { findNavController().popBackStack() }
        binding.btnOk.setOnClickListener { findNavController().popBackStack() }

        return binding.root
    }
}