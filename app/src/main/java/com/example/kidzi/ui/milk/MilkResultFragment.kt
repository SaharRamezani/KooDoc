package com.example.kidzi.ui.milk

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
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
import com.example.kidzi.ui.milk.adapters.MilkAdapter
import com.example.kidzi.util.getAgeInMonths
import com.example.kidzi.util.parsePersianDateToGregorianMillis
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.*
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

        binding.btnBack.setOnClickListener { findNavController().popBackStack() }

        binding.txtTitle.text = when (args.type) {
            6 -> getString(R.string.all_milks)
            else -> getString(R.string.my_kid_milk)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val kid = kidNameDao.getKidInfo(preferenceManager.getCurrentKid())
                val age = getAgeInMonths(kid.birthDate)

                val milkList = generateMilkList(args.type, age, args.cow, args.lac)
                setupRecycler(binding, milkList)

            } catch (e: Exception) {
                Log.e("MilkResultFragment", "Error loading milk list", e)
                Toast.makeText(requireContext(), getString(R.string.toast_load_error), Toast.LENGTH_SHORT).show()
                setupRecycler(binding, emptyList())
            }
        }

        return binding.root
    }

    private fun setupRecycler(binding: FragmentMilkResultBinding, milkList: List<MilkModel>) {
        try {
            val savedSet = preferenceManager.getSelectedMilks()

            milkList.forEach { it.isSelected = savedSet.contains(it.englishName) }

            adapter = MilkAdapter(
                milkList.toMutableList(),
                requireContext(),
                preferenceManager,
                removeOnUncheck = false
            )

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
                list.add(
                    MilkModel(
                        persianName[i],
                        englishName[i],
                        start,
                        end,
                        lactoseLevel,
                        milkUse,
                        typeStr
                    )
                )
            }
        }

        return list
    }

    private fun isMilkSuitable(
        age: Int, start: Int, end: Int,
        lactoseLevel: Int, typeStr: String,
        cow: Boolean, lac: Boolean, type: Int, milkUse: String
    ): Boolean {
        Log.d("MilkResult (end): ", "Type=$type, Age=$age, Lac=$lac, Cow=$cow")
        return age in start..end && !(lac && lactoseLevel >= 3) &&
                !(cow && typeStr.contains(getString(R.string.cow_protein))) &&
                !(cow && typeStr.contains(getString(R.string.milk_protein))) &&
                ((type == 5) ||
                        (type == 3 && milkUse.contains(getString(R.string.diet))) ||
                        (type == 4 && milkUse.contains(getString(R.string.regular)))) ||
                (type == 6)
    }
}
