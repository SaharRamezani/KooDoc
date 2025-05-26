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
import com.example.kidzi.di.db.PreferenceManager
import com.example.kidzi.ui.milk.GrowthModel
import com.example.kidzi.ui.milk.MilkModel
import com.example.kidzi.ui.vaccine.VaccineAboutModel

class MilkAdapter(
    private val milkList: MutableList<MilkModel>,
    private val context: Context,
    private val preferenceManager: PreferenceManager,
    private val onItemRemoved: ((Int) -> Unit)? = null
) : RecyclerView.Adapter<MilkAdapter.GrowthChartViewHolder>() {

    inner class GrowthChartViewHolder(val binding: ListMilkBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(milk: MilkModel, position: Int) {
            binding.persianName.text = milk.persianName
            binding.enName.text = milk.englishName
            binding.txtType.text = milk.usage
            binding.txtLact.text = when(milk.lac){
                0 -> "ندارد"
                1 -> "ناچیز"
                2 -> "کم"
                5 -> "دارد"
                else -> "دارد"
            }
            //binding.txtLact.text = if(vaccine.lac) "دارد" else "ندارد"
            binding.txtStart.text = milk.startAge.toString()
            binding.txtEnd.text = milk.endAge.toString()
            binding.txtStart.text = milk.startAge.toString()

            if (milk.endAge > 36) {
                binding.txtMonth.visibility = View.GONE
                binding.txtEnd.text       = "آخر عمر"
            } else {
                binding.txtMonth.visibility = View.VISIBLE
                binding.txtEnd.visibility   = View.VISIBLE
                binding.txtEnd.text         = milk.endAge.toString()
            }

            binding.txtBase.text = milk.milkType

            // 🧠 Set checkbox state
            binding.milkCheckbox.setOnCheckedChangeListener(null)
            binding.milkCheckbox.isChecked = milk.isSelected

            // 💾 Save immediately on change
            binding.milkCheckbox.setOnCheckedChangeListener { _, isChecked ->
                milk.isSelected = isChecked
                val updated = milkList.filter { it.isSelected }.map { it.englishName }.toSet()
                preferenceManager.saveSelectedMilks(updated)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GrowthChartViewHolder {
        val binding = ListMilkBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GrowthChartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GrowthChartViewHolder, position: Int) {
        holder.bind(milkList[position], position)
    }

    override fun getItemCount(): Int = milkList.size
}
