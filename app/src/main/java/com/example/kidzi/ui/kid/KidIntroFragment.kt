package com.example.kidzi.ui.kid

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.kidzi.R
import com.example.kidzi.databinding.FragmentKidIntroBinding
import com.example.kidzi.di.db.PreferenceManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class KidIntroFragment : Fragment() {


    @Inject
    lateinit var sharedPreferences: PreferenceManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentKidIntroBinding.inflate(inflater)

        binding.txtName.text = sharedPreferences.getParentName()

        binding.btnNext.setOnClickListener {
            findNavController().navigate(KidIntroFragmentDirections.actionKidIntroFragmentToKidInfoFragment(0,true))
        }
        binding.btnBack.setOnClickListener {
            findNavController().navigate(KidIntroFragmentDirections.actionKidIntroFragmentToParentInfoFragment())
        }



        return binding.root
    }

}