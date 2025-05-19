package com.example.kidzi.ui.kid

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kidzi.R
import com.example.kidzi.databinding.FragmentKidChoose2Binding
import com.example.kidzi.di.db.PreferenceManager
import com.example.kidzi.di.db.dao.KidNameDao
import com.example.kidzi.ui.kid.adapter.KidChooseAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class KidChoose2Fragment : Fragment() {
    @Inject
    lateinit var kidNameDao: KidNameDao
    @Inject
    lateinit var preferenceManager: PreferenceManager

    private lateinit var adapter: KidChooseAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentKidChoose2Binding.inflate(inflater)

        // binding.btnBack.setOnClickListener { findNavController().popBackStack() }
        binding.btn_back.visibility = View.GONE

        val kidList = kidNameDao.getAll()

        adapter = KidChooseAdapter(kidList) { position ->
            preferenceManager.updateCurrentKid(kidList.get(position).id)
            findNavController().navigate(KidChoose2FragmentDirections.actionKidChoose2FragmentToAccountFragment())
        }

        binding.recycler.layoutManager = LinearLayoutManager(requireContext())
        binding.recycler.adapter = adapter

        return binding.root
    }
}