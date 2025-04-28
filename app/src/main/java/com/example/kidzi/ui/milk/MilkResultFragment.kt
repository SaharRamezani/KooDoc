package com.example.kidzi.ui.milk

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kidzi.R
import com.example.kidzi.databinding.FragmentMilkResultBinding
import com.example.kidzi.di.db.PreferenceManager
import com.example.kidzi.di.db.dao.KidAlergyDao
import com.example.kidzi.di.db.dao.KidNameDao
import com.example.kidzi.di.helpers.PersianDateHelper
import com.example.kidzi.ui.milk.adapters.MilkAdapter
import com.example.kidzi.ui.vaccine.adapters.VaccineInfoAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MilkResultFragment : Fragment() {

    lateinit var adapter: MilkAdapter

    @Inject lateinit var preferenceManager: PreferenceManager
    @Inject lateinit var kidNameDao: KidNameDao
    @Inject lateinit var kidAlergyDao: KidAlergyDao

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentMilkResultBinding.inflate(inflater, container, false)
        var type = MilkResultFragmentArgs.fromBundle(requireArguments()).type
        var age = MilkResultFragmentArgs.fromBundle(requireArguments()).age
        var cow : Boolean? = MilkResultFragmentArgs.fromBundle(requireArguments()).cow
        var lac : Boolean? = MilkResultFragmentArgs.fromBundle(requireArguments()).lac

        val milkList = mutableListOf<MilkModel>()

        val englishName = resources.getStringArray(R.array.milk_name_en)
        val persianName = resources.getStringArray(R.array.milk_name_fa)
        val monthStart = resources.getStringArray(R.array.milk_start)
        val monthFinish = resources.getStringArray(R.array.milk_finish)
        val milkType = resources.getStringArray(R.array.milk_type)
        val milkUse = resources.getStringArray(R.array.milk_use)
        val milkLac = resources.getStringArray(R.array.milk_lac)

        binding.btnBack.setOnClickListener { findNavController().popBackStack() }

        if (type == 1){
            try {

                binding.txtTitle.text = "همه شیر خشک ها"
                for (i in 0 until englishName.size - 1) {
                    milkList.add(
                        MilkModel(
                            persianName[i],
                            englishName[i],
                            monthStart[i].toInt(),
                            monthFinish[i].toInt(),
                            milkLac[i].toInt(),
                            milkUse[i],
                            milkType[i]
                        )
                    )

                }
            }catch (e:Exception){
                Toast.makeText(requireContext(),e.toString(),Toast.LENGTH_SHORT).show()
            }
        }else if(type == 2){
            binding.txtTitle.text = "شیر خشک های مناسب فرزند من"

            Log.i("Log1","my current kid is : ${preferenceManager.getCurrentKid()}")

            val kid = kidNameDao.getKidInfo(preferenceManager.getCurrentKid())
            val alergy = kidAlergyDao.getKidInfo(preferenceManager.getCurrentKid())

            Log.i("Log1","birth is : ${kid.birthDate}")

            val age = PersianDateHelper.getAgeInMonths(kid.birthDate)
            Log.i("Log1","age is : $age")

            cow = alergy.cow
            lac = alergy.lac

            if(cow == null)
                cow = false
            if(lac == null)
                lac = false
            for(i in 0 until  englishName.size){
                if ((age >= monthStart[i].toInt() && age <= monthFinish[i].toInt())){
                    if(lac){
                        if(milkLac[i].toInt() <3){
                            if(cow){
                                if(!milkType[i].contains("گاو")){
                                    if(type == 3){
                                        if(milkType[i].contains("رژیمی")){
                                            milkList.add(MilkModel(persianName[i],englishName[i],monthStart[i].toInt(),monthFinish[i].toInt(),milkLac[i].toInt(), milkUse[i],milkType[i]))
                                        }
                                        }else{ milkList.add(MilkModel(persianName[i],englishName[i],monthStart[i].toInt(),monthFinish[i].toInt(),milkLac[i].toInt(), milkUse[i],milkType[i])) }
                                    }
                            }else{
                                if(type == 3){
                                    if(milkType[i].contains("رژیمی")){
                                        milkList.add(MilkModel(persianName[i],englishName[i],monthStart[i].toInt(),monthFinish[i].toInt(),milkLac[i].toInt(), milkUse[i],milkType[i]))
                                    }
                                }else{ milkList.add(MilkModel(persianName[i],englishName[i],monthStart[i].toInt(),monthFinish[i].toInt(),milkLac[i].toInt(), milkUse[i],milkType[i])) }
                            }
                        }
                    }else{
                        if(cow){
                            if(!milkType[i].contains("گاو")){
                                if(type == 3){
                                    if(milkType[i].contains("رژیمی")){
                                        milkList.add(MilkModel(persianName[i],englishName[i],monthStart[i].toInt(),monthFinish[i].toInt(),milkLac[i].toInt(), milkUse[i],milkType[i]))
                                    }
                                }else{ milkList.add(MilkModel(persianName[i],englishName[i],monthStart[i].toInt(),monthFinish[i].toInt(),milkLac[i].toInt(), milkUse[i],milkType[i])) }
                            }
                        }else{
                            if(type == 3){
                                if(milkType[i].contains("رژیمی")){
                                    milkList.add(MilkModel(persianName[i],englishName[i],monthStart[i].toInt(),monthFinish[i].toInt(),milkLac[i].toInt(), milkUse[i],milkType[i]))
                                }
                            }else{ milkList.add(MilkModel(persianName[i],englishName[i],monthStart[i].toInt(),monthFinish[i].toInt(),milkLac[i].toInt(), milkUse[i],milkType[i])) }
                        }
                    }
                }
            }
        }else{

            binding.txtTitle.text = "شیر خشک های مناسب ویژگی انتخابی"

            if(cow == null)
                cow = false
            if(lac == null)
                lac = false

            for(i in 0 until  englishName.size){
                if ((age >= monthStart[i].toInt() && age <= monthFinish[i].toInt())){
                    if(lac){
                        if(milkLac[i].toInt() <3){
                            if(cow){
                                if(!milkType[i].contains("گاو")){
                                    milkList.add(MilkModel(persianName[i],englishName[i],monthStart[i].toInt(),monthFinish[i].toInt(),milkLac[i].toInt(), milkUse[i],milkType[i]))
                                }
                            }else{
                                milkList.add(MilkModel(persianName[i],englishName[i],monthStart[i].toInt(),monthFinish[i].toInt(),milkLac[i].toInt(), milkUse[i],milkType[i]))
                            }
                        }
                    }else{
                        if(cow){
                            if(!milkType[i].contains("گاو")){
                                milkList.add(MilkModel(persianName[i],englishName[i],monthStart[i].toInt(),monthFinish[i].toInt(),milkLac[i].toInt(), milkUse[i],milkType[i]))
                            }
                        }else{
                            milkList.add(MilkModel(persianName[i],englishName[i],monthStart[i].toInt(),monthFinish[i].toInt(),milkLac[i].toInt(), milkUse[i],milkType[i]))
                        }
                    }
                }
            }
            /*for(i in 0 until  englishName.size){
                if ((age >= monthStart[i].toInt() && age <= monthFinish[i].toInt())){
                if((milkLac[i].toBoolean() && !lac!!) || (!milkLac[i].toBoolean() && !cow!!))
                        milkList.add(MilkModel(persianName[i],englishName[i],monthStart[i].toInt(),monthFinish[i].toInt(),milkLac[i].toInt(), milkUse[i],milkType[i]))
                }
            }*/
        }


        try {
            adapter = MilkAdapter(milkList)
            binding.recycler.layoutManager = LinearLayoutManager(requireContext())
            binding.recycler.adapter = adapter
        }catch (e: Exception){
            Toast.makeText(requireContext(),e.toString(),Toast.LENGTH_SHORT).show()
        }


        return binding.root
    }

}