package com.example.kidzi.ui.milk

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.kidzi.R
import com.example.kidzi.databinding.FragmentMilkIntroBinding
import com.example.kidzi.di.db.PreferenceManager
import com.example.kidzi.di.db.dao.KidNameDao
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MilkIntroFragment : Fragment() {

    @Inject lateinit var kidNameDao: KidNameDao
    @Inject lateinit var preferenceManager: PreferenceManager

    lateinit var binding : FragmentMilkIntroBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMilkIntroBinding.inflate(inflater)

        binding.btnBack.setOnClickListener { findNavController().popBackStack() }

        binding.btnChooseKid.setOnClickListener {findNavController().navigate(MilkIntroFragmentDirections.actionMilkIntroFragmentToKidsChooseFragment())}
        binding.btnMyKid.setOnClickListener {findNavController().navigate(MilkIntroFragmentDirections.actionMilkIntroFragmentToMyKidMilkFragment())}
        binding.btnAllMilk.setOnClickListener {findNavController().navigate(MilkIntroFragmentDirections.actionMilkIntroFragmentToMilkResultFragment(6,0,false,false))}
        binding.btnManualMilk.setOnClickListener {findNavController().navigate(MilkIntroFragmentDirections.actionMilkIntroFragmentToMilkManualFragment())}

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val kidInfo = kidNameDao.getKidInfo(preferenceManager.getCurrentKid())
                binding.txtKidName.text = kidInfo.name
            } catch (e: Exception) {
                binding.txtKidName.text = getString(R.string.error_choose_child)
            }
        }
    }
}