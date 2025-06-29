package com.example.kidzi.ui.home

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.kidzi.R
import com.example.kidzi.databinding.FragmentParentInfoBinding
import com.example.kidzi.di.db.PreferenceManager
import com.example.kidzi.util.NumberFormatter
import com.pouyaheydari.lineardatepicker.PersianLinearDatePicker
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ParentInfoFragment : Fragment() {
    @Inject
    lateinit var sharedPreferences: PreferenceManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentParentInfoBinding.inflate(inflater)

        binding.btnNext.setOnClickListener {
            sharedPreferences.updateLevel(2)
            if(checkNext(binding))
                findNavController().navigate(ParentInfoFragmentDirections.actionParentInfoFragmentToKidIntroFragment())
        }

        binding.btnDate.setOnClickListener { showDatePickerDialog(binding.btnDate) }

        binding.btnBack.setOnClickListener {
            findNavController().navigate(ParentInfoFragmentDirections.actionParentInfoFragmentToParentInfoIntroFragment())
        }

        return binding.root
    }

    private fun showDatePickerDialog(button: Button) {
        val datePicker = PersianLinearDatePicker(requireContext()).apply {
            setMaxYear(1400,1320)
        }

        val dialog = AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.dialog_title_select_date))
            .setView(datePicker)
            .setPositiveButton(getString(R.string.dialog_positive_button)) { _, _ ->
                val year = datePicker.getSelectedYear()
                val month = datePicker.getSelectedMonth()
                val day = datePicker.getSelectedDay()
                val selectedDate = "$year/${month.toString().padStart(2, '0')}/${day.toString().padStart(2, '0')}"

                button.text = context?.let { NumberFormatter.formatNumber(it, selectedDate) }
            }
            .setNegativeButton(getString(R.string.dialog_negative_button), null)
            .create()

        dialog.show()
    }

    private fun checkNext(binding: FragmentParentInfoBinding): Boolean {
        if (binding.txtName.text.isNullOrBlank()) {
            Toast.makeText(requireContext(), getString(R.string.toast_enter_name), Toast.LENGTH_SHORT).show()
            return false
        }

        if (!binding.btnDate.text.contains("/")) {
            Toast.makeText(requireContext(), getString(R.string.toast_enter_birth_date), Toast.LENGTH_SHORT).show()
            return false
        }

        if (!binding.radioWorkingYes.isChecked && !binding.radioWorkingNo.isChecked) {
            Toast.makeText(requireContext(), getString(R.string.toast_select_working_status), Toast.LENGTH_SHORT).show()
            return false
        }

        if (!binding.radioCaringYes.isChecked && !binding.radioCaringNo.isChecked) {
            Toast.makeText(requireContext(), getString(R.string.toast_select_care_status), Toast.LENGTH_SHORT).show()
            return false
        }

        sharedPreferences.updateParentName(binding.txtName.text.toString())
        sharedPreferences.updateParentBirth(binding.btnDate.text.toString())
        val working = if (binding.radioWorkingYes.isChecked) 1 else 2
        val caring = if (binding.radioCaringYes.isChecked) 1 else 2
        sharedPreferences.updateParentJob(working)
        sharedPreferences.updateParentCare(caring)
        sharedPreferences.updateLevel(3)

        return true
    }
}