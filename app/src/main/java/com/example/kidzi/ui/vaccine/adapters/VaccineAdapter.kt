package com.example.kidzi.ui.vaccine.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kidzi.databinding.ListVaccinesBinding

class VaccineAdapter(
    private val vaccineList: List<String>,
    private val onItemClick: (Int) -> Unit
) : RecyclerView.Adapter<VaccineAdapter.VaccineViewHolder>() {

    inner class VaccineViewHolder(val binding: ListVaccinesBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(vaccine: String, position: Int) {
            binding.txtName.text = vaccine
            binding.root.setOnClickListener { onItemClick(position) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VaccineViewHolder {
        val binding = ListVaccinesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VaccineViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VaccineViewHolder, position: Int) {
        holder.bind(vaccineList[position], position)
    }

    override fun getItemCount(): Int = vaccineList.size
}
