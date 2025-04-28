package com.example.kidzi.ui.vaccine.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kidzi.databinding.ListVaccinesBinding
import com.example.kidzi.databinding.ListWrongBinding

class VaccineWrongAdapter(
    private val vaccineList: List<String>
) : RecyclerView.Adapter<VaccineWrongAdapter.VaccineViewHolder>() {

    inner class VaccineViewHolder(val binding: ListWrongBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(vaccine: String, position: Int) {
            binding.txtName.text = vaccine
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VaccineViewHolder {
        val binding = ListWrongBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VaccineViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VaccineViewHolder, position: Int) {
        holder.bind(vaccineList[position], position)
    }

    override fun getItemCount(): Int = vaccineList.size
}
