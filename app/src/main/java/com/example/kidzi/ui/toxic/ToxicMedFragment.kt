package com.example.kidzi.ui.toxic

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kidzi.R
import com.example.kidzi.databinding.FragmentToxicMedBinding
import com.example.kidzi.ui.vaccine.adapters.VaccineAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ToxicMedFragment : Fragment() {

    private lateinit var adapter: VaccineAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentToxicMedBinding.inflate(inflater)

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        val vaccineArray = resources.getStringArray(R.array.toxic_med_names).toList()

        adapter = VaccineAdapter(
            vaccineList = vaccineArray,
            onItemClick = { position ->
                if (position == 0) {
                    findNavController().navigate(ToxicMedFragmentDirections.actionToxicMedFragmentToToxicIronFragment())
                } else {
                    findNavController().navigate(ToxicMedFragmentDirections.actionToxicMedFragmentToToxicMedDetailFragment(position, vaccineArray[position]))
                }
            },
            useArrow = true
        )

        binding.recycler.layoutManager = LinearLayoutManager(requireContext())
        binding.recycler.adapter = adapter

        return binding.root
    }
}