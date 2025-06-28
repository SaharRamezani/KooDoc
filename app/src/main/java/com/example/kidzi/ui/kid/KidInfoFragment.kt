package com.example.kidzi.ui.kid

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.kidzi.databinding.FragmentKidInfoBinding
import com.example.kidzi.di.db.PreferenceManager
import com.example.kidzi.di.db.dao.KidNameDao
import com.example.kidzi.di.db.models.KidNameModel
import dagger.hilt.android.AndroidEntryPoint
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog
import ir.hamsaa.persiandatepicker.api.PersianPickerDate
import ir.hamsaa.persiandatepicker.api.PersianPickerListener
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class KidInfoFragment : Fragment() {
    @Inject
    lateinit var sharedPreferences: PreferenceManager

    @Inject
    lateinit var kidNameDao: KidNameDao

    var isNew = false

    private fun convertToPersianDigits(input: String): String {
        val persianDigits = listOf('۰','۱','۲','۳','۴','۵','۶','۷','۸','۹')
        return input.map { if (it.isDigit()) persianDigits[it.digitToInt()] else it }.joinToString("")
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentKidInfoBinding.inflate(inflater)
        val id = KidInfoShowFragmentArgs.fromBundle(requireArguments()).kidId
        isNew = KidInfoShowFragmentArgs.fromBundle(requireArguments()).new

        if (!isNew) {
            viewLifecycleOwner.lifecycleScope.launch {
                try {
                    val kidInfo = kidNameDao.getKidInfo(id)
                    binding.txtName.setText(kidInfo.name)
                    binding.txtHeight.setText(kidInfo.height.toString())
                    binding.txtWeight.setText(kidInfo.weight.toString())
                    binding.btnGroup.setText(kidInfo.birthDate)

                    if (kidInfo.type == 1) {
                        binding.radioWorkingYes.isChecked = true
                    } else {
                        binding.radioWorkingNo.isChecked = true
                    }

                } catch (e: Exception) {
                    Log.e("KidInfoShowFragment", "Error loading kid info", e)
                    Toast.makeText(requireContext(), "خطا در بارگذاری اطلاعات", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.btnBack.setOnClickListener {
            findNavController().navigate(KidInfoFragmentDirections.actionKidInfoFragmentToKidIntroFragment())
        }

        binding.btnGroup.setOnClickListener {
            val picker = PersianDatePickerDialog(requireContext())
                .setPositiveButtonString("تائید")
                .setNegativeButton("بیخیال")
                .setTodayButton("امروز")
                .setTodayButtonVisible(true)
                .setMinYear(1380)
                .setInitDate(1404, 1, 1)
                .setActionTextColor(Color.GRAY)
                .setTitleType(PersianDatePickerDialog.WEEKDAY_DAY_MONTH_YEAR)
                .setShowInBottomSheet(true)
                .setListener(object : PersianPickerListener {
                    override fun onDateSelected(persianPickerDate: PersianPickerDate) {
                        val date = "${persianPickerDate.persianYear}/${persianPickerDate.persianMonth}/${persianPickerDate.persianDay}"
                        binding.btnGroup.text = convertToPersianDigits(date)
                    }
                    override fun onDismissed() {
                    }
                })
            picker.show()
        }

        binding.btnNext.setOnClickListener {
            if (binding.txtName.text.isNullOrEmpty()) {
                Toast.makeText(requireContext(), "نام نوزاد را وارد کنید", Toast.LENGTH_SHORT).show()
            } else if (binding.txtHeight.text.isNullOrEmpty()) {
                Toast.makeText(requireContext(), "قد نوزاد را وارد کنید", Toast.LENGTH_SHORT).show()
            } else if (binding.txtWeight.text.isNullOrEmpty()) {
                Toast.makeText(requireContext(), "وزن نوزاد را وارد کنید", Toast.LENGTH_SHORT).show()
            } else if (!binding.btnGroup.text.toString().contains("/")) {
                Toast.makeText(requireContext(), "تاریخ تولد نوزاد را وارد کنید", Toast.LENGTH_SHORT).show()
            } else {
                val sex = if (binding.radioWorkingYes.isChecked) 1 else 2

                viewLifecycleOwner.lifecycleScope.launch {
                    if (isNew) {
                        val newId = kidNameDao.insert(
                            KidNameModel(
                                0,
                                binding.txtName.text.toString(),
                                binding.txtWeight.text.toString().toDouble(),
                                binding.txtHeight.text.toString().toDouble(),
                                binding.btnGroup.text.toString(),
                                sex
                            )
                        ).toInt()
                        sharedPreferences.updateCurrentKid(newId)
                        findNavController().navigate(
                            KidInfoFragmentDirections.actionKidInfoFragmentToKidDiseaseFragment(newId, isNew)
                        )
                    } else {
                        kidNameDao.update(
                            KidNameModel(
                                id,
                                binding.txtName.text.toString(),
                                binding.txtWeight.text.toString().toDouble(),
                                binding.txtHeight.text.toString().toDouble(),
                                binding.btnGroup.text.toString(),
                                sex
                            )
                        )
                        findNavController().navigate(
                            KidInfoFragmentDirections.actionKidInfoFragmentToKidDiseaseFragment(id, isNew)
                        )
                    }
                }
            }
        }

        return binding.root
    }
}