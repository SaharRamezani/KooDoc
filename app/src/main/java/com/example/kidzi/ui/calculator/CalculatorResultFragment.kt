package com.example.kidzi.ui.calculator

import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.kidzi.R
import com.example.kidzi.databinding.FragmentCalculatorResultBinding

class CalculatorResultFragment : DialogFragment() {
    private var _binding: FragmentCalculatorResultBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.DialogBlurTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentCalculatorResultBinding.inflate(inflater, container, false)
        .also { _binding = it }
        .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1) Read your args:
        val drug  = arguments?.getString("drug" ) ?: ""
        val weight= arguments?.getFloat ("weight") ?: 0f
        val age   = arguments?.getInt   ("age"   ) ?: 0

        // 2) Bind the static fields:
        binding.tvDrugName    .text = "Drug: $drug"
        binding.tvCalculatedDose.text = "Dose for ${"%.1f".format(weight)}kg"
        binding.tvAgeNote    .text = if (age < 2) "Pediatric only." else "Standard OK."

        // 3) Now _dynamically_ add one or more text‐boxes:
        val container = binding.root.findViewById<LinearLayout>(R.id.dynamic_container)

        // Example: if weight is > 20kg, show an extra note
        if (weight > 20) {
            val extraNote = TextView(requireContext()).apply {
                text = "⚠️ Note: Above 20kg adjust per adult guidelines."
                setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
                setPadding(0, dpToPx(12), 0, dpToPx(12))
            }
            container.addView(extraNote)
        }

        // Example: a user-input edit‐text for comments
        val commentField = EditText(requireContext()).apply {
            hint = "Any additional comments?"
            layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            ).also {
                it.topMargin = dpToPx(16)
            }
        }
        container.addView(commentField)

        // back‐button
        binding.btnBack.setOnClickListener { dismiss() }
    }

    private fun dpToPx(dp: Int): Int =
        (dp * resources.displayMetrics.density).toInt()

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}