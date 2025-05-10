package com.example.kidzi.ui.calculator

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.kidzi.R
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import com.example.kidzi.databinding.FragmentCalculatorBinding
import com.example.kidzi.di.db.PreferenceManager

@AndroidEntryPoint
class CalculatorFragment : Fragment() {
    @Inject
    lateinit var prefs: PreferenceManager
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

        // 2) set up AGE-UNIT dropdown
        val units = resources.getStringArray(R.array.age_units)
        val unitAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            units
        )
        binding.autoCompleteAgeUnit.setAdapter(unitAdapter)
        binding.autoCompleteAgeUnit.threshold = 1

        // restore last (or default = “Month”)
        binding.autoCompleteAgeUnit.setText(prefs.getLastAgeUnit(), false)

        // save on pick
        binding.autoCompleteAgeUnit.setOnItemClickListener { _, _, pos, _ ->
            prefs.updateLastAgeUnit(binding.autoCompleteAgeUnit.adapter.getItem(pos).toString())
        }

        // Back button
        binding.btnBack.setOnClickListener {
            findNavController().navigate(
                CalculatorFragmentDirections.actionCalculatorFragmentToMainFragment()
            )
        }

        // Collapsible "Read before use" section
        binding.cardReadBeforeUseContent.visibility = View.GONE
        binding.ivReadBeforeUseArrow.rotation = 0f

        binding.cardReadBeforeUse.setOnClickListener {
            val isOpen = binding.cardReadBeforeUseContent.visibility == View.VISIBLE
            binding.cardReadBeforeUseContent.visibility =
                if (isOpen) View.GONE else View.VISIBLE

            binding.ivReadBeforeUseArrow.animate()
                .rotation(if (isOpen) 0f else 180f)
                .setDuration(200)
                .start()
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

        // 3) Calculate button logic
        binding.btnCalculate.setOnClickListener {
            val drugStr  = binding.autoCompleteDrug.text.toString()
            var weightF  = binding.inputWeight.text.toString().toFloatOrNull()
            var ageInt   = binding.inputAge.text.toString().toIntOrNull()
            val unitStr = binding.autoCompleteAgeUnit.text.toString()

            if (drugStr.isBlank()) {
                Toast.makeText(requireContext(),
                    getString(R.string.choose_drug_first), Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (weightF == null && ageInt == null) {
                Toast.makeText(requireContext(),
                    getString(R.string.age_weight_must_enter), Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val bundle = Bundle().apply {
                putString("drug", drugStr)
                weightF?.let { putFloat("weight", it) }
                ageInt ?.let { putInt  ("age",   it) }
                putString("age_unit", unitStr)
            }

            CalculatorResultFragment()
                .apply { arguments = bundle }
                .show(parentFragmentManager, "CalculatorResultDialog")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
