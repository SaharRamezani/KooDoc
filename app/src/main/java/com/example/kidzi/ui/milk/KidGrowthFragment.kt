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
import com.example.kidzi.R
import com.example.kidzi.databinding.FragmentKidGrowthBinding
import com.example.kidzi.di.db.PreferenceManager
import com.example.kidzi.di.db.dao.GrowthDataDao
import com.example.kidzi.di.db.dao.KidNameDao
import com.example.kidzi.di.db.models.KidGrowthModel
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
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

    private lateinit var binding: FragmentKidGrowthBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentKidGrowthBinding.inflate(inflater, container, false)

        binding.btnBack.setOnClickListener { findNavController().popBackStack() }

        val kidId = preferenceManager.getCurrentKid()
        if (kidId != -1) {
            lifecycleScope.launch(Dispatchers.IO) {
                val kidInfo = kidNameDao.getKidInfo(kidId)
                if (kidInfo != null) {
                    val sex = kidInfo.type // 1 = boy, 2 = girl
                    val (m, p3, p97) = readGrowthChartFromCSV(requireContext(), sex)

                    launch(Dispatchers.Main) {
                        setupMultiLineChart(binding.lineChart, m, p3, p97)
                    }

                    val savedData = growthDataDao.getAllGrowthDataForKid(kidId)
                    val savedEntries = convertSavedDataToEntries(savedData)

                    launch(Dispatchers.Main) {
                        addSavedDataToChart(binding.lineChart, savedEntries)
                    }
                } else {
                    // Optionally, handle case where kid info isn't found
                    launch(Dispatchers.Main) {
                        // Show a toast or navigate back
                        findNavController().popBackStack()
                    }
                }
            }
        }

        return binding.root
    }

    private fun readGrowthChartFromCSV(context: android.content.Context, type: Int): Triple<List<Entry>, List<Entry>, List<Entry>> {
        val entriesM = mutableListOf<Entry>()
        val entriesP3 = mutableListOf<Entry>()
        val entriesP97 = mutableListOf<Entry>()

        val fileName = if (type == 2) "girls_weight.csv" else "boys_weight.csv"
        val inputStream = context.assets.open(fileName)

        inputStream.bufferedReader().useLines { lines ->
            lines.drop(1).forEach { line ->
                val tokens = line.split(",")
                val month = tokens[0].toFloat()
                val m = tokens[2].toFloat()
                val p3 = tokens[6].toFloat()
                val p97 = tokens[16].toFloat()

                entriesM.add(Entry(month, m))
                entriesP3.add(Entry(month, p3))
                entriesP97.add(Entry(month, p97))
            }
        }

        return Triple(entriesM, entriesP3, entriesP97)
    }

    private fun setupMultiLineChart(chart: LineChart, m: List<Entry>, p3: List<Entry>, p97: List<Entry>) {
        val dataSetM = LineDataSet(m, getString(R.string.mean)).apply {
            color = Color.BLUE
            lineWidth = 2f
            setDrawCircles(false)
        }

        val dataSetP3 = LineDataSet(p3, getString(R.string.percent_3_weight)).apply {
            color = Color.RED
            lineWidth = 2f
            setDrawCircles(false)
        }

        val dataSetP97 = LineDataSet(p97, getString(R.string.percent_97_weight)).apply {
            color = Color.GREEN
            lineWidth = 2f
            setDrawCircles(false)
        }

        val data = LineData(dataSetM, dataSetP3, dataSetP97)
        chart.data = data
        chart.description.isEnabled = false
        chart.invalidate()
    }

    private fun convertSavedDataToEntries(data: List<KidGrowthModel>): List<Entry> {
        return data.sortedBy { it.ageWeeks }.map {
            Entry(it.ageWeeks.toFloat(), it.weight.toFloat())
        }
    }

    private fun addSavedDataToChart(chart: LineChart, savedEntries: List<Entry>) {
        val savedDataSet = LineDataSet(savedEntries, getString(R.string.saved_growth)).apply {
            color = Color.MAGENTA
            lineWidth = 2.5f
            setCircleColor(Color.MAGENTA)
            circleRadius = 4f
            setDrawCircleHole(false)
            setDrawValues(false)
        }

        chart.data.addDataSet(savedDataSet)
        chart.invalidate()
    }
}