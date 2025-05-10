package com.example.kidzi.ui.calculator

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.example.kidzi.R

class CalculatorResultFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val drug   = arguments?.getString("drug").orEmpty()
        val weight = arguments?.getFloat("weight") ?: 0f
        val age    = arguments?.getInt("age")   ?: 0
        val age_unit    = arguments?.getString("age_unit").orEmpty()

        // 1) calculate the proper dose text
        val resultText = calculateDose(drug, weight, age)

        return MaterialAlertDialogBuilder(requireContext(), R.style.DialogBlurTheme)
            .setTitle(getString(R.string.calculator_result_title))
            .setMessage(resultText)
            .setNeutralButton("بستن") { _, _ -> dismiss() }
            .create()
    }

    private fun calculateDose(drug: String, weight: Float, age: Int): String {
        if (drug == getString(R.string.drug_ibuprofen_syrup)) {
            if (weight in 5.4f..8.1f) {
                return getString(R.string.dose_2_5cc)
            }
            else if (age in 6..12) {
                return getString(R.string.dose_2_5cc)
            }
        }

        // TODO: add other rules from the Excel here…
        return "اطلاعات ناکافی برای محاسبه دوز."
    }
}