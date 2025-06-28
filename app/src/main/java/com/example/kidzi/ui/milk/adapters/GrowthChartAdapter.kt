package com.example.kidzi.ui.milk.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kidzi.databinding.ListGrowthRemovableBinding
import com.example.kidzi.ui.milk.GrowthModel
import com.example.kidzi.util.NumberFormatter

class GrowthChartAdapter(
    private var growthList: MutableList<GrowthModel>,
    private val onDelete: (GrowthModel) -> Unit
) : RecyclerView.Adapter<GrowthChartAdapter.GrowthChartViewHolder>() {

    inner class GrowthChartViewHolder(val binding: ListGrowthRemovableBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: GrowthModel) {
            val context = binding.root.context
            binding.txtAge.text = NumberFormatter.formatNumber(context, data.age)
            binding.head.text = NumberFormatter.formatNumber(context, data.startHead)
            binding.weight.text = NumberFormatter.formatNumber(context, data.startWeight)
            binding.height.text = NumberFormatter.formatNumber(context, data.startHeight)

            binding.removeIcon.setOnClickListener {
                onDelete(data)
            }
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

    fun removeItem(data: GrowthModel) {
        val index = growthList.indexOf(data)
        if (index != -1) {
            growthList.removeAt(index)
            notifyItemRemoved(index)
        }
    }

    fun updateData(newList: List<GrowthModel>) {
        growthList = newList.toMutableList()
        notifyDataSetChanged()
    }
}
