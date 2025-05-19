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
    ): View {
        binding = FragmentKidAlergyBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        kidId = KidAlergyFragmentArgs.fromBundle(requireArguments()).kidId
        isNew = KidAlergyFragmentArgs.fromBundle(requireArguments()).new

        Log.i("Log1", "kidId is: $kidId")

        if (!isNew) {
            try {
                val kido = kidAlergyDao.getKidInfo(kidId)
                binding.checkHoney.isChecked = kido.honey
                binding.checkLactoz.isChecked = kido.lac
                binding.checkPeanut.isChecked = kido.peanut
                binding.checkProtein.isChecked = kido.cow
                binding.checkAlcohol.isChecked = kido.alcohol

                // Set internal state variables as well
                honey = kido.honey
                lac = kido.lac
                peanut = kido.peanut
                cow = kido.cow
                alcohol = kido.alcohol
            } catch (e: Exception) {
                Log.e("AlergyLoad", "Failed to load allergy data", e)
            }
        }

        // Keep this part here
        binding.checkHoney.setOnCheckedChangeListener { _, isChecked -> honey = isChecked }
        binding.checkLactoz.setOnCheckedChangeListener { _, isChecked -> lac = isChecked }
        binding.checkProtein.setOnCheckedChangeListener { _, isChecked -> cow = isChecked }
        binding.checkPeanut.setOnCheckedChangeListener { _, isChecked -> peanut = isChecked }
        binding.checkAlcohol.setOnCheckedChangeListener { _, isChecked -> alcohol = isChecked }

        binding.btnNext.setOnClickListener {
            if (saveAlergy()) {
                findNavController().navigate(
                    KidAlergyFragmentDirections.actionKidAlergyFragmentToKidSocial(kidId, isNew)
                )
            } else {
                Toast.makeText(requireContext(), "خطا در ذخیره اطلاعات", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnBack.setOnClickListener {
            if (saveAlergy()) {
                findNavController().navigate(
                    KidAlergyFragmentDirections.actionKidAlergyFragmentToKidDiseaseFragment(kidId, false)
                )
            } else {
                Toast.makeText(requireContext(), "خطا در ذخیره اطلاعات", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveAlergy(): Boolean{
            if(isNew)
                kidAlergyDao.insert(KidAlergyModel(kidId,peanut,honey,lac,cow,alcohol))
            else
                kidAlergyDao.update(KidAlergyModel(kidId,peanut,honey,lac,cow,alcohol))
            return true
    }

}