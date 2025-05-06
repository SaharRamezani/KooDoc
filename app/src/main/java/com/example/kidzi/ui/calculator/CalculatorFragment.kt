package com.example.kidzi.ui.calculator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.kidzi.R
import com.example.kidzi.databinding.FragmentCalculatorBinding

class CalculatorFragment : Fragment() {
    private var _binding: FragmentCalculatorBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCalculatorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Back navigation
        binding.btnBack.setOnClickListener {
            findNavController().navigate(
                CalculatorFragmentDirections
                    .actionCalculatorFragmentToMainFragment()
            )
        }

        // 1) Prepare dropdown data
        val drugNames = resources.getStringArray(R.array.drug_names)
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            drugNames
        )

        // 2) Attach adapter to AutoCompleteTextView
        binding.autoCompleteDrug.setAdapter(adapter)
        binding.autoCompleteDrug.threshold = 1

        // 3) Handle selection
        binding.autoCompleteDrug.setOnItemClickListener { parent, _, position, _ ->
            val selectedDrug = parent.getItemAtPosition(position) as String
            Toast.makeText(
                requireContext(),
                "Selected drug: $selectedDrug",
                Toast.LENGTH_SHORT
            ).show()

            // TODO: perform any action based on selectedDrug
        }

        // 4) Calculate button logic
        binding.btnCalculate.setOnClickListener {
            val weight = binding.inputWeight.text.toString().toDoubleOrNull()
            val age = binding.inputAge.text.toString().toIntOrNull()
            val drug = binding.autoCompleteDrug.text.toString()

            if (drug.isBlank() || weight == null || age == null) {
                binding.tvWarning.text = getString(R.string.warning_enter_weight_instead)
            } else {
                // TODO: implement your dosage calculation here
                // e.g.: val dose = calculateDose(drug, weight, age)
                //      show result in a dialog or new screen
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
