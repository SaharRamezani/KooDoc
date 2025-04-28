package com.example.kidzi.ui.vaccine

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kidzi.R
import com.example.kidzi.databinding.FragmentVaccineAgeBinding
import com.example.kidzi.ui.vaccine.adapters.VaccineAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VaccineAgeFragment : Fragment() {

    private lateinit var adapter: VaccineAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentVaccineAgeBinding.inflate(inflater, container, false)

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        val vaccineArray = resources.getStringArray(R.array.vaccine_use_time).toList()

        adapter = VaccineAdapter(vaccineArray) { position ->
            val action = VaccineAgeFragmentDirections.actionVaccineAgeFragmentToVaccineFragment(position)
            findNavController().navigate(action)
        }

        binding.recycler.layoutManager = LinearLayoutManager(requireContext())
        binding.recycler.adapter = adapter

        return binding.root
    }
}