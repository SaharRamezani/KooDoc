package com.example.kidzi.ui.milk

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.kidzi.databinding.FragmentKidGrowthBinding
import com.example.kidzi.di.db.PreferenceManager
import com.example.kidzi.di.db.dao.KidGrowthDao
import com.example.kidzi.di.db.dao.KidNameDao
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class KidGrowthFragment : Fragment() {

    @Inject lateinit var preferenceManager: PreferenceManager
    @Inject lateinit var kidNameDao: KidNameDao
    var sex = 1

    lateinit var binding: FragmentKidGrowthBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentKidGrowthBinding.inflate(inflater)
        val kidId = preferenceManager.getCurrentKid()

        sex = kidNameDao.getKidInfo(kidId).type
        binding.btnBack.setOnClickListener { findNavController().popBackStack() }

        return binding.root
    }
}