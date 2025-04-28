package com.example.kidzi.ui.symptoms

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kidzi.R
import com.example.kidzi.databinding.FragmentSymptomsDetailsBinding
import com.example.kidzi.ui.symptoms.adapter.SymtomsAdapter
import com.example.kidzi.ui.vaccine.VaccineAboutModel
import com.example.kidzi.ui.vaccine.adapters.VaccineAdapter
import com.example.kidzi.ui.vaccine.adapters.VaccineInfoAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SymptomsDetailsFragment : Fragment() {

    var isInfo = false
    var isEmergency = false
    var isEmergency2 = false
    var isEmergency3 = false
    var isHome = false
    var isRelate = false
    var isCaring = false
    var isH1 = false
    var isH2 = false
    var isH3 = false
    var isH4 = false
    var isH5 = false
    var isH6 = false
    var isH7 = false


    private lateinit var adapter: VaccineAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentSymptomsDetailsBinding.inflate(inflater)

        val name = SymptomsDetailsFragmentArgs.fromBundle(requireArguments()).name
        val row = SymptomsDetailsFragmentArgs.fromBundle(requireArguments()).row

        binding.txtHeader.text = name
        binding.txtCause.text = name

        val infoDetail = resources.getStringArray(R.array.symptoms_headers)[row]
        if(infoDetail.isNullOrEmpty()||infoDetail.length < 2){
            binding.cardInfo.visibility = View.GONE
        }else{
            binding.infoDetail.text = infoDetail
        }
        binding.cardInfo.setOnClickListener {
            isInfo = !isInfo
            if(isInfo){
                binding.layoutMoreInfo.visibility = View.VISIBLE
                binding.arrowInfo.setImageDrawable(requireContext().getDrawable(R.drawable.ic_arrow_up))
            }else{
                binding.layoutMoreInfo.visibility = View.GONE
                binding.arrowInfo.setImageDrawable(requireContext().getDrawable(R.drawable.ic_arrow_up))
            }
        }


        val emergencyDetail = resources.getStringArray(R.array.symptoms_emergency)[row]
        if(emergencyDetail.isNullOrEmpty()||emergencyDetail.length < 4){
            binding.cardEmergency.visibility = View.GONE
        }else{
            val list = splitByNewLine(emergencyDetail)
            val adapter = SymtomsAdapter(list,1)
            binding.emergencyDetail.layoutManager = LinearLayoutManager(requireContext())
            binding.emergencyDetail.adapter = adapter
        }
        binding.cardEmergency.setOnClickListener {
            isEmergency = !isEmergency
            if(isEmergency){
                binding.layoutMoreEmergency.visibility = View.VISIBLE
                binding.arrowEmergency.setImageDrawable(requireContext().getDrawable(R.drawable.ic_arrow_up))
            }else{
                binding.layoutMoreEmergency.visibility = View.GONE
                binding.arrowEmergency.setImageDrawable(requireContext().getDrawable(R.drawable.ic_arrow_up))
            }
        }

        val emergencyLow = resources.getStringArray(R.array.symptoms_emergency_low)[row]
        if(emergencyLow.isNullOrEmpty()||emergencyLow.length < 2){
            binding.cardEmergencyMed.visibility = View.GONE
        }else{
            val list = splitByNewLine(emergencyLow)
            val adapter = SymtomsAdapter(list,2)
            binding.emergencyMedDetail.layoutManager = LinearLayoutManager(requireContext())
            binding.emergencyMedDetail.adapter = adapter
        }
        binding.cardEmergencyMed.setOnClickListener {
            isEmergency2 = !isEmergency2
            if(isEmergency2){
                binding.layoutMoreEmergencyMed.visibility = View.VISIBLE
                binding.arrowEmergencyMedium.setImageDrawable(requireContext().getDrawable(R.drawable.ic_arrow_up))
            }else{
                binding.layoutMoreEmergencyMed.visibility = View.GONE
                binding.arrowEmergencyMedium.setImageDrawable(requireContext().getDrawable(R.drawable.ic_arrow_up))
            }
        }


        val emergencyNo = resources.getStringArray(R.array.symptoms_emergency_no)[row]
        if(emergencyNo.isNullOrEmpty()||emergencyNo.length < 2){
            binding.cardEmergencyLow.visibility = View.GONE
        }else{

            val list = splitByNewLine(emergencyNo)
            val adapter = SymtomsAdapter(list,3)
            binding.emergencyLowDetail.layoutManager = LinearLayoutManager(requireContext())
            binding.emergencyLowDetail.adapter = adapter
        }
        binding.cardEmergencyLow.setOnClickListener {
            isEmergency3 = !isEmergency3
            if(isEmergency3){
                binding.layoutMoreEmergencyLow.visibility = View.VISIBLE
                binding.arrowEmergencyLow.setImageDrawable(requireContext().getDrawable(R.drawable.ic_arrow_up))
            }else{
                binding.layoutMoreEmergencyLow.visibility = View.GONE
                binding.arrowEmergencyLow.setImageDrawable(requireContext().getDrawable(R.drawable.ic_arrow_up))
            }
        }


        val homeDetail = resources.getStringArray(R.array.symptoms_home)[row]
        if(homeDetail.isNullOrEmpty()||homeDetail.length < 2){
            binding.cardEmergencyLow.visibility = View.GONE
        }else{
            val list = splitByNewLine(homeDetail)
            val adapter = SymtomsAdapter(list,4)
            binding.homeDetail.layoutManager = LinearLayoutManager(requireContext())
            binding.homeDetail.adapter = adapter
        }
        binding.cardHome.setOnClickListener {
            isHome = !isHome
            if(isHome){
                binding.layoutMoreHome.visibility = View.VISIBLE
                binding.arrowHome.setImageDrawable(requireContext().getDrawable(R.drawable.ic_arrow_up))
            }else{
                binding.layoutMoreHome.visibility = View.GONE
                binding.arrowHome.setImageDrawable(requireContext().getDrawable(R.drawable.ic_arrow_up))
            }
        }


        val relatedDetail = resources.getStringArray(R.array.symptoms_related)[row]
        if(relatedDetail.isNullOrEmpty()||relatedDetail.length < 2){
            binding.cardEmergencyLow.visibility = View.GONE
        }else{
            binding.relativeDetail.text = relatedDetail
        }
        binding.cardRelative.setOnClickListener {
            isRelate = !isRelate
            if(isRelate){
                binding.layoutMoreRelative.visibility = View.VISIBLE
                binding.arrowRelative.setImageDrawable(requireContext().getDrawable(R.drawable.ic_arrow_up))
            }else{
                binding.layoutMoreRelative.visibility = View.GONE
                binding.arrowRelative.setImageDrawable(requireContext().getDrawable(R.drawable.ic_arrow_up))
            }
        }


        binding.cardCare.setOnClickListener {
            isCaring = !isCaring
            if(isCaring){
                binding.layoutMoreCare.visibility = View.VISIBLE
                binding.arrowCare.setImageDrawable(requireContext().getDrawable(R.drawable.ic_arrow_up))
            }else{
                binding.layoutMoreCare.visibility = View.GONE
                binding.arrowCare.setImageDrawable(requireContext().getDrawable(R.drawable.ic_arrow_up))
            }
        }

        val h1Text = resources.getStringArray(R.array.symptoms_h1)[row]
        val h2Text = resources.getStringArray(R.array.symptoms_h2)[row]
        val h3Text = resources.getStringArray(R.array.symptoms_h3)[row]
        val h4Text = resources.getStringArray(R.array.symptoms_h4)[row]
        val h5Text = resources.getStringArray(R.array.symptoms_h5)[row]
        val h6Text = resources.getStringArray(R.array.symptoms_h6)[row]
        val h7Text = resources.getStringArray(R.array.symptoms_h7)[row]

        if(h1Text.isNullOrEmpty()||h1Text.length < 2){
            binding.cardCareH1.visibility = View.GONE
        }else{
            val h1 = parseStringToModel(h1Text)
            binding.txtHeaderH1.text = h1.header
            binding.h1Detail.text = h1.body
        }

        if(h2Text.isNullOrEmpty()||h2Text.length < 2){
            binding.cardCareH2.visibility = View.GONE
        }else{
            val h2 = parseStringToModel(h2Text)
            binding.txtHeaderH2.text = h2.header
            binding.h2Detail.text = h2.body
        }

        if(h3Text.isNullOrEmpty()||h3Text.length < 2){
            binding.cardCareH3.visibility = View.GONE
        }else{
            val h1 = parseStringToModel(h3Text)
            binding.txtHeaderH3.text = h1.header
            binding.h3Detail.text = h1.body
        }

        if(h4Text.isNullOrEmpty()||h4Text.length < 2){
            binding.cardCareH4.visibility = View.GONE
        }else{
            val h1 = parseStringToModel(h4Text)
            binding.txtHeaderH4.text = h1.header
            binding.h4Detail.text = h1.body
        }

        if(h5Text.isNullOrEmpty()||h5Text.length < 2){
            binding.cardCareH5.visibility = View.GONE
        }else{
            val h1 = parseStringToModel(h5Text)
            binding.txtHeaderH5.text = h1.header
            binding.h5Detail.text = h1.body
        }

        if(h6Text.isNullOrEmpty()||h6Text.length < 2){
            binding.cardCareH6.visibility = View.GONE
        }else{
            val h1 = parseStringToModel(h6Text)
            binding.txtHeaderH6.text = h1.header
            binding.h6Detail.text = h1.body
        }

        binding.btnBack.setOnClickListener { findNavController().popBackStack() }

        if(h7Text.isNullOrEmpty()||h7Text.length < 2){
            binding.cardCareH7.visibility = View.GONE
        }else{
            val h1 = parseStringToModel(h7Text)
            binding.txtHeaderH7.text = h1.header
            binding.h7Detail.text = h1.body
        }
        binding.cardCareH1.setOnClickListener {
            isH1 = !isH1
            if(isH1){
                binding.layoutMoreH1.visibility = View.VISIBLE
                binding.arrowH1.setImageDrawable(requireContext().getDrawable(R.drawable.ic_arrow_up))
            }else{
                binding.layoutMoreH1.visibility = View.GONE
                binding.arrowH1.setImageDrawable(requireContext().getDrawable(R.drawable.ic_arrow_down))
            }
        }
        binding.cardCareH2.setOnClickListener {
            isH2 = !isH2
            if(isH2){
                binding.layoutMoreH2.visibility = View.VISIBLE
                binding.arrowH2.setImageDrawable(requireContext().getDrawable(R.drawable.ic_arrow_up))
            }else{
                binding.layoutMoreH2.visibility = View.GONE
                binding.arrowH2.setImageDrawable(requireContext().getDrawable(R.drawable.ic_arrow_down))
            }
        }
        binding.cardCareH3.setOnClickListener {
            isH3 = !isH3
            if(isH1){
                binding.layoutMoreH3.visibility = View.VISIBLE
                binding.arrowH3.setImageDrawable(requireContext().getDrawable(R.drawable.ic_arrow_up))
            }else{
                binding.layoutMoreH3.visibility = View.GONE
                binding.arrowH3.setImageDrawable(requireContext().getDrawable(R.drawable.ic_arrow_down))
            }
        }
        binding.cardCareH4.setOnClickListener {
            isH4 = !isH4
            if(isH4){
                binding.layoutMoreH4.visibility = View.VISIBLE
                binding.arrowH4.setImageDrawable(requireContext().getDrawable(R.drawable.ic_arrow_up))
            }else{
                binding.layoutMoreH4.visibility = View.GONE
                binding.arrowH4.setImageDrawable(requireContext().getDrawable(R.drawable.ic_arrow_down))
            }
        }
        binding.cardCareH5.setOnClickListener {
            isH5 = !isH5
            if(isH5){
                binding.layoutMoreH5.visibility = View.VISIBLE
                binding.arrowH5.setImageDrawable(requireContext().getDrawable(R.drawable.ic_arrow_up))
            }else{
                binding.layoutMoreH5.visibility = View.GONE
                binding.arrowH5.setImageDrawable(requireContext().getDrawable(R.drawable.ic_arrow_down))
            }
        }
        binding.cardCareH6.setOnClickListener {
            isH6 = !isH6
            if(isH6){
                binding.layoutMoreH6.visibility = View.VISIBLE
                binding.arrowH6.setImageDrawable(requireContext().getDrawable(R.drawable.ic_arrow_up))
            }else{
                binding.layoutMoreH6.visibility = View.GONE
                binding.arrowH6.setImageDrawable(requireContext().getDrawable(R.drawable.ic_arrow_down))
            }
        }
        binding.cardCareH7.setOnClickListener {
            isH7 = !isH7
            if(isH7){
                binding.layoutMoreH7.visibility = View.VISIBLE
                binding.arrowH7.setImageDrawable(requireContext().getDrawable(R.drawable.ic_arrow_up))
            }else{
                binding.layoutMoreH7.visibility = View.GONE
                binding.arrowH7.setImageDrawable(requireContext().getDrawable(R.drawable.ic_arrow_down))
            }
        }



        // Check if the index is valid



        return binding.root

    }

    /*fun parseStringToModel(input: String): SymptomsMoreModel {
        val lines = input.lines() // Splits the input string into lines
        val firstLine = lines.firstOrNull() ?: "" // Get the first line or empty string if input is empty
        val remainingText = lines.drop(1).joinToString("\n") // Join the rest of the lines back into a string

        return SymptomsMoreModel(firstLine, remainingText)
    }*/

    fun parseStringToModel(input: String): SymptomsMoreModel {
        val regex = """^([^\n:.؟!]+)[\n:.؟!](.+)""".toRegex() // Matches first phrase as header
        val match = regex.find(input.trim())

        val firstLine = match?.groupValues?.get(1)?.trim() ?: input.trim() // Extract header
        val remainingText = match?.groupValues?.get(2)?.trim() ?: "" // Extract body

        return SymptomsMoreModel(firstLine, remainingText)
    }

    fun splitByNewLine(input: String): List<String> {
        return input.lines().map { it.trim() }.filter { it.isNotEmpty() }
    }


   /* private fun parseSymptomDetails(details: String): MutableList<VaccineAboutModel> {
        return details.split("#").map { section ->
            val parts = section.split("|")
            if (parts.size == 2) {
                VaccineAboutModel(parts[0], parts[1],false) // title | body
            } else {
                VaccineAboutModel("نامشخص", "اطلاعاتی موجود نیست.",false)
            }
        }.toMutableList()
    }*/

}