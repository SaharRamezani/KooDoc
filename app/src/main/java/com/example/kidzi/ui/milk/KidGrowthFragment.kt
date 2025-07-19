package com.example.kidzi.ui.milk

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kidzi.R
import com.example.kidzi.databinding.FragmentKidGrowthBinding
import com.example.kidzi.di.db.PreferenceManager
import com.example.kidzi.di.db.dao.GrowthDataDao
import com.example.kidzi.di.db.dao.KidNameDao
import com.example.kidzi.di.db.models.KidGrowthModel
import com.example.kidzi.ui.milk.adapters.GrowthChartAdapter
import com.example.kidzi.util.GrowthChartHelper
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineDataSet
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class KidGrowthFragment : Fragment() {

    @Inject lateinit var preferenceManager: PreferenceManager
    @Inject lateinit var kidNameDao: KidNameDao
    @Inject lateinit var growthDataDao: GrowthDataDao
    private lateinit var adapter: GrowthChartAdapter
    private lateinit var binding: FragmentKidGrowthBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentKidGrowthBinding.inflate(inflater, container, false)

        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.growthMainFragment)
        }

        binding.btnAddData.setOnClickListener {
            findNavController().navigate(R.id.action_kidGrowthFragment_to_addDataGrowthFragment)
        }

        adapter = GrowthChartAdapter(mutableListOf()) { itemToDelete ->
            lifecycleScope.launch(Dispatchers.IO) {
                growthDataDao.deleteByAgeAndKidId(itemToDelete.age, preferenceManager.getCurrentKid())
                val updatedData = growthDataDao.getAllGrowthDataForKid(preferenceManager.getCurrentKid())
                val updatedEntries = convertSavedDataToEntries(updatedData)

                launch(Dispatchers.Main) {
                    adapter.removeItem(itemToDelete)
                    refreshSavedDataOnChart(updatedEntries)
                    Toast.makeText(requireContext(), getString(R.string.success_delete), Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.recycler.adapter = adapter
        binding.recycler.layoutManager = LinearLayoutManager(requireContext())

        val kidId = preferenceManager.getCurrentKid()
        if (kidId != -1) {
            lifecycleScope.launch(Dispatchers.IO) {
                val kidInfo = kidNameDao.getKidInfo(kidId)
                val sex = kidInfo.type
                val (m, p3, p97) = GrowthChartHelper.readGrowthChartFromCSV(requireContext(), sex)

                launch(Dispatchers.Main) {
                    binding.txtKidName.text = kidInfo.name
                    GrowthChartHelper.setupMultiLineChart(binding.lineChart, requireContext(), m, p3, p97)
                }

                val savedData = growthDataDao.getAllGrowthDataForKid(kidId)
                val savedEntries = convertSavedDataToEntries(savedData)

                launch(Dispatchers.Main) {
                    addSavedDataToChart(savedEntries)
                }

                val savedDataForRecycler = savedData.map {
                    GrowthModel(
                        age = it.ageWeeks,
                        startHeight = it.height,
                        endHeight = it.height,
                        startWeight = it.weight,
                        endWeight = it.weight,
                        startHead = it.headCircumference,
                        endHead = it.headCircumference
                    )
                }

                launch(Dispatchers.Main) {
                    adapter.updateData(savedDataForRecycler)
                }
            }
        }

        return binding.root
    }

    private fun refreshSavedDataOnChart(savedEntries: List<Entry>) {
        val chart = binding.lineChart
        val toRemove = chart.data.dataSets.find { it.label == getString(R.string.saved_growth) }
        if (toRemove != null) {
            chart.data.removeDataSet(toRemove)
        }

        val updatedDataSet = LineDataSet(savedEntries, getString(R.string.saved_growth)).apply {
            color = Color.MAGENTA
            lineWidth = 2.5f
            setCircleColor(Color.MAGENTA)
            circleRadius = 4f
            setDrawCircleHole(false)
            setDrawValues(false)
        }

        chart.data.addDataSet(updatedDataSet)
        chart.invalidate()
    }

    private fun convertSavedDataToEntries(data: List<KidGrowthModel>): List<Entry> {
        return data.sortedBy { it.ageWeeks }.map {
            val ageMonths = it.ageWeeks.toFloat() / 4.345f
            Entry(ageMonths, it.weight.toFloat())
        }
    }

    private fun addSavedDataToChart(savedEntries: List<Entry>) {
        val savedDataSet = LineDataSet(savedEntries, getString(R.string.saved_growth)).apply {
            color = Color.MAGENTA
            lineWidth = 2.5f
            setCircleColor(Color.MAGENTA)
            circleRadius = 4f
            setDrawCircleHole(false)
            setDrawValues(false)
        }

        binding.lineChart.data.addDataSet(savedDataSet)
        binding.lineChart.invalidate()
    }
}