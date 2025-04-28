package com.example.kidzi.ui.symptoms.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kidzi.R
import com.example.kidzi.databinding.ListVaccinesBinding


class SymptomSearchAdapter(
    private var items: List<Pair<String, Int>>,
    private val onItemClick: (Int) -> Unit
) : RecyclerView.Adapter<SymptomSearchAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.txt_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_vaccines, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val (name, originalIndex) = items[position]
        holder.textView.text = name

        holder.itemView.setOnClickListener {
            onItemClick(originalIndex) // Pass the original position
        }
    }

    override fun getItemCount(): Int = items.size

    fun updateList(newList: List<Pair<String, Int>>) {
        items = newList
        notifyDataSetChanged()
    }
}

