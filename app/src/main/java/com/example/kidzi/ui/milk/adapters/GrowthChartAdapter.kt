package com.example.kidzi.ui.milk.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.example.kidzi.R
import com.example.kidzi.databinding.ListGrowthBinding
import com.example.kidzi.databinding.ListVaccineAboutBinding
import com.example.kidzi.databinding.ListVaccinesBinding
import com.example.kidzi.databinding.ListWrongBinding
import com.example.kidzi.ui.milk.GrowthModel
import com.example.kidzi.ui.vaccine.VaccineAboutModel

class GrowthChartAdapter(
    private val vaccineList: List<GrowthModel>
) : RecyclerView.Adapter<GrowthChartAdapter.GrowthChartViewHolder>() {

    inner class GrowthChartViewHolder(val binding: ListGrowthBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(vaccine: GrowthModel, position: Int) {
            binding.txtAge.text = vaccine.age.toString()
            binding.headStart.text = vaccine.startHead.toString()
            binding.headEnd.text = vaccine.endHead.toString()
            binding.weightStart.text = vaccine.startWeight.toString()
            binding.weightEnd.text = vaccine.endWeight.toString()
            binding.heightStart.text = vaccine.startHeight.toString()
            binding.heightEnd.text = vaccine.endHeight.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GrowthChartViewHolder {
        val binding = ListGrowthBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GrowthChartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GrowthChartViewHolder, position: Int) {
        holder.bind(vaccineList[position], position)
    }

    override fun getItemCount(): Int = vaccineList.size
}
