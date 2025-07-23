package com.example.kidzi.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.kidzi.R
import com.example.kidzi.databinding.FragmentAccountBinding
import com.example.kidzi.ui.milk.GrowthMainFragmentDirections

class AccountFragment : Fragment() {

    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAccountBinding.inflate(inflater, container, false)

        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.mainFragment)
        }

        binding.btnMyDetail.setOnClickListener {
            findNavController().navigate(R.id.action_accountFragment_to_parentShowFragment)
        }

        binding.btnKids.setOnClickListener {
            findNavController().navigate(
                AccountFragmentDirections.actionAccountFragmentToKidChooseFragment(true)
            )
        }

        binding.btnRules.setOnClickListener {
            findNavController().navigate(R.id.action_accountFragment_to_rulesFragment)
        }

        binding.btnAddKid.setOnClickListener {
            findNavController().navigate(AccountFragmentDirections.actionAccountFragmentToKidInfoFragmentNew(0, true))
        }

        binding.btnLanguages.setOnClickListener {
            findNavController().navigate(R.id.action_accountFragment_to_LanguageSelectionFragment)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}