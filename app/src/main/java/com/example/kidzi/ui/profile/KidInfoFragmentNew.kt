package com.example.kidzi.ui.kid

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.kidzi.R
import com.example.kidzi.databinding.FragmentKidInfoNewBinding
import com.example.kidzi.di.db.PreferenceManager
import com.example.kidzi.di.db.dao.KidNameDao
import com.example.kidzi.di.db.models.KidNameModel
import com.example.kidzi.util.NumberFormatter
import com.example.kidzi.util.showPersianDatePicker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class KidInfoFragmentNew : Fragment() {

    @Inject lateinit var sharedPreferences: PreferenceManager
    @Inject lateinit var kidNameDao: KidNameDao

    private var isNew = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding = FragmentKidInfoNewBinding.inflate(inflater)

        isNew = KidInfoFragmentNewArgs.fromBundle(requireArguments()).new

        binding.btnBack.setOnClickListener {
            findNavController().navigate(KidInfoFragmentNewDirections.actionKidInfoNewFragmentToAccountFragment())
        }

        binding.btnGroup.setOnClickListener {
            showDatePicker(binding)
        }

        binding.btnNext.setOnClickListener {
            validateAndSaveKidInfo(binding) {
                findNavController().navigate(
                    KidInfoFragmentNewDirections.actionKidInfoNewFragmentToAccountFragment()
                )
            }
        }

        return binding.root
    }

    private fun showDatePicker(binding: FragmentKidInfoNewBinding) {
        showPersianDatePicker(requireContext()) { formattedDate ->
            binding.btnGroup.text = NumberFormatter.formatNumber(requireContext(), formattedDate)
        }
    }

    private fun validateAndSaveKidInfo(binding: FragmentKidInfoNewBinding, onSaved: () -> Unit) {
        when {
            binding.txtName.text.isNullOrEmpty() -> showToast(R.string.toast_enter_name_kid)
            binding.txtHeight.text.isNullOrEmpty() -> showToast(R.string.toast_enter_height)
            binding.txtWeight.text.isNullOrEmpty() -> showToast(R.string.toast_enter_weight)
            !binding.btnGroup.text.toString().contains("/") -> showToast(R.string.toast_enter_birth_date_kid)
            else -> {
                val sex = if (binding.radioWorkingYes.isChecked) 1 else 2
                viewLifecycleOwner.lifecycleScope.launch {
                    val newId = kidNameDao.insert(
                        KidNameModel(
                            0, // Let Room auto-generate the ID
                            binding.txtName.text.toString(),
                            binding.txtWeight.text.toString().toDouble(),
                            binding.txtHeight.text.toString().toDouble(),
                            binding.btnGroup.text.toString(),
                            sex
                        )
                    ).toInt()
                    sharedPreferences.updateCurrentKid(newId)
                    Log.i("KidInfoFragmentNew", "id of kid is: $newId")
                    onSaved()
                }
            }
        }
    }

    private fun showToast(resId: Int) {
        Toast.makeText(requireContext(), getString(resId), Toast.LENGTH_SHORT).show()
    }
}