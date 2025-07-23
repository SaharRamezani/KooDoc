package com.example.kidzi.ui.kid

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kidzi.R
import com.example.kidzi.databinding.FragmentKidsChooseBinding
import com.example.kidzi.di.db.PreferenceManager
import com.example.kidzi.di.db.dao.KidNameDao
import com.example.kidzi.ui.kid.adapter.KidChooseAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class KidsChooseFragment : Fragment() {

    @Inject lateinit var kidNameDao: KidNameDao
    @Inject lateinit var preferenceManager: PreferenceManager

    private lateinit var adapter: KidChooseAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentKidsChooseBinding.inflate(inflater)

        Log.i("KidsChooseFragment", "arguments = $arguments")
        Log.i("KidsChooseFragment", "navigateToInfo key exists = ${arguments?.containsKey("navigateToInfo")}")

        val navigateToInfo = arguments?.getBoolean("navigateToInfo") ?: false
        Log.i("KidsChooseFragment", "Final navigateToInfo value = $navigateToInfo")


        binding.btnBack.setOnClickListener {
            if (navigateToInfo)
                findNavController().navigate(R.id.accountFragment)
            else
                findNavController().popBackStack()
            Log.i("KidsChooseFragment", "navigateToInfo = $navigateToInfo")
        }

        binding.recycler.layoutManager = LinearLayoutManager(requireContext())

        viewLifecycleOwner.lifecycleScope.launch {
            val kidList = kidNameDao.getAll()

            adapter = KidChooseAdapter(kidList) { position ->
                val selectedKidId = kidList[position].id
                preferenceManager.updateCurrentKid(selectedKidId)

                Log.i("KidsChooseFragment", "navigateToInfo2 = $navigateToInfo")
                if (navigateToInfo) {
                    findNavController().navigate(
                        KidsChooseFragmentDirections
                            .actionKidsChooseFragmentToKidInfoShowFragment(selectedKidId, false)
                    )
                } else {
                    Log.i("selectedKidId", "selectedKidId = $selectedKidId")
                    findNavController().popBackStack()
                }
            }

            binding.recycler.adapter = adapter
        }

        return binding.root
    }
}