package com.example.kidzi.ui.milk

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kidzi.databinding.FragmentMyKidMilkBinding
import com.example.kidzi.di.db.PreferenceManager
import com.example.kidzi.ui.milk.adapters.MilkAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MyKidMilkFragment : Fragment() {

    private lateinit var adapter: MilkAdapter
    private lateinit var binding: FragmentMyKidMilkBinding

    @Inject
    lateinit var preferenceManager: PreferenceManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentMyKidMilkBinding.inflate(inflater, container, false)

        val selectedMilkNames = preferenceManager.getSelectedMilks()
        val fullList = generateFullMilkList()
        val selectedList = fullList
            .filter { selectedMilkNames.contains(it.englishName) }
            .onEach { it.isSelected = true }
            .toMutableList()

        if (selectedList.isEmpty()) {
            binding.txtEmpty.visibility = View.VISIBLE
            binding.recycler.visibility = View.GONE
        } else {
            binding.txtEmpty.visibility = View.GONE
            binding.recycler.visibility = View.VISIBLE

            adapter = MilkAdapter(
                selectedList,
                requireContext(),
                preferenceManager,
                onItemRemoved = { newSize ->
                    if (newSize == 0) {
                        binding.txtEmpty.visibility = View.VISIBLE
                        binding.recycler.visibility = View.GONE
                    }
                },
                removeOnUncheck = true
            )

            binding.recycler.layoutManager = LinearLayoutManager(requireContext())
            binding.recycler.adapter = adapter
        }

        binding.btnBack.setOnClickListener { findNavController().popBackStack() }

        return binding.root
    }

    private fun generateFullMilkList(): List<MilkModel> {
        val list = mutableListOf<MilkModel>()

        val englishName = resources.getStringArray(com.example.kidzi.R.array.milk_name_en)
        val persianName = resources.getStringArray(com.example.kidzi.R.array.milk_name_fa)
        val monthStart = resources.getStringArray(com.example.kidzi.R.array.milk_start)
        val monthFinish = resources.getStringArray(com.example.kidzi.R.array.milk_finish)
        val milkType = resources.getStringArray(com.example.kidzi.R.array.milk_type)
        val milkUseArr = resources.getStringArray(com.example.kidzi.R.array.milk_use)
        val milkLac = resources.getStringArray(com.example.kidzi.R.array.milk_lac)

        for (i in englishName.indices) {
            list.add(
                MilkModel(
                    persianName[i],
                    englishName[i],
                    monthStart[i].toInt(),
                    monthFinish[i].toInt(),
                    milkLac[i].toInt(),
                    milkUseArr[i],
                    milkType[i]
                )
            )
        }

        return list
    }
}