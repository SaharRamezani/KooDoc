package com.example.kidzi.ui.toxic

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.kidzi.R
import com.example.kidzi.databinding.FragmentToxicNoMedDetailBinding
import com.example.kidzi.ui.vaccine.VaccineFragmentArgs
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ToxicNoMedDetailFragment : Fragment() {

    var ways = false
    var symptoms = false
    var family = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentToxicNoMedDetailBinding.inflate(inflater)

        val pos = ToxicNoMedDetailFragmentArgs.fromBundle(requireArguments()).row
        val name = ToxicNoMedDetailFragmentArgs.fromBundle(requireArguments()).name

        binding.btnBack.setOnClickListener { findNavController().popBackStack() }
        binding.toxName.text = name

        var cautionsList = resources.getStringArray(R.array.toxic_drug_caution)
        if(cautionsList[pos].isNotEmpty()){
            binding.txtCaution.text = cautionsList[pos]
        }else{
            binding.cardCaution.visibility = View.GONE
        }



        var familyList = resources.getStringArray(R.array.toxic_drug_family)
        if(familyList[pos].isNotEmpty()){
            binding.familyDetail.text = familyList[pos]
        }else{
            binding.cardFamily.visibility = View.GONE
        }

        var waysList = resources.getStringArray(R.array.toxic_drugs_ways)
        if(waysList[pos].isNotEmpty()){
            binding.waysDetail.text = waysList[pos]
        }else{
            binding.cardWays.visibility = View.GONE
        }

        var symptomsList = resources.getStringArray(R.array.toxic_drug_symptom)
        if(symptomsList[pos].isNotEmpty()){
            binding.symptomsDetail.text = symptomsList[pos]
        }else{
            binding.cardSymptom.visibility = View.GONE
        }



        binding.cardWays.setOnClickListener {
            ways = !ways
            if(ways){
                binding.layoutMoreWays.visibility = View.VISIBLE
                binding.arrowWays.setImageResource(R.drawable.ic_arrow_up)
            }else{
                binding.layoutMoreWays.visibility = View.GONE
                binding.arrowWays.setImageResource(R.drawable.ic_arrow_down)
            }
        }
        binding.cardSymptom.setOnClickListener {
            symptoms = !symptoms
            if(symptoms){
                binding.layoutMoreSymptoms.visibility = View.VISIBLE
                binding.arrowSymptom.setImageResource(R.drawable.ic_arrow_up)
            }else{
                binding.layoutMoreSymptoms.visibility = View.GONE
                binding.arrowSymptom.setImageResource(R.drawable.ic_arrow_down)
            }
        }
        binding.cardFamily.setOnClickListener {
            family = !family
            if(family){
                binding.layoutMoreFamily.visibility = View.VISIBLE
                binding.arrowFamily.setImageResource(R.drawable.ic_arrow_up)
            }else{
                binding.layoutMoreFamily.visibility = View.GONE
                binding.arrowFamily.setImageResource(R.drawable.ic_arrow_down)
            }
        }

        return binding.root
    }

}