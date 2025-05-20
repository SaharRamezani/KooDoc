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
import com.pouyaheydari.lineardatepicker.PersianLinearDatePicker
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ParentInfoFragment : Fragment() {
    @Inject
    lateinit var sharedPreferences: PreferenceManager

    private fun convertToPersianDigits(input: String): String {
        val persianDigits = listOf('۰','۱','۲','۳','۴','۵','۶','۷','۸','۹')
        return input.map {
            if (it.isDigit()) persianDigits[it.digitToInt()] else it
        }.joinToString("")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
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
        // Create the PersianLinearDatePicker
        val datePicker = PersianLinearDatePicker(requireContext()).apply {
            setMaxYear(1400,1320)
        }

        // Create an AlertDialog to host the date picker
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("انتخاب تاریخ")
            .setView(datePicker)
            .setPositiveButton("تأیید") { _, _ ->
                // Get the selected date
                val year = datePicker.getSelectedYear()
                val month = datePicker.getSelectedMonth()
                val day = datePicker.getSelectedDay()
                val selectedDate = "$year/${month.toString().padStart(2, '0')}/${day.toString().padStart(2, '0')}"
                // Update button text
                button.text = convertToPersianDigits(selectedDate)
            }
            .setNegativeButton("لغو", null)
            .create()

        dialog.show()
    }

    private fun checkNext(binding: FragmentParentInfoBinding): Boolean {
        if (binding.txtName.text.isNullOrBlank()) {
            Toast.makeText(requireContext(), "نام خود را وارد کنید", Toast.LENGTH_SHORT).show()
            return false
        }

        if (!binding.btnDate.text.contains("/")) {
            Toast.makeText(requireContext(), "تاریخ تولد خود را وارد کنید.", Toast.LENGTH_SHORT).show()
            return false
        }

        if (!binding.radioWorkingYes.isChecked && !binding.radioWorkingNo.isChecked) {
            Toast.makeText(requireContext(), "وضعیت اشتغال خود را انتخاب کنید.", Toast.LENGTH_SHORT).show()
            return false
        }

        if (!binding.radioCaringYes.isChecked && !binding.radioCaringNo.isChecked) {
            Toast.makeText(requireContext(), "وضعیت مراقبت خود را انتخاب کنید.", Toast.LENGTH_SHORT).show()
            return false
        }

        // All inputs are valid, update shared preferences
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