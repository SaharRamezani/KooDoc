package com.example.kidzi.ui.vaccine

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.kidzi.R
import com.example.kidzi.databinding.FragmentVaccineAdviceBinding
import dagger.hilt.android.AndroidEntryPoint

// TODO: Rename parameter arguments, choose names th
@AndroidEntryPoint
class VaccineAdviceFragment : Fragment() {

    var after = false
    var mental = false
    var physical = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentVaccineAdviceBinding.inflate(inflater, container, false)

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.cardAfter.setOnClickListener {
            after = !after
            if(after){
                binding.layoutMoreAfter.visibility = View.VISIBLE
                binding.arrowAfter.setImageDrawable(requireContext().getDrawable(R.drawable.ic_arrow_up))
            }else{
                binding.layoutMoreAfter.visibility = View.GONE
                binding.arrowAfter.setImageDrawable(requireContext().getDrawable(R.drawable.ic_arrow_down))

            }
        }

        binding.cardMental.setOnClickListener {
            mental = !mental
            if(mental){
                binding.layoutMoreMental.visibility = View.VISIBLE
                binding.arrowMental.setImageDrawable(requireContext().getDrawable(R.drawable.ic_arrow_up))
            }else{
                binding.layoutMoreMental.visibility = View.GONE
                binding.arrowMental.setImageDrawable(requireContext().getDrawable(R.drawable.ic_arrow_down))
            }
        }

        binding.cardPhysical.setOnClickListener {
            physical = !physical
            if(physical){
                binding.layoutMorePhysical.visibility = View.VISIBLE
                binding.arrowPhysical.setImageDrawable(requireContext().getDrawable(R.drawable.ic_arrow_up))
            }else{
                binding.layoutMorePhysical.visibility = View.GONE
                binding.arrowPhysical.setImageDrawable(requireContext().getDrawable(R.drawable.ic_arrow_down))
            }

        }

        return binding.root
    }
}