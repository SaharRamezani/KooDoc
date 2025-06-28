package com.example.kidzi.ui.milk

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.kidzi.R
import com.example.kidzi.databinding.FragmentGrowthFollowBinding
import com.example.kidzi.di.db.PreferenceManager
import com.example.kidzi.di.db.dao.KidGrowthDao
import com.example.kidzi.di.db.dao.KidNameDao
import com.example.kidzi.di.db.models.KidGrowthModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class GrowthFollowFragment : Fragment() {

    @Inject lateinit var preferenceManager: PreferenceManager
    @Inject lateinit var growthDao: KidGrowthDao
    @Inject lateinit var kidNameDao: KidNameDao
    var sex = 1

    private val percentiles = listOf(3, 5, 10, 25, 50, 75, 90, 95, 97)
    lateinit var binding: FragmentGrowthFollowBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGrowthFollowBinding.inflate(inflater)
        val kidId = preferenceManager.getCurrentKid()

        sex = kidNameDao.getKidInfo(kidId).type
        binding.btnBack.setOnClickListener { findNavController().popBackStack() }

        return binding.root
    }
}