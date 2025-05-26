package com.example.kidzi.ui.vaccine.adapters

import com.example.kidzi.databinding.ListVaccinesNoArrowBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kidzi.databinding.ListVaccinesBinding

class VaccineAdapter(
    private val vaccineList: List<String>,
    private val onItemClick: (Int) -> Unit,
    private val useArrow: Boolean = true
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class VaccineViewHolder(val binding: ListVaccinesBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(vaccine: String, position: Int) {
            binding.txtName.text = vaccine
            binding.root.setOnClickListener { onItemClick(position) }
        }
    }

    inner class VaccineNoArrowViewHolder(val binding: ListVaccinesNoArrowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(vaccine: String, position: Int) {
            binding.txtName.text = vaccine
            binding.root.setOnClickListener { onItemClick(position) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == 0) {
            val binding = ListVaccinesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            VaccineViewHolder(binding)
        } else {
            val binding = ListVaccinesNoArrowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            VaccineNoArrowViewHolder(binding)
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is VaccineViewHolder -> holder.bind(vaccineList[position], position)
            is VaccineNoArrowViewHolder -> holder.bind(vaccineList[position], position)
        }
    }

    override fun getItemCount(): Int = vaccineList.size
    override fun getItemViewType(position: Int): Int {
        return if (useArrow) 0 else 1
    }
}
