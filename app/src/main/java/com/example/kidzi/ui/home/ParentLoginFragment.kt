package com.example.kidzi.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.kidzi.R
import com.example.kidzi.databinding.FragmentParentLoginBinding
import com.example.kidzi.di.db.PreferenceManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ParentLoginFragment : Fragment() {
    @Inject
    lateinit var sharedPreferences: PreferenceManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentParentLoginBinding.inflate(inflater)

        binding.btnNext.setOnClickListener {
            if(binding.radioDad.isChecked||binding.radioMom.isChecked||binding.radioOther.isChecked){
                var parent = if(binding.radioDad.isChecked)
                    1
                else if(binding.radioMom.isChecked)
                    2
                else
                    3
                sharedPreferences.updateParent(parent)
                sharedPreferences.updateLevel(2)
                findNavController().navigate(ParentLoginFragmentDirections.actionParentLoginFragmentToParentInfoIntroFragment())
            }else{
                Toast.makeText(requireContext(),"باید یکی از گزینه ها را انتخاب کنید.",Toast.LENGTH_SHORT).show()
            }
        }



        return binding.root
    }

}