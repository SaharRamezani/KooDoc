package com.example.kidzi.ui.milk.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kidzi.R
import com.example.kidzi.databinding.ListMilkBinding
import com.example.kidzi.di.db.PreferenceManager
import com.example.kidzi.ui.milk.MilkModel

class MilkAdapter(
    private val milkList: MutableList<MilkModel>,
    private val context: Context,
    private val preferenceManager: PreferenceManager,
    private val onItemRemoved: ((Int) -> Unit)? = null, // Only used by MyKidMilkFragment
    private val removeOnUncheck: Boolean = false // New flag to control behavior
) : RecyclerView.Adapter<MilkAdapter.GrowthChartViewHolder>() {

    inner class GrowthChartViewHolder(val binding: ListMilkBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(milk: MilkModel, position: Int) {
            binding.persianName.text = milk.persianName
            binding.enName.text = milk.englishName
            binding.txtType.text = milk.usage

            binding.txtLact.text = when (milk.lac) {
                0 -> context.getString(R.string.lact_none)
                1 -> context.getString(R.string.lact_negligible)
                2 -> context.getString(R.string.lact_low)
                5 -> context.getString(R.string.lact_present)
                else -> context.getString(R.string.lact_present)
            }

            val currentLocale = context.resources.configuration.locales.get(0).language
            if (currentLocale == "fa") {
                binding.persianName.visibility = View.VISIBLE
                binding.enName.text = "(${milk.englishName})"
            } else {
                binding.persianName.visibility = View.GONE
                binding.enName.text = milk.englishName
            }

            binding.txtStart.text = milk.startAge.toString()
            if (milk.endAge > 36) {
                binding.txtMonth.visibility = View.GONE
                binding.txtEnd.text = context.getString(R.string.end_age_lifetime)
            } else {
                binding.txtMonth.visibility = View.VISIBLE
                binding.txtEnd.visibility = View.VISIBLE
                binding.txtEnd.text = milk.endAge.toString()
            }

            binding.txtBase.text = milk.milkType

            // Prevent checkbox listener triggering on recycling
            binding.milkCheckbox.setOnCheckedChangeListener(null)
            binding.milkCheckbox.isChecked = milk.isSelected

            binding.milkCheckbox.setOnCheckedChangeListener { _, isChecked ->
                milk.isSelected = isChecked

                val updatedSet = milkList.filter { it.isSelected }.map { it.englishName }.toSet()
                preferenceManager.saveSelectedMilks(updatedSet)

                if (removeOnUncheck && !isChecked) {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        milkList.removeAt(position)
                        notifyItemRemoved(position)
                        onItemRemoved?.invoke(milkList.size)
                    }
                } else {
                    notifyItemChanged(adapterPosition)
                }
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
