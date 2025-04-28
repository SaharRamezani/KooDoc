package com.example.kidzi.ui.vaccine

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kidzi.R
import com.example.kidzi.databinding.FragmentVaccineBinding
import com.example.kidzi.ui.vaccine.adapters.VaccineAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VaccineFragment : Fragment() {

    private var _binding: FragmentVaccineBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var adapter: VaccineAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVaccineBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val pos = VaccineFragmentArgs.fromBundle(requireArguments()).row

        val vaccineTimeArray  = resources.getStringArray(R.array.vaccine_time_names)

        if(pos < vaccineTimeArray.size){
            val vaccineTimesList = vaccineTimeArray[pos].split("،").map { it.trim() }
            adapter = VaccineAdapter(vaccineTimesList) { position ->
            }


            binding.recycler.layoutManager = LinearLayoutManager(requireContext())
            binding.recycler.adapter = adapter
        }

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}