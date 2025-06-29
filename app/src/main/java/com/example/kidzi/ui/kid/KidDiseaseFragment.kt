package com.example.kidzi.ui.kid

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.kidzi.R
import com.example.kidzi.databinding.FragmentKidDiseaseBinding
import com.example.kidzi.di.db.PreferenceManager
import com.example.kidzi.di.db.dao.KidDiseaseDao
import com.example.kidzi.di.db.models.KidDiseaseModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class KidDiseaseFragment : Fragment() {

    private lateinit var binding: FragmentKidDiseaseBinding
    private var kidId = 0
    private var isNew = true

    private var diabetB = false
    private var fawB = false
    private var metaB = false
    private var asmB = false
    private var fiberB = false
    private var selB = false

    @Inject lateinit var kidDiseaseDao: KidDiseaseDao
    @Inject
    lateinit var sharedPreferences: PreferenceManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.i("KidDiseaseFragment", "Received kidId = $kidId, isNew = $isNew")

        binding = FragmentKidDiseaseBinding.inflate(inflater, container, false)

        val args = KidDiseaseFragmentArgs.fromBundle(requireArguments())
        kidId = args.kidId
        isNew = args.new

        Log.i("Log1", "kidId is: $kidId")

        if (!isNew) loadKidData()

        setUpCheckListeners()
        setUpButtonListeners()

        return binding.root
    }

    private fun loadKidData() {
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val kido = kidDiseaseDao.getKidInfo(kidId) ?: return@launch

                // Set states for hardcoded checkboxes
                binding.checkAsm.isChecked = kido.asm
                binding.checkFawism.isChecked = kido.faw
                binding.checkFibrozm.isChecked = kido.fibr
                binding.checkMetabolism.isChecked = kido.meta
                binding.checkSeliac.isChecked = kido.seli
                binding.checkDiabetes.isChecked = kido.diabetes

                diabetB = kido.diabetes
                fawB = kido.faw
                metaB = kido.meta
                asmB = kido.asm
                fiberB = kido.fibr
                selB = kido.seli

                val hardcodedCheckBoxes = listOf(
                    binding.checkAsm,
                    binding.checkFawism,
                    binding.checkFibrozm,
                    binding.checkMetabolism,
                    binding.checkSeliac,
                    binding.checkDiabetes
                )
                val hardcodedTexts = hardcodedCheckBoxes.map { it.text.toString().trim() }

                val diseases = kido.other.split(",").map { it.trim() }.filter { it.isNotEmpty() }

                diseases.forEach { disease ->
                    if (hardcodedTexts.contains(disease)) {
                        hardcodedCheckBoxes.find { it.text.toString().trim() == disease }?.isChecked = true
                    } else {
                        val checkBox = CheckBox(requireContext()).apply {
                            text = disease
                            isChecked = true
                            id = View.generateViewId()
                            layoutParams = ViewGroup.MarginLayoutParams(
                                ViewGroup.LayoutParams.WRAP_CONTENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT
                            ).apply {
                                topMargin = resources.getDimensionPixelSize(R.dimen.default_checkbox_margin_top)
                            }
                        }
                        binding.dynamicCheckContainer.addView(checkBox)
                    }
                }

            } catch (e: Exception) {
                Log.e("Log1", "Error loading kid data: $e")
            }
        }
    }

    private fun setUpCheckListeners() {
        binding.checkAsm.setOnCheckedChangeListener { _, isChecked -> asmB = isChecked }
        binding.checkFawism.setOnCheckedChangeListener { _, isChecked -> fawB = isChecked }
        binding.checkSeliac.setOnCheckedChangeListener { _, isChecked -> selB = isChecked }
        binding.checkFibrozm.setOnCheckedChangeListener { _, isChecked -> fiberB = isChecked }
        binding.checkDiabetes.setOnCheckedChangeListener { _, isChecked -> diabetB = isChecked }
        binding.checkMetabolism.setOnCheckedChangeListener { _, isChecked -> metaB = isChecked }
    }

    private fun setUpButtonListeners() {
        binding.btnAdd.setOnClickListener {
            val name = binding.txtName.text?.toString()?.trim()
            if (!name.isNullOrEmpty()) {
                for (i in 0 until binding.dynamicCheckContainer.childCount) {
                    val child = binding.dynamicCheckContainer.getChildAt(i)
                    if (child is CheckBox && child.text.toString() == name) return@setOnClickListener
                }

                val checkBox = CheckBox(requireContext()).apply {
                    text = name
                    isChecked = true
                    id = View.generateViewId()
                    layoutParams = ViewGroup.MarginLayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    ).apply {
                        topMargin = resources.getDimensionPixelSize(R.dimen.default_checkbox_margin_top)
                    }
                }

                binding.dynamicCheckContainer.addView(checkBox)
                binding.txtName.setText("")
            }
        }

        binding.btnBack.setOnClickListener {
            findNavController().navigate(
                KidDiseaseFragmentDirections.actionKidDiseaseFragmentToKidInfoFragment(kidId, false)
            )
        }

        binding.btnNext.setOnClickListener {
            if (kidId > 0) {
                saveDisease()
            } else {
                Toast.makeText(requireContext(), getString(R.string.toast_invalid_kid_id), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveDisease() {
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val extraDiseases = StringBuilder()

                val hardcodedIds = setOf(
                    binding.checkAsm.id,
                    binding.checkFawism.id,
                    binding.checkFibrozm.id,
                    binding.checkMetabolism.id,
                    binding.checkSeliac.id,
                    binding.checkDiabetes.id
                )

                for (i in 0 until binding.dynamicCheckContainer.childCount) {
                    val view = binding.dynamicCheckContainer.getChildAt(i)
                    if (view is CheckBox && view.isChecked && view.id !in hardcodedIds) {
                        if (extraDiseases.isNotEmpty()) extraDiseases.append(", ")
                        extraDiseases.append(view.text.toString())
                    }
                }

                val model = KidDiseaseModel(
                    kidId, diabetB, fawB, metaB, asmB, fiberB, selB,
                    extraDiseases.toString()
                )

                if (isNew) {
                    kidDiseaseDao.insert(model)
                } else {
                    kidDiseaseDao.update(model)
                }

                sharedPreferences.updateCurrentKid(kidId)

                findNavController().navigate(
                    KidDiseaseFragmentDirections.actionKidDiseaseFragmentToKidAlergyFragment(kidId, isNew)
                )

            } catch (e: Exception) {
                Log.e("Log1", "Save error: $e")
                Toast.makeText(requireContext(), getString(R.string.error_save), Toast.LENGTH_SHORT).show()
            }
        }
    }
}