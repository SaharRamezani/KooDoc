package com.example.kidzi.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.kidzi.databinding.FragmentLanguageSelectionBinding
import com.example.kidzi.di.db.PreferenceManager
import com.example.kidzi.util.MyLanguageManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LanguageSelectionFragment : Fragment() {

    private var _binding: FragmentLanguageSelectionBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var preferenceManager: PreferenceManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLanguageSelectionBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun changeLanguage(lang: String) {
        preferenceManager.saveLanguage(lang)

        val context = requireActivity()
        val intent = context.packageManager.getLaunchIntentForPackage(context.packageName)

        if (intent != null) {
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            context.finishAffinity() // Kill the current task
        } else {
            // Optional: fallback or error handling
            throw IllegalStateException("Unable to restart app: launch intent is null")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.btnPersian.setOnClickListener {
            changeLanguage("fa")
        }

        binding.btnEnglish.setOnClickListener {
            changeLanguage("en")
        }

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}