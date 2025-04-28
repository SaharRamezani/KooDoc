package com.example.kidzi.ui.kid.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.example.kidzi.R
import com.example.kidzi.databinding.ListGrowthBinding
import com.example.kidzi.databinding.ListKidsBinding
import com.example.kidzi.databinding.ListMilkBinding
import com.example.kidzi.databinding.ListVaccineAboutBinding
import com.example.kidzi.databinding.ListVaccinesBinding
import com.example.kidzi.databinding.ListWrongBinding
import com.example.kidzi.di.db.PreferenceManager
import com.example.kidzi.di.db.models.KidNameModel
import com.example.kidzi.ui.milk.GrowthModel
import com.example.kidzi.ui.milk.MilkModel
import com.example.kidzi.ui.vaccine.VaccineAboutModel
import javax.inject.Inject

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
