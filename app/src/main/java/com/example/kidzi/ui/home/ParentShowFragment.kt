package com.example.kidzi.ui.home

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.widget.addTextChangedListener
import com.pouyaheydari.lineardatepicker.PersianLinearDatePicker
import androidx.navigation.fragment.findNavController
import com.example.kidzi.R
import com.example.kidzi.databinding.FragmentParentShowBinding
import com.example.kidzi.di.db.PreferenceManager
import com.example.kidzi.util.NumberFormatter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ParentShowFragment : Fragment() {

    @Inject lateinit var preferenceManager: PreferenceManager

    private fun showDatePickerDialog(button: Button) {
        val datePicker = PersianLinearDatePicker(requireContext()).apply {
            setMaxYear(1400,1320)
        }

        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("انتخاب تاریخ")
            .setView(datePicker)
            .setPositiveButton("تأیید") { _, _ ->
                val year = datePicker.getSelectedYear()
                val month = datePicker.getSelectedMonth()
                val day = datePicker.getSelectedDay()
                val selectedDate = "$year/${month.toString().padStart(2, '0')}/${day.toString().padStart(2, '0')}"
                button.text = context?.let { NumberFormatter.formatNumber(it, selectedDate) }
                preferenceManager.setParentBirth(selectedDate)
            }
            .setNegativeButton("لغو", null)
            .create()

        dialog.show()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentParentShowBinding.inflate(inflater)

        binding.txtName.setText(preferenceManager.getParentName())
        binding.btnDate.text = preferenceManager.getParentBirth()
        binding.btnDate.text = context?.let { NumberFormatter.formatNumber(it, preferenceManager.getParentBirth()) }

        if (preferenceManager.getParentCare() == 1)
            binding.radioCaringYes.isChecked = true
        else
            binding.radioCaringNo.isChecked = true

        if (preferenceManager.getParentJob() == 1)
            binding.radioWorkingYes.isChecked = true
        else
            binding.radioWorkingNo.isChecked = true

        // Update name immediately on text change
        binding.txtName.addTextChangedListener { editable ->
            editable?.let {
                preferenceManager.setParentName(it.toString().trim())
            }
        }

        // Radio buttons: Caring
        binding.radioCaringYes.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) preferenceManager.setParentCare(1)
        }
        binding.radioCaringNo.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) preferenceManager.setParentCare(0)
        }

        // Radio buttons: Working
        binding.radioWorkingYes.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) preferenceManager.setParentJob(1)
        }
        binding.radioWorkingNo.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) preferenceManager.setParentJob(0)
        }

        binding.btnDate.setOnClickListener {
            showDatePickerDialog(binding.btnDate)
        }

        binding.btnNext.setOnClickListener {
            findNavController().navigate(R.id.action_parentShowFragment_to_accountFragment)
        }

        return binding.root
    }
}