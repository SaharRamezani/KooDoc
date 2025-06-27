package com.example.kidzi.ui.calculator

import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.example.kidzi.R

class CalculatorResultFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val drug = arguments?.getString("drug").orEmpty()
        val weight = arguments?.get("weight") as? Float ?: 0f
        val age = arguments?.get("age") as? Int ?: 0
        val age_unit = arguments?.getString("age_unit").orEmpty()

        // Show a toast and dismiss if both are null
        if (weight == 0f && age == 0) {
            Toast.makeText(requireContext(), getString(R.string.age_weight_must_enter), Toast.LENGTH_LONG).show()
            dismiss()
            return Dialog(requireContext())
        }

        var ageInWeeks: Int = 0
        if (age > 0) {
            ageInWeeks = when (age_unit) {
                getString(R.string.Month) -> age * 4
                getString(R.string.Year) -> age * 52
                getString(R.string.Week) -> age
                else -> 0
            }
        }

        // 1) calculate the proper dose text
        val resultText = calculateDose(drug, weight, ageInWeeks)

        return MaterialAlertDialogBuilder(requireContext(), R.style.DialogBlurTheme)
            .setTitle(getString(R.string.calculator_result_title))
            .setMessage(resultText)
            .setNeutralButton(getString(R.string.close)) { _, _ -> dismiss() }
            .create()
    }

    private fun calculateDose(drug: String, weight: Float, ageInWeeks: Int): String {
        var resultText = ""

        if (drug == getString(R.string.drug_ibuprofen_syrup)) {
            if (ageInWeeks > 0 && ageInWeeks  < 6 * 4)
            {
                resultText = getString(R.string.age_below_6mon_ibuprofen_syrup)
            }
            if (weight > 0 && weight < 5.4f)
            {
                resultText = getString(R.string.weight_below_5_ibuprofen_syrup)
            }
            else if (weight in 5.4f..8.1f) {
                resultText = getString(R.string.dose_2_5cc)
            }
            else if (weight in 8.2f..10.8f) {
                resultText = getString(R.string.dose_3_5_4cc)
            }
            else if (weight in 10.9f..16.3f) {
                resultText = getString(R.string.dose_5cc)
            }
            else if (weight in 16.4f..21.7f) {
                resultText = getString(R.string.dose_7_5cc)
            }
            else if (weight in 21.8f..27.2f) {
                resultText = getString(R.string.dose_10cc)
            }
            else if (weight in 27.3f..32.6f) {
                resultText = getString(R.string.dose_10_12_5cc)
            }
            else if (weight >= 32.7f) {
                resultText = getString(R.string.dose_15cc)
            }
            else if (ageInWeeks in 6 * 4..12 * 4) {
                resultText = getString(R.string.dose_2_5cc)
            }
            else if (ageInWeeks in 12 * 4..< 2 * 52) {
                resultText = getString(R.string.dose_3_5_4cc)
            }
            else if (ageInWeeks in 2 * 52..< 4 * 52) {
                resultText = getString(R.string.dose_5cc)
            }
            else if (ageInWeeks in 4 * 52..< 6 * 52) {
                resultText = getString(R.string.dose_7_5cc)
            }
            else if (ageInWeeks in 6 * 52..< 8 * 52) {
                resultText = getString(R.string.dose_10cc)
            }
            else if (ageInWeeks in 8 * 52..< 9 * 52) {
                resultText = getString(R.string.dose_10_12_5cc)
            }
            else if (ageInWeeks >= 9 * 52) {
                resultText = getString(R.string.dose_15cc)
            }

            return buildString {
                append(resultText)
                append("\n\n⚠ ")
                append(getString(R.string.warning_fever_usage))
                append(getString(R.string.warning_ibuprofen_syrup_usage))
            }
        }
        // End drug_ibuprofen_syrup

        // Start drug_paracetamol_syrup
        else if (drug == getString(R.string.drug_paracetamol_syrup))
        {
            if (weight > 0 && weight < 2.7f)
            {
                resultText = getString(R.string.weight_below_2_7_paracetamol_syrup)
            }
            else if (weight in 2.7f..5.3f) {
                resultText = getString(R.string.dose_1_5cc)
            }
            else if (weight in 5.4f..8.1f) {
                resultText = getString(R.string.dose_3_5cc)
            }
            else if (weight in 8.2f..10.8f) {
                resultText = getString(R.string.dose_5cc)
            }
            else if (weight in 10.9f..16.3f) {
                resultText = getString(R.string.dose_6_5cc)
            }
            else if (weight in 16.4f..21.7f) {
                resultText = getString(R.string.dose_10cc)
            }
            else if (weight in 21.8f..27.2f) {
                resultText = getString(R.string.dose_13_5cc)
            }
            else if (weight in 27.3f..32.6f) {
                resultText = getString(R.string.dose_13_5_16_5_5cc)
            }
            else if (weight >= 32.7f) {
                resultText = getString(R.string.dose_20cc)
            }
            else if (ageInWeeks in 0..3 * 4) {
                resultText = getString(R.string.dose_1_5cc)
            }
            else if (ageInWeeks in 4 * 4..< 11 * 4) {
                resultText = getString(R.string.dose_3_5cc)
            }
            else if (ageInWeeks in 1 * 52..< 2 * 52) {
                resultText = getString(R.string.dose_5cc)
            }
            else if (ageInWeeks in 2 * 52..< 3 * 52) {
                resultText = getString(R.string.dose_6_5cc)
            }
            else if (ageInWeeks in 3 * 52..< 5 * 52) {
                resultText = getString(R.string.dose_10cc)
            }
            else if (ageInWeeks in 5 * 52..< 8 * 52) {
                resultText = getString(R.string.dose_13_5cc)
            }
            else if (ageInWeeks in 8 * 52..< 10 * 52) {
                resultText = getString(R.string.dose_13_5_16_5_5cc)
            }
            else if (ageInWeeks >= 10 * 52) {
                resultText = getString(R.string.dose_20cc)
            }

            return buildString {
                append(resultText)
                append("\n\n⚠ ")
                append(getString(R.string.warning_paracetamol_syrup))
            }
        }
        // End drug_paracetamol_syrup

        // Start drug_paracetamol_drop
        else if (drug == getString(R.string.drug_paracetamol_drop))
        {
            if (ageInWeeks > 2 * 52) {
                resultText = getString(R.string.age_above_2_paracetamol_drop)
            }
            else if (weight > 0 && weight < 2.7f)
            {
                resultText = getString(R.string.weight_below_2_7_paracetamol_drop)
            }
            else if (weight > 10.8f)
            {
                resultText = getString(R.string.weight_above_10_8_paracetamol_drop)
            }
            else if (weight in 2.7f..5.3f) {
                resultText = getString(R.string.dose_10drop)
            }
            else if (weight in 5.4f..8.1f) {
                resultText = getString(R.string.dose_20drop)
            }
            else if (weight > 8.2f) {
                resultText = getString(R.string.dose_30drop)
            }
            else if (ageInWeeks in 0 * 4..3 * 4) {
                resultText = getString(R.string.dose_10drop)
            }
            else if (ageInWeeks in 4 * 4..< 11 * 4) {
                resultText = getString(R.string.dose_20drop)
            }
            else if (ageInWeeks >= 11 * 4) {
                resultText = getString(R.string.dose_30drop)
            }

            return buildString {
                append(resultText)
                append("\n\n⚠ ")
                append(getString(R.string.warning_paracetamol_drop))
            }
        }
        // End drug_paracetamol_drop

        // Start drug_paracetamol_suppository
        else if (drug == getString(R.string.drug_paracetamol_suppository))
        {
            if (ageInWeeks > 0 && ageInWeeks  < 6 * 4)
            {
                resultText = getString(R.string.age_below_6mon_paracetamol_suppository)
            }
            if (weight > 0 && weight < 5.4f)
            {
                resultText = getString(R.string.weight_below_5_4_paracetamol_suppository)
            }
            else if (weight in 5.4f..8.1f) {
                resultText = getString(R.string.suppository_75_5_4)
            }
            else if (weight in 8.2f..16.3f) {
                resultText = getString(R.string.suppository_75_8_2)
            }
            else if (weight in 16.4f..21.7f) {
                resultText = getString(R.string.suppository_125)
            }
            else if (weight >= 21.8f) {
                resultText = getString(R.string.suppository_325)
            }
            else if (ageInWeeks in 6 * 4..< 12 * 4) {
                resultText = getString(R.string.suppository_75_5_4)
            }
            else if (ageInWeeks in 12 * 4..< 3 * 52) {
                resultText = getString(R.string.suppository_75_8_2)
            }
            else if (ageInWeeks in 3 * 52..< 6 * 52) {
                resultText = getString(R.string.suppository_125)
            }
            else if (ageInWeeks >= 6 * 52) {
                resultText = getString(R.string.suppository_325)
            }

            return resultText
//            return buildString {
//                append(resultText)
//                append("\n\n⚠ ")
//                append(getString(R.string.warning_paracetamol_suppository))
//            }
        }
        // End drug_paracetamol_suppository

        // Start drug_ibuprofen_suppository
        else if (drug == getString(R.string.drug_ibuprofen_suppository))
        {
            if (ageInWeeks > 0 && ageInWeeks  < 6 * 4)
            {
                resultText = getString(R.string.age_below_6mon_ibuprofen_suppository)
            }
            else if (ageInWeeks >= 6 * 4 && ageInWeeks  <= 8 * 4)
            {
                resultText = getString(R.string.age_6_8_mon_ibuprofen_suppository)
            }
            else if (weight > 0 && weight < 7.5f)
            {
                resultText = getString(R.string.weight_below_7_5_ibuprofen_suppository)
            }
            else if (weight in 7.5f..< 10f) {
                resultText = getString(R.string.suppository_75_1)
            }
            else if (weight in 10f..< 15f) {
                resultText = getString(R.string.suppository_75_2)
            }
            else if (weight in 15f..< 20f) {
                resultText = getString(R.string.suppository_150_1)
            }
            else if (weight > 20f) {
                resultText = getString(R.string.suppository_150_2)
            }
            else if (ageInWeeks >= 8 * 4 && ageInWeeks  < 12 * 4)
            {
                resultText = getString(R.string.suppository_75_1)
            }
            else if (ageInWeeks >= 12 * 4 && ageInWeeks  < 3 * 52)
            {
                resultText = getString(R.string.suppository_75_2)
            }
            else if (ageInWeeks >= 3 * 52 && ageInWeeks  < 6 * 52)
            {
                resultText = getString(R.string.suppository_150_1)
            }
            else if (ageInWeeks >= 6 * 52)
            {
                resultText = getString(R.string.suppository_150_2)
            }

            return resultText
//            return buildString {
//                append(resultText)
//                append("\n\n⚠ ")
//                append(getString(R.string.warning_paracetamol_suppository))
//            }
        }
        // End drug_ibuprofen_suppository

        return getString(R.string.dose_no_data)
    }
}