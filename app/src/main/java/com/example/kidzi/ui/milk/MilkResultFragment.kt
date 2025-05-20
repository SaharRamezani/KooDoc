package com.example.kidzi.ui.milk

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kidzi.R
import com.example.kidzi.databinding.FragmentMilkResultBinding
import com.example.kidzi.di.db.PreferenceManager
import com.example.kidzi.di.db.dao.KidAlergyDao
import com.example.kidzi.di.db.dao.KidNameDao
import com.example.kidzi.di.helpers.PersianDateHelper
import com.example.kidzi.ui.milk.adapters.MilkAdapter
import com.example.kidzi.ui.vaccine.adapters.VaccineInfoAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MilkResultFragment : Fragment() {

    lateinit var adapter: MilkAdapter

    @Inject lateinit var preferenceManager: PreferenceManager
    @Inject lateinit var kidNameDao: KidNameDao
    @Inject lateinit var kidAlergyDao: KidAlergyDao

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding = FragmentMilkResultBinding.inflate(inflater, container, false)
        val args = MilkResultFragmentArgs.fromBundle(requireArguments())

        val milkList = generateMilkList(args.type, args.age, args.cow, args.lac)

        setupRecycler(binding, milkList)
        binding.btnBack.setOnClickListener { findNavController().popBackStack() }

        binding.txtTitle.text = when (args.type) {
            1 -> getString(R.string.all_milk)
            2 -> getString(R.string.my_kid_milk)
            else -> getString(R.string.my_kid_allergy_milk)
        }

        return binding.root
    }

    private fun setupRecycler(binding: FragmentMilkResultBinding, milkList: List<MilkModel>) {
        try {
            adapter = MilkAdapter(milkList)
            binding.recycler.layoutManager = LinearLayoutManager(requireContext())
            binding.recycler.adapter = adapter
        } catch (e: Exception) {
            Toast.makeText(requireContext(), e.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    private fun generateMilkList(type: Int, inputAge: Int?, inputCow: Boolean?, inputLac: Boolean?): List<MilkModel> {
        val list = mutableListOf<MilkModel>()

        val englishName = resources.getStringArray(R.array.milk_name_en)
        val persianName = resources.getStringArray(R.array.milk_name_fa)
        val monthStart = resources.getStringArray(R.array.milk_start)
        val monthFinish = resources.getStringArray(R.array.milk_finish)
        val milkType = resources.getStringArray(R.array.milk_type)
        val milkUse = resources.getStringArray(R.array.milk_use)
        val milkLac = resources.getStringArray(R.array.milk_lac)

        if (type == 1) {
            for (i in englishName.indices) {
                list.add(createModel(i, persianName, englishName, monthStart, monthFinish, milkLac, milkUse, milkType))
            }
            return list
        }

        var cow = inputCow ?: false
        var lac = inputLac ?: false
        val age = if (type == 2) {
            val kidId = preferenceManager.getCurrentKid()
            val kid = kidNameDao.getKidInfo(kidId)
            val alergy = kidAlergyDao.getKidInfo(kidId)

            cow = alergy.cow ?: false
            lac = alergy.lac ?: false

            PersianDateHelper.getAgeInMonths(kid.birthDate)
        } else inputAge ?: 0

        for (i in englishName.indices) {
            val start = monthStart[i].toInt()
            val end = monthFinish[i].toInt()
            val lactoseLevel = milkLac[i].toInt()
            val typeStr = milkType[i]

            if (isMilkSuitable(age, start, end, lactoseLevel, typeStr, cow, lac, type)) {
                list.add(createModel(i, persianName, englishName, monthStart, monthFinish, milkLac, milkUse, milkType))
            }
        }

        return list
    }

    private fun isMilkSuitable(
        age: Int, start: Int, end: Int,
        lactoseLevel: Int, typeStr: String,
        cow: Boolean, lac: Boolean, type: Int
    ): Boolean {
        return age in start..end &&
                !(lac && lactoseLevel >= 3) &&
                !(cow && typeStr.contains(getString(R.string.cow))) &&
                !(type == 3 && !typeStr.contains(getString(R.string.diet)))
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
