package com.example.kidzi.ui.symptoms

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.kidzi.R
import com.example.kidzi.databinding.FragmentSymptomsBinding
import com.example.kidzi.di.db.PreferenceManager
import com.example.kidzi.di.db.dao.KidNameDao
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SymptomsFragment : Fragment() {

    private var _binding: FragmentSymptomsBinding? = null

    private val binding get() = _binding!!

    @Inject lateinit var preferenceManager: PreferenceManager
    @Inject lateinit var kidNamesDao: KidNameDao

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSymptomsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.btnBack.setOnClickListener { findNavController().popBackStack() }
        binding.btnChooseKid.setOnClickListener { findNavController().navigate(SymptomsFragmentDirections.actionSymptomsFragmentToKidsChooseFragment()) }
        binding.btnAddKid.setOnClickListener { findNavController().navigate(SymptomsFragmentDirections.actionSymptomsFragmentToKidInfoFragment(0,true)) }
        binding.btnSymp.setOnClickListener { findNavController().navigate(SymptomsFragmentDirections.actionSymptomsFragmentToSymptomsParentFragment()) }

        return root
    }

    override fun onResume() {
        super.onResume()

        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val kid = kidNamesDao.getKidInfo(preferenceManager.getCurrentKid())
                binding.txtKidName.text = kid.name
            } catch (e: Exception) {
                binding.txtKidName.text = getString(R.string.error_choose_child)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}