package com.example.kidzi.ui.kid

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kidzi.R
import com.example.kidzi.databinding.FragmentKidsChooseBinding
import com.example.kidzi.di.db.PreferenceManager
import com.example.kidzi.di.db.dao.KidNameDao
import com.example.kidzi.ui.kid.adapter.KidChooseAdapter
import com.example.kidzi.ui.vaccine.VaccineAgeFragmentDirections
import com.example.kidzi.ui.vaccine.adapters.VaccineAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class KidsChooseFragment : Fragment() {

    @Inject lateinit var kidNameDao: KidNameDao
    @Inject lateinit var preferenceManager: PreferenceManager

    private lateinit var adapter: KidChooseAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentKidsChooseBinding.inflate(inflater)
        binding.btnBack.setOnClickListener { findNavController().popBackStack() }

        val kidList = kidNameDao.getAll()

        adapter = KidChooseAdapter(kidList) { position ->
            preferenceManager.updateCurrentKid(kidList.get(position).id)
            findNavController().popBackStack()
        }

        binding.recycler.layoutManager = LinearLayoutManager(requireContext())
        binding.recycler.adapter = adapter


        return binding.root
    }

}