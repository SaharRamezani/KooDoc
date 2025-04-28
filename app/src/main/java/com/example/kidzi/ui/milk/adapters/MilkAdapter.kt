package com.example.kidzi.ui.milk.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.example.kidzi.R
import com.example.kidzi.databinding.ListGrowthBinding
import com.example.kidzi.databinding.ListMilkBinding
import com.example.kidzi.databinding.ListVaccineAboutBinding
import com.example.kidzi.databinding.ListVaccinesBinding
import com.example.kidzi.databinding.ListWrongBinding
import com.example.kidzi.ui.milk.GrowthModel
import com.example.kidzi.ui.milk.MilkModel
import com.example.kidzi.ui.vaccine.VaccineAboutModel

class MilkAdapter(
    private val vaccineList: List<MilkModel>
) : RecyclerView.Adapter<MilkAdapter.GrowthChartViewHolder>() {

    inner class GrowthChartViewHolder(val binding: ListMilkBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(vaccine: MilkModel, position: Int) {
            binding.persianName.text = vaccine.persianName
            binding.enName.text = vaccine.englishName
            binding.txtType.text = vaccine.usage
            binding.txtLact.text = when(vaccine.lac){
                0 -> "ندارد"
                1 -> "ناچیز"
                2 -> "کم"
                5 -> "دارد"
                else -> "دارد"
            }
            //binding.txtLact.text = if(vaccine.lac) "دارد" else "ندارد"
            binding.txtStart.text = vaccine.startAge.toString()
            binding.txtEnd.text = vaccine.endAge.toString()
            if(vaccine.endAge > 36) {
                binding.txtMonth.visibility = View.GONE
                binding.txtEnd.text = "آخر عمر"
            }
            binding.txtBase.text = vaccine.milkType
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GrowthChartViewHolder {
        val binding = ListMilkBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GrowthChartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GrowthChartViewHolder, position: Int) {
        holder.bind(vaccineList[position], position)
    }

    override fun getItemCount(): Int = vaccineList.size
}
