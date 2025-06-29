package com.example.kidzi.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.kidzi.R
import com.example.kidzi.databinding.FragmentParentInfoIntroBinding
import com.example.kidzi.di.db.PreferenceManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ParentInfoIntroFragment : Fragment() {
    @Inject
    lateinit var sharedPreferences: PreferenceManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentParentInfoIntroBinding.inflate(inflater)

        binding.txtRelation.text = when (sharedPreferences.getParent()) {
            1 -> getString(R.string.relation_father)
            2 -> getString(R.string.relation_mother)
            3 -> getString(R.string.relation_caregiver)
            else -> getString(R.string.relation_caregiver)
        }

        binding.btnRules.setOnClickListener {
            findNavController().navigate(ParentInfoIntroFragmentDirections.actionParentInfoIntroFragmentToRulesFragment())
        }
        binding.btnNext.setOnClickListener {
            if(binding.checkRules.isChecked)
                findNavController().navigate(ParentInfoIntroFragmentDirections.actionParentInfoIntroFragmentToParentInfoFragment())
            else
                Toast.makeText(requireContext(), getString(R.string.toast_agree_rules), Toast.LENGTH_SHORT).show()
        }
        binding.btnBack.setOnClickListener {
            findNavController().navigate(ParentInfoIntroFragmentDirections.actionParentInfoIntroFragmentToParentLoginFragment())
        }

        return binding.root
    }

}