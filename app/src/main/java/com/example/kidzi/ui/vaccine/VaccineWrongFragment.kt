package com.example.kidzi.ui.vaccine

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kidzi.R
import com.example.kidzi.databinding.FragmentVaccineWrongBinding
import com.example.kidzi.ui.vaccine.adapters.VaccineWrongAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class VaccineWrongFragment : Fragment() {

    lateinit var adapter : VaccineWrongAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentVaccineWrongBinding.inflate(inflater, container, false)

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        val vaccineList = resources.getStringArray(R.array.vaccine_wrong).toList()

        adapter = VaccineWrongAdapter(vaccineList)
        binding.recycler.layoutManager = LinearLayoutManager(requireContext())
        binding.recycler.adapter = adapter

        return binding.root
    }
}