package com.example.kidzi.ui.symptoms

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kidzi.R
import com.example.kidzi.databinding.FragmentSymptomsDetailsBinding
import com.example.kidzi.ui.symptoms.adapter.SymtomsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlin.reflect.KMutableProperty0

@AndroidEntryPoint
class SymptomsDetailsFragment : Fragment() {

    private var isInfo = false
    private var isCaring = false
    private var isEmergency = false
    private var isEmergency2 = false
    private var isEmergency3 = false
    private var isHome = false
    private var isRelate = false
    private var isH1 = false
    private var isH2 = false
    private var isH3 = false
    private var isH4 = false
    private var isH5 = false
    private var isH6 = false
    private var isH7 = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentSymptomsDetailsBinding.inflate(inflater)

        val name = SymptomsDetailsFragmentArgs.fromBundle(requireArguments()).name
        val row = SymptomsDetailsFragmentArgs.fromBundle(requireArguments()).row

        binding.txtHeader.text = name
        binding.txtCause.text = name

        setupInfo(binding, row)
        setupEmergency(binding, row)
        setupEmergencyMed(binding, row)
        setupEmergencyLow(binding, row)
        setupHome(binding, row)
        setupRelative(binding, row)
        setupCareSections(binding, row)

        binding.btnBack.setOnClickListener { findNavController().popBackStack() }

        return binding.root
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun setupInfo(binding: FragmentSymptomsDetailsBinding, row: Int) {
        val infoDetail = resources.getStringArray(R.array.symptoms_headers)[row]
        if (infoDetail.isNullOrEmpty() || infoDetail.length < 2) {
            binding.cardInfo.visibility = View.GONE
        } else {
            binding.infoDetail.text = infoDetail
            binding.cardInfo.setOnClickListener {
                isInfo = !isInfo
                binding.layoutMoreInfo.visibility = if (isInfo) View.VISIBLE else View.GONE
                binding.arrowInfo.setImageDrawable(requireContext().getDrawable(
                    if (isInfo) R.drawable.ic_arrow_up else R.drawable.ic_arrow_down
                ))
            }
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun setupEmergency(binding: FragmentSymptomsDetailsBinding, row: Int) {
        val detail = resources.getStringArray(R.array.symptoms_emergency)[row]
        if (detail.isNullOrEmpty() || detail.length < 2) {
            binding.cardEmergency.visibility = View.GONE
        } else {
            val list = splitByNewLine(detail)
            binding.emergencyDetail.layoutManager = LinearLayoutManager(requireContext())
            binding.emergencyDetail.adapter = SymtomsAdapter(list, 1)
            binding.cardEmergency.setOnClickListener {
                isEmergency = !isEmergency
                binding.layoutMoreEmergency.visibility = if (isEmergency) View.VISIBLE else View.GONE
                binding.arrowEmergency.setImageDrawable(requireContext().getDrawable(
                    if (isEmergency) R.drawable.ic_arrow_up else R.drawable.ic_arrow_down
                ))
            }
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun setupEmergencyMed(binding: FragmentSymptomsDetailsBinding, row: Int) {
        val detail = resources.getStringArray(R.array.symptoms_emergency_low)[row]
        if (detail.isNullOrEmpty() || detail.length < 2) {
            binding.cardEmergencyMed.visibility = View.GONE
        } else {
            val list = splitByNewLine(detail)
            binding.emergencyMedDetail.layoutManager = LinearLayoutManager(requireContext())
            binding.emergencyMedDetail.adapter = SymtomsAdapter(list, 2)
            binding.cardEmergencyMed.setOnClickListener {
                isEmergency2 = !isEmergency2
                binding.layoutMoreEmergencyMed.visibility = if (isEmergency2) View.VISIBLE else View.GONE
                binding.arrowEmergencyMedium.setImageDrawable(requireContext().getDrawable(
                    if (isEmergency2) R.drawable.ic_arrow_up else R.drawable.ic_arrow_down
                ))
            }
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun setupEmergencyLow(binding: FragmentSymptomsDetailsBinding, row: Int) {
        val emergencyNoArray = resources.getStringArray(R.array.symptoms_emergency_no)

        // Check if row is within bounds
        if (row >= emergencyNoArray.size) {
            binding.cardEmergencyLow.visibility = View.GONE
            Toast.makeText(requireContext(), resources.getString(R.string.symptom_index_error), Toast.LENGTH_SHORT).show()
            return
        }

        val detail = resources.getStringArray(R.array.symptoms_emergency_no)[row]
        if (detail.isNullOrEmpty() || detail.length < 2) {
            binding.cardEmergencyLow.visibility = View.GONE
        } else {
            val list = splitByNewLine(detail)
            binding.emergencyLowDetail.layoutManager = LinearLayoutManager(requireContext())
            binding.emergencyLowDetail.adapter = SymtomsAdapter(list, 3)
            binding.cardEmergencyLow.setOnClickListener {
                isEmergency3 = !isEmergency3
                binding.layoutMoreEmergencyLow.visibility = if (isEmergency3) View.VISIBLE else View.GONE
                binding.arrowEmergencyLow.setImageDrawable(requireContext().getDrawable(
                    if (isEmergency3) R.drawable.ic_arrow_up else R.drawable.ic_arrow_down
                ))
            }
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun setupHome(binding: FragmentSymptomsDetailsBinding, row: Int) {
        val detail = resources.getStringArray(R.array.symptoms_home)[row]
        if (detail.isNullOrEmpty() || detail.length < 2) {
            binding.cardHome.visibility = View.GONE
        } else {
            val list = splitByNewLine(detail)
            binding.homeDetail.layoutManager = LinearLayoutManager(requireContext())
            binding.homeDetail.adapter = SymtomsAdapter(list, 4)
            binding.cardHome.setOnClickListener {
                isHome = !isHome
                binding.layoutMoreHome.visibility = if (isHome) View.VISIBLE else View.GONE
                binding.arrowHome.setImageDrawable(requireContext().getDrawable(
                    if (isHome) R.drawable.ic_arrow_up else R.drawable.ic_arrow_down
                ))
            }
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun setupRelative(binding: FragmentSymptomsDetailsBinding, row: Int) {
        val detail = resources.getStringArray(R.array.symptoms_related)[row]
        if (detail.isNullOrEmpty() || detail.length < 2) {
            binding.cardRelative.visibility = View.GONE
        } else {
            binding.relativeDetail.text = detail
            binding.cardRelative.setOnClickListener {
                isRelate = !isRelate
                binding.layoutMoreRelative.visibility = if (isRelate) View.VISIBLE else View.GONE
                binding.arrowRelative.setImageDrawable(requireContext().getDrawable(
                    if (isRelate) R.drawable.ic_arrow_up else R.drawable.ic_arrow_down
                ))
            }
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun setupCareSections(binding: FragmentSymptomsDetailsBinding, row: Int) {

        val h1Array = resources.getStringArray(R.array.symptoms_h1)
        val h2Array = resources.getStringArray(R.array.symptoms_h2)
        val h3Array = resources.getStringArray(R.array.symptoms_h3)
        val h4Array = resources.getStringArray(R.array.symptoms_h4)
        val h5Array = resources.getStringArray(R.array.symptoms_h5)
        val h6Array = resources.getStringArray(R.array.symptoms_h6)
        val h7Array = resources.getStringArray(R.array.symptoms_h7)

        if (row >= h1Array.size || row >= h2Array.size || row >= h3Array.size ||
            row >= h4Array.size || row >= h5Array.size || row >= h6Array.size ||
            row >= h7Array.size) {
            // Log error and return early
            android.util.Log.e("SymptomsDetails", "Invalid row index: $row")
            return
        }

        // Then proceed with your existing setupCare calls using safe indices
        setupCare(
            binding.cardCareH1, binding.layoutMoreH1, binding.arrowH1,
            h1Array[row],
            R.id.txt_header_h1, R.id.h1_detail,
            ::isH1
        )

        setupCare(
            binding.cardCareH2, binding.layoutMoreH2, binding.arrowH2,
            h2Array[row],
            R.id.txt_header_h2, R.id.h2_detail,
            ::isH2
        )

        setupCare(
            binding.cardCareH3, binding.layoutMoreH3, binding.arrowH3,
            h3Array[row],
            R.id.txt_header_h3, R.id.h3_detail,
            ::isH3
        )

        setupCare(
            binding.cardCareH4, binding.layoutMoreH4, binding.arrowH4,
            h4Array[row],
            R.id.txt_header_h4, R.id.h4_detail,
            ::isH4
        )

        setupCare(
            binding.cardCareH5, binding.layoutMoreH5, binding.arrowH5,
            h5Array[row],
            R.id.txt_header_h5, R.id.h5_detail,
            ::isH5
        )

        setupCare(
            binding.cardCareH6, binding.layoutMoreH6, binding.arrowH6,
            h6Array[row],
            R.id.txt_header_h6, R.id.h6_detail,
            ::isH6
        )

        val h7Text = h7Array[row]
        if (h7Text.isNullOrEmpty() || h7Text.length < 2) {
            binding.cardCareH7.visibility = View.GONE
        } else {
            val h7 = parseStringToModel(h7Text)
            binding.txtHeaderH7.text = h7.header
            binding.h7Detail.text = h7.body
        }

        binding.cardCareH7.setOnClickListener {
            isH7 = !isH7

            // Make sure the main caring advice expands when H7 expands
            if (isH7) {
                if (binding.layoutMoreCare.visibility != View.VISIBLE) {
                    isCaring = true
                    binding.layoutMoreCare.visibility = View.VISIBLE
                    binding.arrowCare.setImageDrawable(requireContext().getDrawable(R.drawable.ic_arrow_up))
                }
                binding.layoutMoreH7.visibility = View.VISIBLE
                binding.arrowH7.setImageDrawable(requireContext().getDrawable(R.drawable.ic_arrow_up))
            } else {
                binding.layoutMoreH7.visibility = View.GONE
                binding.arrowH7.setImageDrawable(requireContext().getDrawable(R.drawable.ic_arrow_down))

                // Optionally collapse Caring Advice if H7 is last open section
                val anyOtherHOpen = isH1 || isH2 || isH3 || isH4 || isH5 || isH6
                if (!anyOtherHOpen) {
                    isCaring = false
                    binding.layoutMoreCare.visibility = View.GONE
                    binding.arrowCare.setImageDrawable(requireContext().getDrawable(R.drawable.ic_arrow_down))
                }
            }
        }

        binding.cardCare.setOnClickListener {
            isCaring = !isCaring
            binding.layoutMoreCare.visibility = if (isCaring) View.VISIBLE else View.GONE
            binding.arrowCare.setImageDrawable(requireContext().getDrawable(
                if (isCaring) R.drawable.ic_arrow_up else R.drawable.ic_arrow_down
            ))
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun setupCare(
        card: View,
        layout: View,
        arrow: View,
        text: String,
        headerId: Int,
        detailId: Int,
        state: KMutableProperty0<Boolean>
    ) {
        if (text.isEmpty() || text.length < 2) {
            card.visibility = View.GONE
            return
        }

        val model = parseStringToModel(text)
        val header = card.findViewById<TextView>(headerId)
        val detail = card.findViewById<TextView>(detailId)
        val arrowView = arrow as ImageView

        header.text = model.header
        detail.text = model.body

        card.setOnClickListener {
            val shouldExpand = layout.visibility != View.VISIBLE

            // ✅ Step 1: Update this section's state
            state.set(shouldExpand)

            // ✅ Step 2: Expand/collapse this section
            layout.visibility = if (shouldExpand) View.VISIBLE else View.GONE
            arrowView.setImageResource(if (shouldExpand) R.drawable.ic_arrow_up else R.drawable.ic_arrow_down)

            val binding = FragmentSymptomsDetailsBinding.bind(requireView())

            // ✅ Step 3: Handle the parent visibility
            if (shouldExpand) {
                if (binding.layoutMoreCare.visibility != View.VISIBLE) {
                    isCaring = true
                    binding.layoutMoreCare.visibility = View.VISIBLE
                    binding.arrowCare.setImageDrawable(requireContext().getDrawable(R.drawable.ic_arrow_up))
                }
            } else {
                // ✅ Step 4: Re-evaluate ALL open states after state is updated
                val anyStillOpen = isH1 || isH2 || isH3 || isH4 || isH5 || isH6 || isH7
                if (!anyStillOpen) {
                    isCaring = false
                    binding.layoutMoreCare.visibility = View.GONE
                    binding.arrowCare.setImageDrawable(requireContext().getDrawable(R.drawable.ic_arrow_down))
                }
            }
        }
    }

    private fun parseStringToModel(input: String): SymptomsMoreModel {
        val regex = """^([^\n:.؟!]+)[\n:.؟!](.+)""".toRegex()
        val match = regex.find(input.trim())
        val firstLine = match?.groupValues?.get(1)?.trim() ?: input.trim()
        val remainingText = match?.groupValues?.get(2)?.trim() ?: ""
        return SymptomsMoreModel(firstLine, remainingText)
    }

    private fun splitByNewLine(input: String): List<String> {
        return input.lines().map { it.trim() }.filter { it.isNotEmpty() }
    }
}