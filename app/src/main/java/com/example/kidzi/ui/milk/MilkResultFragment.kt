package com.example.kidzi.ui.milk

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import saman.zamani.persiandate.PersianDate
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kidzi.R
import com.example.kidzi.databinding.FragmentMilkResultBinding
import com.example.kidzi.di.db.PreferenceManager
import com.example.kidzi.di.db.dao.KidAlergyDao
import com.example.kidzi.di.db.dao.KidNameDao
import com.example.kidzi.di.helpers.PersianDateHelper.Companion.toEnglishDigits
import com.example.kidzi.ui.milk.adapters.MilkAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@AndroidEntryPoint
class MilkResultFragment : Fragment() {

    lateinit var adapter: MilkAdapter

    @Inject lateinit var preferenceManager: PreferenceManager
    @Inject lateinit var kidNameDao: KidNameDao
    @Inject lateinit var kidAlergyDao: KidAlergyDao

    fun getAgeInMonthsEn(persianDate: String): Int {
        val parts = persianDate.toEnglishDigits().split("/")
        val (year, month, day) = parts.map { it.toInt() }

        val dobPersian = PersianDate().apply {
            setShYear(year)
            setShMonth(month)
            setShDay(day)
        }

        val dobGregorian = Calendar.getInstance().apply {
            timeInMillis = dobPersian.toDate().time
        }

        val now = Calendar.getInstance()

        val yearDiff = now.get(Calendar.YEAR) - dobGregorian.get(Calendar.YEAR)
        val monthDiff = now.get(Calendar.MONTH) - dobGregorian.get(Calendar.MONTH)
        return yearDiff * 12 + monthDiff
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding = FragmentMilkResultBinding.inflate(inflater, container, false)
        val args = MilkResultFragmentArgs.fromBundle(requireArguments())

        binding.btnBack.setOnClickListener { findNavController().popBackStack() }

        binding.txtTitle.text = when (args.type) {
            6 -> getString(R.string.all_milks)
            else -> getString(R.string.my_kid_milk)
        }

        // Fetch kid info and generate list inside coroutine
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val kid = kidNameDao.getKidInfo(preferenceManager.getCurrentKid())
                val age = kid.birthDate.let { getAgeInMonthsEn(it) }

                val milkList = generateMilkList(args.type, age, args.cow, args.lac)
                setupRecycler(binding, milkList)

            } catch (e: Exception) {
                Log.e("MilkResultFragment", "Error loading milk list", e)
                Toast.makeText(requireContext(), "خطا در بارگذاری لیست", Toast.LENGTH_SHORT).show()
                setupRecycler(binding, emptyList())
            }
        }

        return binding.root
    }

    private fun setupRecycler(binding: FragmentMilkResultBinding, milkList: List<MilkModel>) {
        try {
            // Step 1: Get previously selected items
            val savedSet = preferenceManager.getSelectedMilks()

            // Step 2: Update each item based on saved selection
            milkList.forEach { it.isSelected = savedSet.contains(it.englishName) }

            // Step 3: Pass preferenceManager and context to adapter
            adapter = MilkAdapter(milkList.toMutableList(), requireContext(), preferenceManager)

            binding.recycler.layoutManager = LinearLayoutManager(requireContext())
            binding.recycler.adapter = adapter
        } catch (e: Exception) {
            Toast.makeText(requireContext(), e.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    private fun generateMilkList(type: Int, age: Int, inputCow: Boolean?, inputLac: Boolean?): List<MilkModel> {
        val list = mutableListOf<MilkModel>()

        val englishName = resources.getStringArray(R.array.milk_name_en)
        val persianName = resources.getStringArray(R.array.milk_name_fa)
        val monthStart = resources.getStringArray(R.array.milk_start)
        val monthFinish = resources.getStringArray(R.array.milk_finish)
        val milkType = resources.getStringArray(R.array.milk_type)
        val milkUseArr = resources.getStringArray(R.array.milk_use)
        val milkLac = resources.getStringArray(R.array.milk_lac)

        val cow = inputCow ?: false
        val lac = inputLac ?: false

        for (i in englishName.indices) {
            val start = monthStart[i].toInt()
            val end = monthFinish[i].toInt()
            val lactoseLevel = milkLac[i].toInt()
            val typeStr = milkType[i]
            val milkUse = milkUseArr[i]

            if (isMilkSuitable(age, start, end, lactoseLevel, typeStr, cow, lac, type, milkUse)) {
                list.add(createModel(i, persianName, englishName, monthStart, monthFinish, milkLac, milkUseArr, milkType))
            }
        }

        return list
    }

    private fun isMilkSuitable(
        age: Int, start: Int, end: Int,
        lactoseLevel: Int, typeStr: String,
        cow: Boolean, lac: Boolean, type: Int, milkUse: String
    ): Boolean {
        return age in start..end &&
                !(lac && lactoseLevel >= 3) &&
                !(cow && typeStr.contains(getString(R.string.cow_protein))) &&
                !(cow && typeStr.contains(getString(R.string.milk_protein))) &&
                ((type == 5) ||
                        (type == 3 && milkUse.contains(getString(R.string.diet))) ||
                        (type == 4 && milkUse.contains(getString(R.string.regular)))) ||
                (type == 6)
    }

    private fun createModel(
        i: Int,
        fa: Array<String>, en: Array<String>,
        start: Array<String>, end: Array<String>,
        lac: Array<String>, use: Array<String>, type: Array<String>
    ) = MilkModel(
        fa[i],
        en[i],
        start[i].toInt(),
        end[i].toInt(),
        lac[i].toInt(),
        use[i],
        type[i]
    )
}
