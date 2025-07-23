package com.example.kidzi.ui.milk.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kidzi.databinding.ListGrowthBinding
import com.example.kidzi.di.db.models.GrowthModel
import com.example.kidzi.util.NumberFormatter

class GrowthChartSimpleAdapter(
    private var growthList: List<GrowthModel>
) : RecyclerView.Adapter<GrowthChartSimpleAdapter.GrowthChartViewHolder>() {

    inner class GrowthChartViewHolder(val binding: ListGrowthBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: GrowthModel) {
            val context = binding.root.context
            binding.txtAge.text = NumberFormatter.formatNumber(context, data.age)
            binding.headStart.text = NumberFormatter.formatNumber(context, data.startHead)
            binding.headEnd.text = NumberFormatter.formatNumber(context, data.endHead)
            binding.weightStart.text = NumberFormatter.formatNumber(context, data.startWeight)
            binding.weightEnd.text = NumberFormatter.formatNumber(context, data.endWeight)
            binding.heightStart.text = NumberFormatter.formatNumber(context, data.startHeight)
            binding.heightEnd.text = NumberFormatter.formatNumber(context, data.endHeight)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GrowthChartViewHolder {
        val binding = ListGrowthBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GrowthChartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GrowthChartViewHolder, position: Int) {
        holder.bind(growthList[position])
    }

    override fun getItemCount(): Int = growthList.size
}