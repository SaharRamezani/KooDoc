package com.example.kidzi.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.paging.LOGGER
import com.example.kidzi.databinding.FragmentHomeBinding
import com.example.kidzi.di.db.PreferenceManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import javax.inject.Inject


@AndroidEntryPoint
class HomeFragment : Fragment() {


    @Inject
    lateinit var sharedPreferences: PreferenceManager

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root


        binding.btnNext.setOnClickListener {
            //checkApiStatus()

            Log.i("Log1","can open is: ${sharedPreferences.canOpen()}")
            if(sharedPreferences.canOpen()) {
                when (sharedPreferences.getLevel()) {
                    0 -> findNavController().navigate(HomeFragmentDirections.actionNavigationHomeToParentLoginFragment())
                    1 -> findNavController().navigate(HomeFragmentDirections.actionNavigationHomeToParentInfoIntroFragment())
                    2 -> findNavController().navigate(HomeFragmentDirections.actionNavigationHomeToKidIntroFragment())
                    3 -> findNavController().navigate(
                        HomeFragmentDirections.actionNavigationHomeToFamilyDisFirstFragment(
                            0,
                            true
                        )
                    )

                    4 -> findNavController().navigate(HomeFragmentDirections.actionNavigationHomeToMainFragment())
                    else -> findNavController().navigate(HomeFragmentDirections.actionNavigationHomeToParentLoginFragment())
                }
            }
            /*
            when(sharedPreferences.getLevel()){
                0-> findNavController().navigate(HomeFragmentDirections.actionNavigationHomeToParentLoginFragment())
                1-> findNavController().navigate(HomeFragmentDirections.actionNavigationHomeToParentInfoIntroFragment())
                2-> findNavController().navigate(HomeFragmentDirections.actionNavigationHomeToKidIntroFragment())
                3-> findNavController().navigate(HomeFragmentDirections.actionNavigationHomeToFamilyDisFirstFragment(0,true))
                4-> findNavController().navigate(HomeFragmentDirections.actionNavigationHomeToMainFragment())
                else -> findNavController().navigate(HomeFragmentDirections.actionNavigationHomeToParentLoginFragment())
            }*/
        }


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}