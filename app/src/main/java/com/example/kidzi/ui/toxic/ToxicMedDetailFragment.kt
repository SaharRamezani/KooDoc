package com.example.kidzi.ui.toxic

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.kidzi.R
import com.example.kidzi.databinding.FragmentToxicMedDetailBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ToxicMedDetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentToxicMedDetailBinding.inflate(inflater, container, false)
        binding.btnBack.setOnClickListener { findNavController().popBackStack() }

        val name = ToxicMedDetailFragmentArgs.fromBundle(requireArguments()).name
        val row = ToxicMedDetailFragmentArgs.fromBundle(requireArguments()).row

        binding.toxName.text = name
        binding.toxDetail.text = resources.getStringArray(R.array.toxic_med_detail)[row]

        return binding.root
    }
}