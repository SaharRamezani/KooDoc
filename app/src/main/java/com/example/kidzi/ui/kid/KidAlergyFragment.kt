package com.example.kidzi.ui.kid

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.kidzi.databinding.FragmentKidAlergyBinding
import com.example.kidzi.di.db.dao.KidAlergyDao
import com.example.kidzi.di.db.models.KidAlergyModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class KidAlergyFragment : Fragment() {

    private var isNew = true
    private var kidId = 0
    private lateinit var binding: FragmentKidAlergyBinding

    @Inject lateinit var kidAlergyDao: KidAlergyDao

    private var honey = false
    private var peanut = false
    private var lac = false
    private var cow = false
    private var elexir = false

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
            loadAlergyData()
        }

        binding.checkHoney.setOnCheckedChangeListener { _, isChecked -> honey = isChecked }
        binding.checkLactose.setOnCheckedChangeListener { _, isChecked -> lac = isChecked }
        binding.checkProtein.setOnCheckedChangeListener { _, isChecked -> cow = isChecked }
        binding.checkPeanut.setOnCheckedChangeListener { _, isChecked -> peanut = isChecked }
        binding.checkElixir.setOnCheckedChangeListener { _, isChecked -> elexir = isChecked }

        binding.btnNext.setOnClickListener {
            saveAlergyAndNavigateNext()
        }

        binding.btnBack.setOnClickListener {
            saveAlergyAndNavigateBack()
        }
    }

    private fun loadAlergyData() {
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val kido = kidAlergyDao.getKidInfo(kidId) ?: return@launch

                binding.checkHoney.isChecked = kido.honey
                binding.checkLactose.isChecked = kido.lac
                binding.checkPeanut.isChecked = kido.peanut
                binding.checkProtein.isChecked = kido.cow
                binding.checkElixir.isChecked = kido.elixir

                honey = kido.honey
                lac = kido.lac
                peanut = kido.peanut
                cow = kido.cow
                elexir = kido.elixir

            } catch (e: Exception) {
                Log.e("AlergyLoad", "Failed to load allergy data", e)
                Toast.makeText(requireContext(), "خطا در بارگذاری اطلاعات", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveAlergyAndNavigateNext() {
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val model = KidAlergyModel(kidId, peanut, honey, lac, cow, elexir)
                if (isNew) {
                    kidAlergyDao.insert(model)
                } else {
                    kidAlergyDao.update(model)
                }

                findNavController().navigate(
                    KidAlergyFragmentDirections.actionKidAlergyFragmentToKidSocial(kidId, isNew)
                )

            } catch (e: Exception) {
                Log.e("AlergySave", "Failed to save allergy data", e)
                Toast.makeText(requireContext(), "خطا در ذخیره اطلاعات", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveAlergyAndNavigateBack() {
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val model = KidAlergyModel(kidId, peanut, honey, lac, cow, elexir)
                if (isNew) {
                    kidAlergyDao.insert(model)
                } else {
                    kidAlergyDao.update(model)
                }

                findNavController().navigate(
                    KidAlergyFragmentDirections.actionKidAlergyFragmentToKidDiseaseFragment(kidId, false)
                )

            } catch (e: Exception) {
                Log.e("AlergySave", "Failed to save allergy data", e)
                Toast.makeText(requireContext(), "خطا در ذخیره اطلاعات", Toast.LENGTH_SHORT).show()
            }
        }
    }
}