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
import com.example.kidzi.util.showLocalizedDatePicker
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

        binding.btnDate.setOnClickListener { showLocalizedDatePicker(requireContext()) { selectedDate ->
            binding.btnDate.text = selectedDate
        }}

        binding.btnBack.setOnClickListener {
            findNavController().navigate(ParentInfoFragmentDirections.actionParentInfoFragmentToParentInfoIntroFragment())
        }

        return binding.root
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