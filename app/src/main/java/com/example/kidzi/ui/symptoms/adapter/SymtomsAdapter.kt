package com.example.kidzi.ui.symptoms.adapter

import android.graphics.Color
import android.provider.CalendarContract.Colors
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kidzi.databinding.ListSymptomsBinding
import com.example.kidzi.databinding.ListVaccinesBinding
import com.example.kidzi.databinding.ListWrongBinding

class SymtomsAdapter(
    private val vaccineList: List<String>, private val mode: Int
) : RecyclerView.Adapter<SymtomsAdapter.VaccineViewHolder>() {

    inner class VaccineViewHolder(val binding: ListSymptomsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(vaccine: String, position: Int) {
            binding.txtName.text = vaccine
            when(mode){
                1 -> binding.color.setBackgroundColor(Color.RED)
                2 -> binding.color.setBackgroundColor(Color.YELLOW)
                3 -> binding.color.setBackgroundColor(Color.BLUE)
                4 -> binding.color.setBackgroundColor(Color.GREEN)
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
