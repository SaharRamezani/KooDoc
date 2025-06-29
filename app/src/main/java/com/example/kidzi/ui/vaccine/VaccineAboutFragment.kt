package com.example.kidzi.ui.vaccine

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kidzi.R
import com.example.kidzi.databinding.FragmentVaccineAboutBinding
import com.example.kidzi.ui.vaccine.adapters.VaccineInfoAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VaccineAboutFragment : Fragment() {

    lateinit var adapter: VaccineInfoAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentVaccineAboutBinding.inflate(inflater, container, false)

        binding.btnBack.setOnClickListener { findNavController().popBackStack() }
        val vaccineNames = resources.getStringArray(R.array.vaccine_names)
        val vaccineAbout = resources.getStringArray(R.array.vaccine_info)

        val vaccineList = mutableListOf<VaccineAboutModel>()
        val size = minOf(vaccineNames.size, vaccineAbout.size)

        for(i in 0 until size){
            vaccineList.add(VaccineAboutModel(vaccineNames[i], vaccineAbout[i], false))
        }

        adapter = VaccineInfoAdapter(vaccineList, requireContext())
        binding.recycler.layoutManager = LinearLayoutManager(requireContext())
        binding.recycler.adapter = adapter

        return binding.root
    }
}