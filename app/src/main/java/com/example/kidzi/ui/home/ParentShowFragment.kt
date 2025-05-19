package com.example.kidzi.ui.home

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.example.kidzi.R
import com.example.kidzi.databinding.FragmentParentShowBinding
import com.example.kidzi.di.db.PreferenceManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ParentShowFragment : Fragment() {

    @Inject lateinit var preferenceManager: PreferenceManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentParentShowBinding.inflate(inflater)

        // binding.btnBack.setOnClickListener { findNavController().popBackStack() }

        binding.btnDate.text = preferenceManager.getParentBirth()
        binding.txtName.setText( preferenceManager.getParentName())

        if (preferenceManager.getParentCare() == 1)
            binding.radioCaringYes.isChecked = true
        else
            binding.radioCaringNo.isChecked = true

        if (preferenceManager.getParentJob() == 1)
            binding.radioWorkingYes.isChecked = true
        else
            binding.radioWorkingNo.isChecked = true

        // Update name immediately on text change
        binding.txtName.addTextChangedListener {
            val newName = it.toString().trim()
            preferenceManager.setParentName(newName)
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

        // Optional: Save on date button click if user selects a new date
        binding.btnDate.setOnClickListener {
            // showDatePicker(...) -> On date selected:
            val selectedDate = "2025-05-19" // Example
            binding.btnDate.text = selectedDate
            preferenceManager.setParentBirth(selectedDate)
        }

        // The "Next" button can still be used for navigation or confirmation
        binding.btnNext.setOnClickListener {
            findNavController().navigate(R.id.action_parentShowFragment_to_accountFragment)
        }

        return binding.root
    }
}