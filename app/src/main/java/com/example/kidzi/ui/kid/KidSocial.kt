package com.example.kidzi.ui.kid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.kidzi.databinding.FragmentKidSocialBinding
import com.example.kidzi.di.db.dao.FamilyDiseaseDao
import com.example.kidzi.di.db.dao.KidSocialDao
import com.example.kidzi.di.db.models.KidSocialModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class KidSocial : Fragment() {

    private lateinit var binding: FragmentKidSocialBinding
    @Inject lateinit var kidSocialDao: KidSocialDao
    @Inject lateinit var familyDis: FamilyDiseaseDao

    private var isNew = true
    private var kidId = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentKidSocialBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        kidId = KidSocialArgs.fromBundle(requireArguments()).kidId
        isNew = KidSocialArgs.fromBundle(requireArguments()).new

        if (!isNew) {
            loadSocialData()
        }

        binding.btnBack.setOnClickListener {
            findNavController().navigate(KidSocialDirections.actionKidSocialToKidAlergyFragment(kidId, false))
        }

        binding.btnNext.setOnClickListener {
            if (binding.radioYes.isChecked || binding.radioNo.isChecked) {
                saveSocialDataAndNavigate()
            } else {
                Toast.makeText(requireContext(), "باید یک گزینه را انتخاب نمایید.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loadSocialData() {
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val kido = kidSocialDao.getKidInfo(kidId) ?: return@launch
                if (kido.social) {
                    binding.radioYes.isChecked = true
                } else {
                    binding.radioNo.isChecked = true
                }
            } catch (e: Exception) {
                // Optionally log or show an error
            }
        }
    }

    private fun saveSocialDataAndNavigate() {
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val socialMode = binding.radioYes.isChecked
                val model = KidSocialModel(kidId, socialMode)
                if (isNew) {
                    kidSocialDao.insert(model)
                } else {
                    kidSocialDao.update(model)
                }

                findNavController().navigate(
                    KidSocialDirections.actionKidSocialToFamilyDisFirstFragment(kidId, isNew)
                )

            } catch (e: Exception) {
                Toast.makeText(requireContext(), "خطا در ذخیره اطلاعات", Toast.LENGTH_SHORT).show()
            }
        }
    }
}