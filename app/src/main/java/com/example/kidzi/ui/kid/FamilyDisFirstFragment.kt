package com.example.kidzi.ui.kid

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.kidzi.R
import com.example.kidzi.databinding.FragmentFamilyDisFirstBinding
import com.example.kidzi.di.db.PreferenceManager
import com.example.kidzi.di.db.dao.FamilyDiseaseDao
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class FamilyDisFirstFragment : Fragment() {

    @Inject lateinit var familyDiseaseDao: FamilyDiseaseDao
    @Inject lateinit var sharedPreferences: PreferenceManager

    lateinit var binding: FragmentFamilyDisFirstBinding
    var isNew = true
    var kidId = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFamilyDisFirstBinding.inflate(inflater)
        kidId = FamilyDisFirstFragmentArgs.fromBundle(requireArguments()).kidId
        isNew = FamilyDisFirstFragmentArgs.fromBundle(requireArguments()).new

        binding.fatherDisease.setOnClickListener {
            findNavController().navigate(FamilyDisFirstFragmentDirections.actionFamilyDisFirstFragmentToFamilyDisSecondFragment(1))
        }
        binding.motherDisease.setOnClickListener {
            findNavController().navigate(FamilyDisFirstFragmentDirections.actionFamilyDisFirstFragmentToFamilyDisSecondFragment(2))
        }

        binding.btnNext.setOnClickListener {
            sharedPreferences.updateLevel(4)
            findNavController().navigate(FamilyDisFirstFragmentDirections.actionFamilyDisFirstFragmentToMainFragment())
            /*if (familyDiseaseDao.getAll().size>1) {
            }else{
                Toast.makeText(requireContext(),"اطلاعات هر دو والد را وارد کنید.",Toast.LENGTH_SHORT).show()
            }*/
        }

        binding.btnBack.setOnClickListener {
            findNavController().navigate(FamilyDisFirstFragmentDirections.actionFamilyDisFirstFragmentToKidSocial(kidId,false))
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        val data = familyDiseaseDao.getAll()
        if(data.size > 1){
            binding.fatherDisease.setCardBackgroundColor(requireContext().getColor(R.color.button_success_bg))
            binding.motherDisease.setCardBackgroundColor(requireContext().getColor(R.color.button_success_bg))
        }else if(data.isEmpty()){
            binding.fatherDisease.setCardBackgroundColor(requireContext().getColor(R.color.white))
            binding.motherDisease.setCardBackgroundColor(requireContext().getColor(R.color.white))
        }else{
            if(data[0].parentId == 1)
                binding.fatherDisease.setCardBackgroundColor(requireContext().getColor(R.color.button_success_bg))
            else
                binding.motherDisease.setCardBackgroundColor(requireContext().getColor(R.color.button_success_bg))
        }
    }
}