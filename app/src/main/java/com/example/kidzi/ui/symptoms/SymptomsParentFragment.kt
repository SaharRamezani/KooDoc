package com.example.kidzi.ui.symptoms

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kidzi.R
import com.example.kidzi.databinding.FragmentSymptomsParentBinding
import com.example.kidzi.ui.symptoms.adapter.SymptomSearchAdapter
import com.example.kidzi.ui.toxic.ToxicMedFragmentDirections
import com.example.kidzi.ui.vaccine.adapters.VaccineAdapter

class SymptomsParentFragment : Fragment() {



    private lateinit var adapter: SymptomSearchAdapter
    private lateinit var searchView: SearchView
    private lateinit var symptomArray: List<String>
    private var fullList: List<Pair<String, Int>> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentSymptomsParentBinding.inflate(inflater)



        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }


        symptomArray = resources.getStringArray(R.array.symptoms_names).toList()
        fullList = symptomArray.mapIndexed { index, name -> name to index } // Store original indices


        adapter = SymptomSearchAdapter(fullList) { originalPosition ->
            findNavController().navigate(
                SymptomsParentFragmentDirections.actionSymptomsParentFragmentToSymptomsDetailsFragment(
                    symptomArray[originalPosition], originalPosition
                )
            )
        }

        binding.recycler.layoutManager = LinearLayoutManager(requireContext())

        binding.recycler.adapter = adapter

        // Setup SearchView
        searchView = binding.searchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                filter(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filter(newText)
                return true
            }
        })



        return binding.root
    }


    private fun filter(query: String?) {
        val filteredList = if (!query.isNullOrEmpty()) {
            fullList.filter { it.first.contains(query, ignoreCase = true) }
        } else {
            fullList
        }
        adapter.updateList(filteredList)
    }

}