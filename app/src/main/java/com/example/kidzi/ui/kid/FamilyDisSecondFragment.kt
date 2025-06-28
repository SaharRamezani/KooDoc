package com.example.kidzi.ui.kid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.kidzi.databinding.FragmentFamilyDisSecondBinding
import com.example.kidzi.di.db.dao.FamilyDiseaseDao
import com.example.kidzi.di.db.models.FamilyDiseaseModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class FamilyDisSecondFragment : Fragment() {

    @Inject lateinit var familyDiseaseDao: FamilyDiseaseDao
    private lateinit var binding: FragmentFamilyDisSecondBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFamilyDisSecondBinding.inflate(inflater)

        val parentId = FamilyDisSecondFragmentArgs.fromBundle(requireArguments()).parentId

        if (parentId == 1) {
            binding.txtParent.text = "پدر"
        } else {
            binding.txtParent.text = "مادر"
        }

        loadData(parentId)

        binding.btnNext.setOnClickListener {
            save(parentId)
        }

        return binding.root
    }

    private fun loadData(parentId: Int) {
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val allData = familyDiseaseDao.getAll()

                for (i in allData) {
                    if (i.parentId == parentId) {
                        binding.checkAsm.isChecked = i.asm
                        binding.checkDiabetes.isChecked = i.diabetes
                        binding.checkSelf.isChecked = i.self
                        binding.checkAlergy.isChecked = i.alergy
                        binding.checkCancer.isChecked = i.cancer
                        binding.checkDigestive.isChecked = i.govaresh
                        break
                    }
                }

            } catch (e: Exception) {
                // Optionally log or handle error
            }
        }
    }

    private fun save(parentId: Int) {
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val asm = binding.checkAsm.isChecked
                val diabetes = binding.checkDiabetes.isChecked
                val self = binding.checkSelf.isChecked
                val alergy = binding.checkAlergy.isChecked
                val cancer = binding.checkCancer.isChecked
                val govaresh = binding.checkDigestive.isChecked

                familyDiseaseDao.insert(
                    FamilyDiseaseModel(parentId, asm, alergy, diabetes, cancer, govaresh, self)
                )

                findNavController().popBackStack()

            } catch (e: Exception) {
                // Optionally show a Toast or log
            }
        }
    }
}