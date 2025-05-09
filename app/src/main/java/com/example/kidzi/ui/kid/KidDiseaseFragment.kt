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
import com.example.kidzi.databinding.FragmentKidDiseaseBinding
import com.example.kidzi.di.db.dao.KidDiseaseDao
import com.example.kidzi.di.db.models.KidDiseaseModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class KidDiseaseFragment : Fragment() {
    var isNew = true
    lateinit var binding: FragmentKidDiseaseBinding
    var kidId = 0
    var diabetB = false
    var fawB = false
    var metaB = false
    var asmB = false
    var fiberB = false
    var selB = false
    var otherB = ""
    @Inject lateinit var kidDiseaseDao: KidDiseaseDao

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentKidDiseaseBinding.inflate(inflater)
        kidId = KidDiseaseFragmentArgs.fromBundle(requireArguments()).kidId

        Log.i("Log1","kidId is: $kidId")
        isNew = KidDiseaseFragmentArgs.fromBundle(requireArguments()).new

        if(!isNew){
            try {
                var kido = kidDiseaseDao.getKidInfo(kidId)
                if(kido.asm) binding.checkAsm.isChecked = true
                if(kido.faw) binding.checkFawism.isChecked = true
                if(kido.fibr) binding.checkFibrozm.isChecked = true
                if(kido.meta) binding.checkMetabolism.isChecked = true
                if(kido.seli) binding.checkSeliac.isChecked = true
                if(kido.diabetes) binding.checkDiabetes.isChecked = true
                if(kido.other.length > 0) binding.txtDis.text = kido.other
            }catch (e: Exception){
                Log.i("Log1","error is: $e")
            }
        }

        binding.checkAsm.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked)
                asmB = true
            else
                asmB = false
        }

        binding.checkFawism.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked)
                fawB = true
            else
                fawB = false
        }
        binding.checkSeliac.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked)
                selB = true
            else
                selB = false
        }

        binding.checkFibrozm.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked)
                fiberB = true
            else
                fiberB = false
        }

        binding.checkDiabetes.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked)
                diabetB = true
            else
                diabetB = false
        }
        binding.checkMetabolism.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked)
                metaB = true
            else
                metaB = false
        }

        binding.btnAdd.setOnClickListener {
            if (!binding.txtName.text.isNullOrEmpty()) {
                if (binding.txtDis.text.isNullOrEmpty())
                    binding.txtDis.text = binding.txtName.text
                else
                    binding.txtDis.text = binding.txtDis.text.toString() + ", " + binding.txtName.text.toString()
            }
        }

        binding.btnBack.setOnClickListener {
            findNavController().navigate(KidDiseaseFragmentDirections.actionKidDiseaseFragmentToKidInfoFragment(kidId,false))
        }

        binding.btnNext.setOnClickListener {
            if(id > 0){
                if(saveDisease()){
                    findNavController().navigate(KidDiseaseFragmentDirections.actionKidDiseaseFragmentToKidAlergyFragment(kidId,isNew))
                }else
                    Toast.makeText(requireContext(),"کودک یافت نشد!",Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(requireContext(),"کودک یافت نشد!",Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }

    private fun saveDisease(): Boolean{
        try{
            if(isNew)
                kidDiseaseDao.insert(KidDiseaseModel(kidId,diabetB,fawB,metaB,asmB,fiberB,selB,binding.txtDis.text.toString()))
            else
                kidDiseaseDao.update(KidDiseaseModel(kidId,diabetB,fawB,metaB,asmB,fiberB,selB,binding.txtDis.text.toString()))
            return true
        }catch (e: Exception){
            return false
        }
    }
}