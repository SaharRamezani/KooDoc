package com.example.kidzi.ui.calculator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.kidzi.R
import com.example.kidzi.databinding.FragmentCalculatorBinding

class CalculatorFragment : Fragment() {
    private var _binding: FragmentCalculatorBinding? = null
    private val binding get() = _binding!!

    // Valid input ranges
    private val MIN_WEIGHT = 2.0
    private val MAX_WEIGHT = 50.0
    private val MIN_AGE = 1
    private val MAX_AGE = 12

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

        // 2) Attach adapter
        binding.autoCompleteDrug.setAdapter(adapter)
        binding.autoCompleteDrug.threshold = 1

        // 3) Calculate button logic with manual Bundle navigation
        binding.btnCalculate.setOnClickListener {
            val drugStr  = binding.autoCompleteDrug.text.toString()
            var weightF  = binding.inputWeight.text.toString().toFloatOrNull()
            var ageInt   = binding.inputAge.text.toString().toIntOrNull()

            when {
                drugStr.isBlank() ->
                    binding.tvWarning.text = getString(R.string.warning_select_drug)

                weightF == null && ageInt == null ->
                    binding.tvWarning.text = getString(R.string.warning_enter_weight_age)

                else -> {
                    // Build a Bundle and navigate to ResultFragment
                    weightF  = binding.inputWeight.text.toString().toFloat()
                    ageInt   = binding.inputAge.text.toString().toInt()

                    val bundle = Bundle().apply {
                        putString("drug", drugStr)
                        putFloat("weight", weightF)
                        putInt("age", ageInt)
                    }
                    findNavController().navigate(
                        R.id.action_calculatorFragment_to_calculatorResultFragment,
                        bundle
                    )
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}