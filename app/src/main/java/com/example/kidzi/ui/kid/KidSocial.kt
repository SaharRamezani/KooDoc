package com.example.kidzi.ui.kid

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.kidzi.R
import com.example.kidzi.databinding.FragmentKidSocialBinding
import com.example.kidzi.di.db.dao.FamilyDiseaseDao
import com.example.kidzi.di.db.dao.KidSocialDao
import com.example.kidzi.di.db.models.KidSocialModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class KidSocial : Fragment() {

    lateinit var binding: FragmentKidSocialBinding
    @Inject lateinit var kidSocialDao: KidSocialDao
    @Inject lateinit var familyDis: FamilyDiseaseDao

    var isNew = true
    var kidId = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentKidSocialBinding.inflate(inflater, container, false)

        kidId = KidSocialArgs.fromBundle(requireArguments()).kidId
        isNew = KidSocialArgs.fromBundle(requireArguments()).new

        if(!isNew){
            try {
                var kido = kidSocialDao.getKidInfo(id)
                if(kido.social) binding.radioYes.isChecked = true
                else binding.radioNo.isChecked = true
            }catch (e: Exception){

            }
        }

        binding.btnBack.setOnClickListener {
            findNavController().navigate(KidSocialDirections.actionKidSocialToKidAlergyFragment(kidId,false))
        }

        binding.btnNext.setOnClickListener {
            if(binding.radioYes.isChecked||binding.radioNo.isChecked){
                val socialMode = binding.radioYes.isChecked
                if(isNew)
                    kidSocialDao.insert(KidSocialModel(kidId,socialMode))
                else
                    kidSocialDao.update(KidSocialModel(kidId,socialMode))

                if(!isNew)
                    findNavController().navigate(KidSocialDirections.actionKidSocialToMainFragment())
                else
                    findNavController().navigate(KidSocialDirections.actionKidSocialToFamilyDisFirstFragment(kidId,isNew))

            }else{
                Toast.makeText(requireContext(),"باید یک گزینه را انتخاب نمایید.",Toast.LENGTH_SHORT).show()
            }
        }


        return binding.root
    }

}