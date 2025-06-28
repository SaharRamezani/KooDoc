package com.example.kidzi.ui.kid

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
import com.example.kidzi.databinding.FragmentKidInfoNewBinding
import com.example.kidzi.di.db.PreferenceManager
import com.example.kidzi.di.db.dao.KidNameDao
import com.example.kidzi.di.db.models.KidNameModel
import com.example.kidzi.util.NumberFormatter
import dagger.hilt.android.AndroidEntryPoint
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog
import ir.hamsaa.persiandatepicker.api.PersianPickerDate
import ir.hamsaa.persiandatepicker.api.PersianPickerListener
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class KidInfoFragmentNew : Fragment() {
    @Inject
    lateinit var sharedPreferences: PreferenceManager

    @Inject
    lateinit var kidNameDao: KidNameDao

    var isNew = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentKidInfoNewBinding.inflate(inflater)

        isNew = KidInfoFragmentNewArgs.fromBundle(requireArguments()).new

        binding.btnBack.setOnClickListener {
            findNavController().navigate(KidInfoFragmentNewDirections.actionKidInfoNewFragmentToAccountFragment())
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
                        binding.btnGroup.text =
                            context?.let { it1 -> NumberFormatter.formatNumber(it1, date) }
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
                    val newId = kidNameDao.insert(
                        KidNameModel(
                            0,  // Let Room auto-generate the ID
                            binding.txtName.text.toString(),
                            binding.txtWeight.text.toString().toDouble(),
                            binding.txtHeight.text.toString().toDouble(),
                            binding.btnGroup.text.toString(),
                            sex
                        )
                    ).toInt()

                    sharedPreferences.updateCurrentKid(newId)
                    Log.i("Log1", "id of kid is: $newId")

                    findNavController().navigate(
                        KidInfoFragmentNewDirections.actionKidInfoNewFragmentToAccountFragment()
                    )
                }
            }
        }

        return binding.root
    }
}