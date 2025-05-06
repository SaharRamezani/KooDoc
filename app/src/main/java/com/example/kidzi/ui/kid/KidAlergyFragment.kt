package com.example.kidzi.ui.kid

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.kidzi.R
import com.example.kidzi.databinding.FragmentKidAlergyBinding
import com.example.kidzi.databinding.FragmentKidDiseaseBinding
import com.example.kidzi.di.db.dao.KidAlergyDao
import com.example.kidzi.di.db.dao.KidDiseaseDao
import com.example.kidzi.di.db.models.KidAlergyModel
import com.example.kidzi.di.db.models.KidDiseaseModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class KidAlergyFragment : Fragment() {

    var isNew = true
    var kidId = 0
    lateinit var binding: FragmentKidAlergyBinding


    @Inject lateinit var kidAlergyDao: KidAlergyDao
    var honey = false
    var peanut = false
    var lac = false
    var cow = false
    var alcohol = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentKidAlergyBinding.inflate(inflater)
        kidId = KidAlergyFragmentArgs.fromBundle(requireArguments()).kidId
        Log.i("Log1","kidId is: $kidId")
        isNew = KidAlergyFragmentArgs.fromBundle(requireArguments()).new
        if(!isNew){
            try {
                var kido = kidAlergyDao.getKidInfo(kidId)
                if(kido.honey) binding.checkHoney.isChecked = true
                if(kido.lac) binding.checkLactoz.isChecked = true
                if(kido.peanut) binding.checkPeanut.isChecked = true
                if(kido.cow) binding.checkProtein.isChecked = true
                if(kido.alcohol) binding.checkAlcohol.isChecked = true
            }catch (e: Exception){

            }
        }
        binding.checkHoney.setOnCheckedChangeListener { _, isChecked -> honey = isChecked}
        binding.checkLactoz.setOnCheckedChangeListener { _, isChecked -> lac = isChecked}
        binding.checkProtein.setOnCheckedChangeListener { _, isChecked -> cow = isChecked}
        binding.checkPeanut.setOnCheckedChangeListener { _, isChecked -> peanut = isChecked}
        binding.checkAlcohol.setOnCheckedChangeListener { _, isChecked -> alcohol = isChecked}

        binding.btnNext.setOnClickListener {
            if(saveAlergy()){ findNavController().navigate(KidAlergyFragmentDirections.actionKidAlergyFragmentToKidSocial(kidId,isNew)) }
            else Toast.makeText(requireContext(),"خطا در ذخیره اطلاعات",Toast.LENGTH_SHORT).show()
        }


        binding.btnBack.setOnClickListener {
            findNavController().navigate(KidAlergyFragmentDirections.actionKidAlergyFragmentToKidDiseaseFragment(kidId,false))
        }

        return binding.root
    }
    private fun saveAlergy(): Boolean{
            if(isNew)
                kidAlergyDao.insert(KidAlergyModel(kidId,peanut,honey,lac,cow,alcohol))
            else
                kidAlergyDao.update(KidAlergyModel(kidId,peanut,honey,lac,cow,alcohol))
            return true
    }

}