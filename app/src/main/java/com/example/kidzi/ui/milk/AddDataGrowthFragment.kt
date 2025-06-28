package com.example.kidzi.ui.milk

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.kidzi.R
import com.example.kidzi.databinding.FragmentAddDataGrowthBinding
import com.example.kidzi.di.db.PreferenceManager
import com.example.kidzi.di.db.dao.GrowthDataDao
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import com.example.kidzi.di.db.models.KidGrowthModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AddDataGrowthFragment : Fragment() {

    private var _binding: FragmentAddDataGrowthBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var growthDataDao: GrowthDataDao

    @Inject
    lateinit var preferenceManager: PreferenceManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddDataGrowthBinding.inflate(inflater, container, false)

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        val currentKidId = preferenceManager.getCurrentKid()
        if (currentKidId == -1) {
            Toast.makeText(requireContext(), getString(R.string.error_choose_child), Toast.LENGTH_SHORT).show()
            findNavController().navigateUp()
        } else {
            // Optionally show kid's name somewhere if needed
        }

        binding.btnRegister.setOnClickListener {
            saveGrowthData(currentKidId)
        }

        return binding.root
    }

    private fun saveGrowthData(kidId: Int) {
        val ageText = binding.txtAge.text.toString()
        val heightText = binding.txtHeight.text.toString()
        val weightText = binding.txtWeight.text.toString()
        val headText = binding.txtHead.text.toString()

        if (ageText.isBlank() || heightText.isBlank() || weightText.isBlank() || headText.isBlank()) {
            Toast.makeText(requireContext(), getString(R.string.error_fill_all_fields), Toast.LENGTH_SHORT).show()
            return
        }

        val age = ageText.toIntOrNull()
        val height = heightText.toDoubleOrNull()
        val weight = weightText.toDoubleOrNull()
        val head = headText.toDoubleOrNull()

        if (age == null || height == null || weight == null || head == null) {
            Toast.makeText(requireContext(), getString(R.string.error_info_not_valid), Toast.LENGTH_SHORT).show()
            return
        }

        val growthData = KidGrowthModel(
            kidId = kidId,
            ageWeeks = age,
            height = height,
            weight = weight,
            headCircumference = head
        )

        lifecycleScope.launch(Dispatchers.IO) {
            try {
                growthDataDao.insert(growthData)
                launch(Dispatchers.Main) {
                    Toast.makeText(requireContext(), getString(R.string.success_save), Toast.LENGTH_SHORT).show()
                    findNavController().navigateUp()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                launch(Dispatchers.Main) {
                    Toast.makeText(requireContext(), getString(R.string.error_save), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}