package com.example.kidzi.ui.kid

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.kidzi.R
import com.example.kidzi.databinding.FragmentKidDiseaseBinding
import com.example.kidzi.di.db.dao.KidDiseaseDao
import com.example.kidzi.di.db.models.KidDiseaseModel
import dagger.hilt.android.AndroidEntryPoint
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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
        try {
            kidDiseaseDao.getKidInfo(kidId)?.let { kido ->
                binding.checkAsm.isChecked = kido.asm
                binding.checkFawism.isChecked = kido.faw
                binding.checkFibrozm.isChecked = kido.fibr
                binding.checkMetabolism.isChecked = kido.meta
                binding.checkSeliac.isChecked = kido.seli
                binding.checkDiabetes.isChecked = kido.diabetes
                binding.txtDis.text = kido.other
            }
        } catch (e: Exception) {
            Log.e("Log1", "Error loading kid data: $e")
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
            if (kidId > 0 && saveDisease()) {
                findNavController().navigate(
                    KidDiseaseFragmentDirections.actionKidDiseaseFragmentToKidAlergyFragment(kidId, isNew)
                )
            } else {
                Toast.makeText(requireContext(), "کودک یافت نشد!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveDisease(): Boolean {
        return try {
            val extraDiseases = StringBuilder(binding.txtDis.text.toString().trim())

            for (i in 0 until binding.dynamicCheckContainer.childCount) {
                val view = binding.dynamicCheckContainer.getChildAt(i)
                if (view is CheckBox && view.isChecked) {
                    if (extraDiseases.isNotEmpty()) extraDiseases.append(", ")
                    extraDiseases.append(view.text.toString())
                }
            }

            val model = KidDiseaseModel(
                kidId, diabetB, fawB, metaB, asmB, fiberB, selB,
                extraDiseases.toString()
            )
            if (isNew) kidDiseaseDao.insert(model) else kidDiseaseDao.update(model)
            true
        } catch (e: Exception) {
            Log.e("Log1", "Save error: $e")
            false
        }
    }
}