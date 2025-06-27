package com.example.kidzi.ui.symptoms.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kidzi.databinding.ListSymptomsBinding

class SymtomsAdapter(
    private val vaccineList: List<String>, private val mode: Int
) : RecyclerView.Adapter<SymtomsAdapter.VaccineViewHolder>() {

    inner class VaccineViewHolder(val binding: ListSymptomsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(vaccine: String, position: Int) {
            binding.txtName.text = vaccine
            val context = binding.root.context
            when (mode) {
                1 -> binding.color.setBackgroundColor(
                    context.getColor(android.R.color.holo_red_dark)
                )
                2 -> binding.color.setBackgroundColor(
                    context.getColor(android.R.color.holo_orange_light)
                )
                3 -> binding.color.setBackgroundColor(
                    context.getColor(android.R.color.holo_blue_light)
                )
                4 -> binding.color.setBackgroundColor(
                    context.getColor(android.R.color.holo_green_light)
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VaccineViewHolder {
        val binding = ListSymptomsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VaccineViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VaccineViewHolder, position: Int) {
        holder.bind(vaccineList[position], position)
    }

    override fun getItemCount(): Int = vaccineList.size
}
