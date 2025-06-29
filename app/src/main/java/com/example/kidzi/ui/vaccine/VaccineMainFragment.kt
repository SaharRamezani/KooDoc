package com.example.kidzi.ui.vaccine

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.kidzi.databinding.FragmentVaccineMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class VaccineMainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentVaccineMainBinding.inflate(inflater, container, false)

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnAge.setOnClickListener { findNavController().navigate(VaccineMainFragmentDirections.actionVaccineMainFragmentToVaccineAgeFragment()) }
        binding.btnSpecial.setOnClickListener { findNavController().navigate(VaccineMainFragmentDirections.actionVaccineMainFragmentToVaccineSpecialFragment()) }
        binding.btnAdvice.setOnClickListener { findNavController().navigate(VaccineMainFragmentDirections.actionVaccineMainFragmentToVaccineAdviceFragment()) }
        binding.btnWrong.setOnClickListener { findNavController().navigate(VaccineMainFragmentDirections.actionVaccineMainFragmentToVaccineWrongFragment()) }
        binding.btnAbout.setOnClickListener { findNavController().navigate(VaccineMainFragmentDirections.actionVaccineMainFragmentToVaccineAboutFragment()) }

        return binding.root
    }
}