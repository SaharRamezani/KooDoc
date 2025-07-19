package com.example.kidzi.ui.kid

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import com.example.kidzi.util.NumberFormatter
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.kidzi.R
import com.example.kidzi.databinding.FragmentKidInfoBinding
import com.example.kidzi.di.db.PreferenceManager
import com.example.kidzi.di.db.dao.KidNameDao
import com.example.kidzi.di.db.models.KidNameModel
import com.example.kidzi.util.showPersianDatePicker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class KidInfoFragment : Fragment() {
    @Inject lateinit var sharedPreferences: PreferenceManager
    @Inject lateinit var kidNameDao: KidNameDao
    var isNew = false

    @SuppressLint("SetTextI18n")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding = FragmentKidInfoBinding.inflate(inflater)
        val args = KidInfoShowFragmentArgs.fromBundle(requireArguments())
        val id = args.kidId
        isNew = args.new

        if (!isNew) loadKidInfo(id, binding)

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnGroup.setOnClickListener { showDatePicker(binding) }

        binding.btnNext.setOnClickListener {
            validateAndSaveKidInfo(binding, id) { newId ->
                findNavController().navigate(
                    KidInfoFragmentDirections.actionKidInfoFragmentToKidDiseaseFragment(newId, isNew)
                )
            }
        }

        return binding.root
    }

    private fun loadKidInfo(id: Int, binding: FragmentKidInfoBinding) {
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val kidInfo = kidNameDao.getKidInfo(id)
                binding.txtName.setText(kidInfo.name)
                binding.txtHeight.setText(kidInfo.height.toString())
                binding.txtWeight.setText(kidInfo.weight.toString())
                binding.btnGroup.text = kidInfo.birthDate
                if (kidInfo.type == 1) binding.radioWorkingYes.isChecked = true else binding.radioWorkingNo.isChecked = true
            } catch (e: Exception) {
                Log.e("KidInfoFragment", "Error loading kid info", e)
                Toast.makeText(requireContext(), getString(R.string.toast_load_error), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showDatePicker(binding: FragmentKidInfoBinding) {
        showPersianDatePicker(requireContext()) { formattedDate ->
            binding.btnGroup.text = NumberFormatter.formatNumber(requireContext(), formattedDate)
        }
    }

    private fun validateAndSaveKidInfo(binding: FragmentKidInfoBinding, id: Int, onSaved: (Int) -> Unit) {
        when {
            binding.txtName.text.isNullOrEmpty() -> showToast(R.string.toast_enter_name_kid)
            binding.txtHeight.text.isNullOrEmpty() -> showToast(R.string.toast_enter_height)
            binding.txtWeight.text.isNullOrEmpty() -> showToast(R.string.toast_enter_weight)
            !binding.btnGroup.text.toString().contains("/") -> showToast(R.string.toast_enter_birth_date_kid)
            else -> {
                val sex = if (binding.radioWorkingYes.isChecked) 1 else 2
                viewLifecycleOwner.lifecycleScope.launch {
                    val model = KidNameModel(
                        if (isNew) 0 else id,
                        binding.txtName.text.toString(),
                        binding.txtWeight.text.toString().toDouble(),
                        binding.txtHeight.text.toString().toDouble(),
                        binding.btnGroup.text.toString(),
                        sex
                    )
                    val newId = if (isNew) {
                        kidNameDao.insert(model).toInt().also { sharedPreferences.updateCurrentKid(it) }
                    } else {
                        kidNameDao.update(model)
                        id
                    }
                    onSaved(newId)
                }
            }
        }
    }

    private fun showToast(resId: Int) {
        Toast.makeText(requireContext(), getString(resId), Toast.LENGTH_SHORT).show()
    }
}
