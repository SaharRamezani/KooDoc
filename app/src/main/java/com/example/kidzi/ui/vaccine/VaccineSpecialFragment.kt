package com.example.kidzi.ui.vaccine

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.kidzi.R
import com.example.kidzi.databinding.FragmentVaccineSpecialBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class VaccineSpecialFragment : Fragment() {

    private var ill = false
    private var yellow = false
    private var pre = false
    private var brain = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentVaccineSpecialBinding.inflate(inflater)
        binding.btnBack.setOnClickListener { findNavController().popBackStack() }

        binding.cardHot.setOnClickListener {
            ill = !ill
            if (ill){
                binding.layoutMoreHot.visibility = View.VISIBLE
                binding.arrowHot.setImageDrawable(requireContext().getDrawable(R.drawable.ic_arrow_up))
            }else{
                binding.layoutMoreHot.visibility = View.GONE
                binding.arrowHot.setImageDrawable(requireContext().getDrawable(R.drawable.ic_arrow_down))
            }
        }


        binding.yellowKids.setOnClickListener {
            yellow = !yellow
            if (yellow){
                binding.layoutMoreYellow.visibility = View.VISIBLE
                binding.arrowYellow.setImageDrawable(requireContext().getDrawable(R.drawable.ic_arrow_up))
            }else{
                binding.layoutMoreYellow.visibility = View.GONE
                binding.arrowYellow.setImageDrawable(requireContext().getDrawable(R.drawable.ic_arrow_down))
            }
        }


        binding.preKids.setOnClickListener {
            pre = !pre
            if (pre){
                binding.layoutMorePre.visibility = View.VISIBLE
                binding.arrowPre.setImageDrawable(requireContext().getDrawable(R.drawable.ic_arrow_up))
            }else{
                binding.layoutMorePre.visibility = View.GONE
                binding.arrowPre.setImageDrawable(requireContext().getDrawable(R.drawable.ic_arrow_down))
            }
        }


        binding.brainKids.setOnClickListener {
            brain = !brain
            if (brain){
                binding.layoutMoreBrain.visibility = View.VISIBLE
                binding.arrowBrain.setImageDrawable(requireContext().getDrawable(R.drawable.ic_arrow_up))
            }else{
                binding.layoutMoreBrain.visibility = View.GONE
                binding.arrowBrain.setImageDrawable(requireContext().getDrawable(R.drawable.ic_arrow_down))
            }
        }

        return binding.root
    }

}