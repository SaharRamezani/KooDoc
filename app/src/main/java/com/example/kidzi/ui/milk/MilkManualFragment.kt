package com.example.kidzi.ui.milk

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.kidzi.R
import com.example.kidzi.databinding.FragmentMilkManualBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MilkManualFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentMilkManualBinding.inflate(inflater, container, false)

        binding.btnBack.setOnClickListener { findNavController().popBackStack() }

        binding.btnRegister.setOnClickListener {
            if(binding.txtAge.text.toString().isNotEmpty()&&!binding.txtAge.text.contains(".")){
                if(binding.radioLacNo.isChecked||binding.radioLacYes.isChecked){
                    if(binding.radioCaringNo.isChecked||binding.radioCaringYes.isChecked){
                        if(binding.radioDietYes.isChecked){
                            findNavController().navigate(
                                MilkManualFragmentDirections.actionMilkManualFragmentToMilkResultFragment(3,
                                    binding.txtAge.text.toString().toInt(),
                                    binding.radioLacYes.isChecked,
                                    binding.radioCaringYes.isChecked)
                            )
                        }else{

                            findNavController().navigate(
                                MilkManualFragmentDirections.actionMilkManualFragmentToMilkResultFragment(4,
                                    binding.txtAge.text.toString().toInt(),
                                    binding.radioLacYes.isChecked,
                                    binding.radioCaringYes.isChecked)
                            )
                        }
                    }else{
                        Toast.makeText(requireContext(),"حساسیت به شیر گاو باید مشخص گردد.",Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(requireContext(),"حساسیت به لاکتوز باید مشخص گردد.",Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(requireContext(),"سن باید بصورت عدد صحیح وارد شود",Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }

}