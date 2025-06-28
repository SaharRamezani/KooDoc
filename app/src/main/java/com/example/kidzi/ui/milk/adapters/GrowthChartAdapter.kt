package com.example.kidzi.ui.milk.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kidzi.databinding.ListGrowthRemovableBinding
import com.example.kidzi.ui.milk.GrowthModel

class GrowthChartAdapter(
    private var growthList: List<GrowthModel>
) : RecyclerView.Adapter<GrowthChartAdapter.GrowthChartViewHolder>() {

    inner class GrowthChartViewHolder(val binding: ListGrowthRemovableBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(vaccine: GrowthModel) {
            binding.txtAge.text = vaccine.age.toString()
            binding.head.text = vaccine.startHead.toString()
            binding.weight.text = vaccine.startWeight.toString()
            binding.height.text = vaccine.startHeight.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GrowthChartViewHolder {
        val binding = ListGrowthRemovableBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GrowthChartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GrowthChartViewHolder, position: Int) {
        holder.bind(growthList[position])
    }

    override fun getItemCount(): Int = growthList.size

    fun updateData(newList: List<GrowthModel>) {
        growthList = newList
        notifyDataSetChanged()
    }
}