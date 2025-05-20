package com.example.kidzi.ui.milk

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.kidzi.databinding.FragmentMilkManualBinding
import com.example.kidzi.di.db.PreferenceManager
import com.example.kidzi.di.db.dao.KidAlergyDao
import com.example.kidzi.di.db.dao.KidNameDao
import com.example.kidzi.di.db.models.KidAlergyModel
import dagger.hilt.android.AndroidEntryPoint
import ir.hamsaa.persiandatepicker.util.PersianCalendar
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class MilkManualFragment : Fragment() {

    @Inject lateinit var kidAlergyDao: KidAlergyDao
    @Inject lateinit var preferenceManager: PreferenceManager
    @Inject lateinit var kidNameDao: KidNameDao

    private fun parsePersianDateToGregorianMillis(persianDate: String): Long {
        return try {
            val parts = persianDate.split("/")
            val (year, month, day) = parts.map { it.toInt() }
            val persian = PersianCalendar()
            persian.setPersianDate(year, month - 1, day)  // FIX: month - 1
            persian.timeInMillis
        } catch (_: Exception) {
            0L
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = com.example.kidzi.databinding.FragmentMilkManualBinding.inflate(inflater, container, false)
        val kidId = preferenceManager.getCurrentKid()

        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val existing = kidAlergyDao.getKidInfo(kidId)
                binding.radioLacYes.isChecked = existing?.lac == true
                binding.radioLacNo.isChecked = existing?.lac == false
                binding.radioCaringYes.isChecked = existing?.cow == true
                binding.radioCaringNo.isChecked = existing?.cow == false
            } catch (_: Exception) {}

            try {
                val kid = kidNameDao.getKidInfo(kidId)
                val dobMillis = parsePersianDateToGregorianMillis(kid.birthDate)
                val dob = Calendar.getInstance().apply { timeInMillis = dobMillis }
                val now = Calendar.getInstance()

                val yearDiff = now.get(Calendar.YEAR) - dob.get(Calendar.YEAR)
                val monthDiff = now.get(Calendar.MONTH) - dob.get(Calendar.MONTH)
                val ageInMonths = yearDiff * 12 + monthDiff

                binding.txtAge.setText(ageInMonths.toString())
            } catch (_: Exception) {
                binding.txtAge.setText("")
            }
        }

        binding.btnBack.setOnClickListener { findNavController().popBackStack() }

        binding.btnRegister.setOnClickListener {
            val ageText = binding.txtAge.text.toString()
            val isLacSelected = binding.radioLacNo.isChecked || binding.radioLacYes.isChecked
            val isCowSelected = binding.radioCaringNo.isChecked || binding.radioCaringYes.isChecked
            val isDietChecked = binding.radioDietYes.isChecked

            if (ageText.isEmpty() || ageText.contains(".")) {
                Toast.makeText(requireContext(), "سن باید بصورت عدد صحیح وارد شود", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (!isLacSelected) {
                Toast.makeText(requireContext(), "حساسیت به لاکتوز باید مشخص گردد.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (!isCowSelected) {
                Toast.makeText(requireContext(), "حساسیت به شیر گاو باید مشخص گردد.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val age = ageText.toInt()
            val lac = binding.radioLacYes.isChecked
            val cow = binding.radioCaringYes.isChecked
            val kidId = preferenceManager.getCurrentKid()

            viewLifecycleOwner.lifecycleScope.launch {
                try {
                    val existing = kidAlergyDao.getKidInfo(kidId)
                    if (existing != null) {
                        val updated = existing.copy(lac = lac, cow = cow)
                        kidAlergyDao.update(updated)
                    } else {
                        kidAlergyDao.insert(
                            KidAlergyModel(
                                kidId = kidId,
                                peanut = false,
                                honey = false,
                                lac = lac,
                                cow = cow,
                                alcohol = false,
                            )
                        )
                    }

                    val type = if (isDietChecked) 3 else 4
                    findNavController().navigate(
                        MilkManualFragmentDirections.actionMilkManualFragmentToMilkResultFragment(
                            type, age, lac, cow
                        )
                    )
                } catch (e: Exception) {
                    Toast.makeText(requireContext(), "خطا در ذخیره اطلاعات", Toast.LENGTH_SHORT).show()
                }
            }
        }

        return binding.root
    }
}
