package com.example.kidzi.ui.toxic

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kidzi.R
import com.example.kidzi.databinding.FragmentToxicDrugBinding
import com.example.kidzi.ui.vaccine.adapters.VaccineAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ToxicDrugFragment : Fragment() {
    private lateinit var adapter: VaccineAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentToxicDrugBinding.inflate(inflater)
        val vaccineArray = resources.getStringArray(R.array.toxic_drugs).toList()

        adapter = VaccineAdapter(
            vaccineArray,
            onItemClick = { position ->
                val action = ToxicDrugFragmentDirections.actionToxicDrugFragmentToToxicNoMedDetailFragment(position, vaccineArray[position])
                findNavController().navigate(action)
            },
            useArrow = true
        )

        binding.btnBack.setOnClickListener {findNavController().popBackStack()}
        binding.recycler.layoutManager = LinearLayoutManager(requireContext())
        binding.recycler.adapter = adapter

        return binding.root
    }
}