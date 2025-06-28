package com.example.kidzi.ui.milk

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kidzi.R
import com.example.kidzi.databinding.FragmentGrowthChartBinding
import com.example.kidzi.ui.milk.adapters.GrowthChartAdapter
import dagger.hilt.android.AndroidEntryPoint
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import android.content.Context
import android.graphics.Color

@AndroidEntryPoint
class GrowthChartFragment : Fragment() {
    lateinit var adapter: GrowthChartAdapter

    fun readGrowthChartFromCSV(context: Context, type: Int): Triple<List<Entry>, List<Entry>, List<Entry>> {
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

    fun setupMultiLineChart(chart: LineChart, m: List<Entry>, p3: List<Entry>, p97: List<Entry>) {
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentGrowthChartBinding.inflate(inflater, container, false)
        val type = GrowthChartFragmentArgs.fromBundle(requireArguments()).type
        binding.txtType.text = if(type == 2) "دختران" else "پسران"

        val month = resources.getStringArray(R.array.month)
        val weightStart = if(type == 2) resources.getStringArray(R.array.girl_weight_from) else resources.getStringArray(R.array.boy_weight_from)
        val weightEnd = if(type == 2) resources.getStringArray(R.array.girl_weight_to) else resources.getStringArray(R.array.boy_weight_to)
        val heightStart = if(type == 2) resources.getStringArray(R.array.girl_height_from) else resources.getStringArray(R.array.boy_height_from)
        val heightEnd = if(type == 2) resources.getStringArray(R.array.girl_height_to) else resources.getStringArray(R.array.boy_height_to)
        val headStart = if(type == 2) resources.getStringArray(R.array.girl_head_from) else resources.getStringArray(R.array.boy_head_from)
        val headEnd = if(type == 2) resources.getStringArray(R.array.girl_head_to) else resources.getStringArray(R.array.boy_head_to)

        val (m, p3, p97) = readGrowthChartFromCSV(requireContext(), type)
        setupMultiLineChart(binding.lineChart, m, p3, p97)

        val vaccineList = mutableListOf<GrowthModel>()

        for(i in 0 until month.size){
            Log.i("Log1", "month: ${month[i]} weight start: ${weightStart[i]} weight-end:${weightEnd[i]} height-start:${heightStart[i]}height-end: ${heightEnd[i]}head-start: ${headStart[i]} head-end:${headEnd[i]}")
            vaccineList.add(
                GrowthModel(month[i].toInt(),heightStart[i].toDouble(),heightEnd[i].toDouble(),weightStart[i].toDouble(),weightEnd[i].toDouble(),headStart[i].toDouble(),
                    headEnd[i].toDouble()))
        }

        adapter = GrowthChartAdapter(vaccineList)
        binding.recycler.layoutManager = LinearLayoutManager(requireContext())
        binding.recycler.adapter = adapter
        binding.btnBack.setOnClickListener { findNavController().popBackStack() }

        return binding.root
    }

}