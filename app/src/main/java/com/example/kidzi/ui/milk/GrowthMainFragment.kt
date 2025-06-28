package com.example.kidzi.ui.milk

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.kidzi.R
import com.example.kidzi.databinding.FragmentGrowthMainBinding
import com.example.kidzi.di.db.PreferenceManager
import com.example.kidzi.di.db.dao.KidNameDao
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class GrowthMainFragment : Fragment() {

    lateinit var binding: FragmentGrowthMainBinding

    @Inject lateinit var preferenceManager: PreferenceManager
    @Inject lateinit var kidNamesDao: KidNameDao

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGrowthMainBinding.inflate(inflater, container, false)
        binding.btnBack.setOnClickListener { findNavController().popBackStack() }
        binding.btnChooseKid.setOnClickListener { findNavController().navigate(GrowthMainFragmentDirections.actionGrowthMainFragmentToKidsChooseFragment()) }
        binding.btnBoy.setOnClickListener { findNavController().navigate(GrowthMainFragmentDirections.actionGrowthMainFragmentToGrowthChartFragment(1)) }
        binding.btnGirl.setOnClickListener { findNavController().navigate(GrowthMainFragmentDirections.actionGrowthMainFragmentToGrowthChartFragment(2)) }
        binding.btnGrowth.setOnClickListener { findNavController().navigate(GrowthMainFragmentDirections.actionGrowthMainFragmentToGrowthFollowFragment()) }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        binding.txtKidName.text = kidNamesDao.getKidInfo(preferenceManager.getCurrentKid()).name
    }

}