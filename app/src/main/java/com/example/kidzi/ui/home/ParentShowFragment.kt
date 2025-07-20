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
import com.example.kidzi.util.showLocalizedDatePicker
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ParentShowFragment : Fragment() {

    @Inject lateinit var preferenceManager: PreferenceManager

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
            showLocalizedDatePicker(requireContext()) { selectedDate ->
                binding.btnDate.text = selectedDate
                preferenceManager.setParentBirth(selectedDate)
            }
        }

        binding.btnNext.setOnClickListener {
            findNavController().navigate(R.id.action_parentShowFragment_to_accountFragment)
        }

        return binding.root
    }
}