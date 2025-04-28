package com.example.kidzi.ui.milk

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kidzi.R
import com.example.kidzi.databinding.FragmentGrowthChartBinding
import com.example.kidzi.ui.milk.adapters.GrowthChartAdapter
import com.example.kidzi.ui.vaccine.VaccineAboutModel
import com.example.kidzi.ui.vaccine.adapters.VaccineInfoAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GrowthChartFragment : Fragment() {



    lateinit var adapter: GrowthChartAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentGrowthChartBinding.inflate(inflater, container, false)
        val type = GrowthChartFragmentArgs.fromBundle(requireArguments()).type
        binding.txtType.text = if(type == 2) "دختران" else "پسران"

        val month = resources.getStringArray(R.array.month)
        val weightStart = if(type == 2) resources.getStringArray(R.array.girl_weight_from) else resources.getStringArray(R.array.boy_weight_from)
        val weightEnd = if(type == 2) resources.getStringArray(R.array.girl_weight_to) else resources.getStringArray(R.array.boy_weight_to)
        val heightStart = if(type == 2) resources.getStringArray(R.array.girl_height_from) else resources.getStringArray(R.array.boy_height_from)
        val heightEnd = if(type == 2) resources.getStringArray(R.array.girl_height_to) else resources.getStringArray(R.array.boy_height_to)
        val headStart = if(type == 2) resources.getStringArray(R.array.girl_head_from) else resources.getStringArray(R.array.boy_head_from)
        val headEnd = if(type == 2) resources.getStringArray(R.array.girl_head_to) else resources.getStringArray(R.array.boy_head_to)


        val vaccineList = mutableListOf<GrowthModel>()

        for(i in 0 until month.size){
            Log.i("Log1", "month: ${month[i]} weight start: ${weightStart[i]} weight-end:${weightEnd[i]} height-start:${heightStart[i]}height-end: ${heightEnd[i]}head-start: ${headStart[i]} head-end:${headEnd[i]}")
            vaccineList.add(
                GrowthModel(month[i].toInt(),heightStart[i].toDouble(),heightEnd[i].toDouble(),weightStart[i].toDouble(),weightEnd[i].toDouble(),headStart[i].toDouble(),
                    headEnd[i].toDouble()))
        }


        adapter = GrowthChartAdapter(vaccineList)
        binding.recycler.layoutManager = LinearLayoutManager(requireContext())
        binding.recycler.adapter = adapter




        binding.btnBack.setOnClickListener { findNavController().popBackStack() }



        return binding.root
    }

}