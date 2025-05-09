package com.example.kidzi.ui.kid

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.kidzi.R
import com.example.kidzi.databinding.FragmentFamilyDisFirstBinding
import com.example.kidzi.databinding.FragmentFamilyDisSecondBinding
import com.example.kidzi.di.db.dao.FamilyDiseaseDao
import com.example.kidzi.di.db.models.FamilyDiseaseModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FamilyDisSecondFragment : Fragment() {

    @Inject lateinit var familyDiseaseDao: FamilyDiseaseDao

    lateinit var binding: FragmentFamilyDisSecondBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFamilyDisSecondBinding.inflate(inflater)
        var parentId = FamilyDisSecondFragmentArgs.fromBundle(requireArguments()).parentId

        if(familyDiseaseDao.getAll().size>0){
            for(i in familyDiseaseDao.getAll()){
                if(i.parentId == parentId){
                    binding.checkAsm.isChecked = i.asm
                    binding.checkDiabetes.isChecked = i.diabetes
                    binding.checkSelf.isChecked = i.self
                    binding.checkAlergy.isChecked = i.alergy
                    binding.checkCancer.isChecked = i.cancer
                    binding.checkDigestive.isChecked = i.govaresh
                }
            }
        }

        if(parentId == 1)
            binding.txtParent.text = "پدر"
        else
            binding.txtParent.text = "مادر"

        binding.btnNext.setOnClickListener {
            save(parentId)
            findNavController().popBackStack()
        }

        return binding.root
    }

    fun save(parentId: Int){
        var asm = binding.checkAsm.isChecked
        var diabetes = binding.checkDiabetes.isChecked
        var self = binding.checkSelf.isChecked
        var alergy = binding.checkAlergy.isChecked
        var cancer = binding.checkCancer.isChecked
        var govaresh = binding.checkDigestive.isChecked
        familyDiseaseDao.insert(FamilyDiseaseModel(parentId,asm,alergy,diabetes,cancer,govaresh,self))
    }

}