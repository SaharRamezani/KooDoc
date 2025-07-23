package com.example.kidzi.ui.kid.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kidzi.databinding.ListKidsBinding
import com.example.kidzi.di.db.models.KidNameModel

class KidChooseAdapter(
    private val vaccineList: List<KidNameModel>,
    private val onItemClick: (Int) -> Unit
) : RecyclerView.Adapter<KidChooseAdapter.GrowthChartViewHolder>() {

    inner class GrowthChartViewHolder(val binding: ListKidsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(vaccine: KidNameModel, position: Int) {
            binding.txtName.text = vaccine.name
            binding.card.setOnClickListener { onItemClick(position) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GrowthChartViewHolder {
        val binding = ListKidsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GrowthChartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GrowthChartViewHolder, position: Int) {
        holder.bind(vaccineList[position], position)
    }

    override fun getItemCount(): Int = vaccineList.size
}
