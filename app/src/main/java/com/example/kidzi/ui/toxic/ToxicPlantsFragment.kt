package com.example.kidzi.ui.toxic

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kidzi.R
import com.example.kidzi.databinding.FragmentToxicPlantsBinding
import com.example.kidzi.ui.toxic.adapters.ToxicPlantsAdapter
import com.example.kidzi.ui.toxic.adapters.ToxicPlantsModel
import com.example.kidzi.ui.vaccine.VaccineAboutModel
import com.example.kidzi.ui.vaccine.adapters.VaccineInfoAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ToxicPlantsFragment : Fragment() {
    lateinit var adapter: ToxicPlantsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentToxicPlantsBinding.inflate(inflater, container, false)


        binding.btnBack.setOnClickListener { findNavController().popBackStack() }
        val vaccineNames = resources.getStringArray(R.array.toxic_plants)
        val vaccineAbout = resources.getStringArray(R.array.toxic_plants_details)

        val vaccineList = mutableListOf<ToxicPlantsModel>()
        val size = minOf(vaccineNames.size, vaccineAbout.size)

        for (i in 0 until size) {
            vaccineList.add(ToxicPlantsModel(vaccineNames[i], vaccineAbout[i],getDrawableByName("toxic$i")!!, false))
        }

        adapter = ToxicPlantsAdapter(vaccineList, requireContext())
        binding.recycler.layoutManager = LinearLayoutManager(requireContext())
        binding.recycler.adapter = adapter

        return binding.root
    }

    fun getDrawableByName(drawableName: String): Drawable? {
        val resId = resources.getIdentifier(drawableName, "drawable", requireContext().packageName)
        return if (resId != 0) {
            ContextCompat.getDrawable(requireContext(), resId)
        } else {
            null
        }
    }
}