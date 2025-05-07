package com.example.kidzi.ui.calculator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.kidzi.databinding.FragmentCalculatorResultBinding

class CalculatorResultFragment : Fragment() {
    private var _binding: FragmentCalculatorResultBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentCalculatorResultBinding.inflate(inflater, container, false).also {
        _binding = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Retrieve data from arguments Bundle
        val drug = arguments?.getString("drug") ?: ""
        val weight = arguments?.getFloat("weight") ?: 0f
        val age = arguments?.getInt("age") ?: 0

        // Example logic — tweak as needed!
        val dosage = when {
            weight < 10 -> "5 mg/kg"
            weight < 20 -> "7.5 mg/kg"
            else -> "10 mg/kg"
        }
        val note = if (age < 2) {
            "Use pediatric formulation only."
        } else {
            "Standard formulation OK."
        }

        // Bind to UI
        binding.tvDrugName.text = "Drug: $drug"
        binding.tvCalculatedDose.text = "Dose: $dosage for ${"%.1f".format(weight)} kg"
        binding.tvAgeNote.text = note

        // Back button
        binding.btnBack.setOnClickListener { requireActivity().onBackPressed() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
