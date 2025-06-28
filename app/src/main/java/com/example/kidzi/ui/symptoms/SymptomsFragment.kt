package com.example.kidzi.ui.symptoms

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.kidzi.databinding.FragmentSymptomsBinding
import com.example.kidzi.di.db.PreferenceManager
import com.example.kidzi.di.db.dao.KidNameDao
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SymptomsFragment : Fragment() {

    private var _binding: FragmentSymptomsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
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
                // Handle error gracefully (e.g., no kid selected or DB issue)
                binding.txtKidName.text = "نوزادی انتخاب نشده"
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}