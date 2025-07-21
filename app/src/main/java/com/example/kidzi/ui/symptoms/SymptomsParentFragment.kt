package com.example.kidzi.ui.symptoms

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.SearchView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kidzi.R
import com.example.kidzi.databinding.FragmentSymptomsParentBinding
import com.example.kidzi.ui.symptoms.adapter.SymptomSearchAdapter

class SymptomsParentFragment : Fragment() {
    private lateinit var adapter: SymptomSearchAdapter
    private lateinit var searchView: SearchView
    private lateinit var symptomArray: List<String>
    private lateinit var searchEditText: EditText
    private var fullList: List<Pair<String, Int>> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
        searchView.isIconifiedByDefault = false;
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

        val searchTextId = resources.getIdentifier("search_src_text", "id", "android")
        if (searchTextId != 0) {
            val editText = searchView.findViewById<View>(searchTextId)
            if (editText is EditText) {
                searchEditText = editText
                // searchEditText.textDirection = View.TEXT_DIRECTION_RTL
                // searchEditText.textAlignment = View.TEXT_ALIGNMENT_VIEW_START
                // searchEditText.gravity = Gravity.END or Gravity.CENTER_VERTICAL
                // searchEditText.layoutDirection = View.LAYOUT_DIRECTION_RTL
            }
        } else {
            Log.e("SymptomsFragment", "search_src_text ID not found")
        }


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