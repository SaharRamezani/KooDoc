package com.example.kidzi.ui.milk

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.kidzi.R
import com.example.kidzi.databinding.FragmentGrowthMainBinding
import com.example.kidzi.di.db.PreferenceManager
import com.example.kidzi.di.db.dao.KidNameDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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
        binding.btnChooseKid.setOnClickListener { findNavController().navigate(R.id.kidsChooseFragment) }
        binding.btnBoy.setOnClickListener { findNavController().navigate(GrowthMainFragmentDirections.actionGrowthMainFragmentToGrowthChartFragment(1)) }
        binding.btnGirl.setOnClickListener { findNavController().navigate(GrowthMainFragmentDirections.actionGrowthMainFragmentToGrowthChartFragment(2)) }
        binding.btnGrowth.setOnClickListener {
            val kidId = preferenceManager.getCurrentKid()
            if (kidId != -1) {
                findNavController().navigate(GrowthMainFragmentDirections.actionGrowthMainFragmentToKidGrowthFragment())
            } else {
                Toast.makeText(requireContext(), getString(R.string.error_choose_child), Toast.LENGTH_SHORT).show()
            }
        }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        val kidId = preferenceManager.getCurrentKid()
        if (kidId == -1) {
            binding.txtKidName.text = getString(R.string.error_no_name)
            return
        }

        lifecycleScope.launch(Dispatchers.IO) {
            val kidInfo = kidNamesDao.getKidInfo(kidId)
            launch(Dispatchers.Main) {
                binding.txtKidName.text = kidInfo?.name ?: getString(R.string.error_no_name)
            }
        }
    }
}