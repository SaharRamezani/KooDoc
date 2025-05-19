package com.example.kidzi.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.kidzi.R
import com.example.kidzi.databinding.FragmentAccountBinding

class AccountFragment : Fragment() {

    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAccountBinding.inflate(inflater, container, false)

        // Hook up buttons to navigation actions
        binding.btnMyDetail.setOnClickListener {
            findNavController().navigate(R.id.action_accountFragment_to_parentShowFragment)
        }

        binding.btnKids.setOnClickListener {
            findNavController().navigate(R.id.action_accountFragment_to_kidChoose2Fragment)
        }

        binding.btnRules.setOnClickListener {
            findNavController().navigate(R.id.action_accountFragment_to_rulesFragment)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}