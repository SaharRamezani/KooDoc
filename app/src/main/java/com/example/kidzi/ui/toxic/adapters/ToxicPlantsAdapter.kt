package com.example.kidzi.ui.toxic.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kidzi.R
import com.example.kidzi.databinding.ListToxicPlantsBinding

class ToxicPlantsAdapter(
    private val vaccineList: MutableList<ToxicPlantsModel>,
    val context : Context
) : RecyclerView.Adapter<ToxicPlantsAdapter.VaccineViewHolder>() {

    inner class VaccineViewHolder(val binding: ListToxicPlantsBinding, context: Context) : RecyclerView.ViewHolder(binding.root) {
        fun bind(vaccine: ToxicPlantsModel, position: Int) {
            binding.txtHeader.text = vaccine.name
            binding.txtMore.text = vaccine.about
            binding.card.setOnClickListener {
                vaccineList[position].expand = !vaccineList[position].expand
                if(vaccineList[position].expand){
                    binding.layoutMore.visibility = View.VISIBLE
                    binding.arrow.setImageDrawable(context.getDrawable(R.drawable.ic_arrow_up))
                }else{
                    binding.layoutMore.visibility = View.GONE
                    binding.arrow.setImageDrawable(context.getDrawable(R.drawable.ic_arrow_down))
                }
            }
            if(vaccine.img != null) binding.imgTox.setImageDrawable(vaccine.img)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VaccineViewHolder {
        val binding = ListToxicPlantsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VaccineViewHolder(binding,context)
    }

    override fun onBindViewHolder(holder: VaccineViewHolder, position: Int) {
        holder.bind(vaccineList[position], position)
    }

    override fun getItemCount(): Int = vaccineList.size
}
