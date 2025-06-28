package com.example.kidzi.util

import android.content.Context
import android.graphics.Color
import com.example.kidzi.R
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

object GrowthChartHelper {

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

    fun setupMultiLineChart(chart: LineChart, context: Context, m: List<Entry>, p3: List<Entry>, p97: List<Entry>) {
        val dataSetM = LineDataSet(m, context.getString(R.string.mean)).apply {
            color = Color.BLUE
            lineWidth = 2f
            setDrawCircles(false)
        }

        val dataSetP3 = LineDataSet(p3, context.getString(R.string.percent_3_weight)).apply {
            color = Color.RED
            lineWidth = 2f
            setDrawCircles(false)
        }

        val dataSetP97 = LineDataSet(p97, context.getString(R.string.percent_97_weight)).apply {
            color = Color.GREEN
            lineWidth = 2f
            setDrawCircles(false)
        }

        val data = LineData(dataSetM, dataSetP3, dataSetP97)
        chart.data = data
        chart.description.isEnabled = true

        // ✅ X-axis label
        chart.xAxis.apply {
            granularity = 1f
            setDrawGridLines(true)
            setDrawAxisLine(true)
            textColor = Color.BLACK
            position = com.github.mikephil.charting.components.XAxis.XAxisPosition.BOTTOM
            labelRotationAngle = 0f
        }

        // ✅ Y-axis label
        chart.axisLeft.apply {
            textColor = Color.BLACK
            setDrawGridLines(true)
            setDrawAxisLine(true)
        }
        chart.axisRight.isEnabled = false

        // Optionally set custom labels
        chart.legend.isEnabled = true

        // Set chart title (alternative to description)
        chart.description.text = context.getString(R.string.weight_kg_vs_months)
        chart.description.textColor = Color.DKGRAY
        chart.description.textSize = 12f

        chart.invalidate()
    }
}