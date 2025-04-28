package com.example.kidzi.ui.home

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        binding.btnBack.setOnClickListener { findNavController().popBackStack() }

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


        return binding.root
    }
}