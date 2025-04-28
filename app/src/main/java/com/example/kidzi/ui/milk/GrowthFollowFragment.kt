package com.example.kidzi.ui.milk

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.kidzi.R
import com.example.kidzi.databinding.FragmentGrowthFollowBinding
import com.example.kidzi.di.db.PreferenceManager
import com.example.kidzi.di.db.dao.KidGrowthDao
import com.example.kidzi.di.db.dao.KidNameDao
import com.example.kidzi.di.db.models.KidGrowthModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class GrowthFollowFragment : Fragment() {

    @Inject lateinit var preferenceManager: PreferenceManager
    @Inject lateinit var growthDao: KidGrowthDao
    @Inject lateinit var kidNameDao: KidNameDao

    var sex = 1


    private val percentiles = listOf(3, 5, 10, 25, 50, 75, 90, 95, 97)


    lateinit var binding: FragmentGrowthFollowBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentGrowthFollowBinding.inflate(inflater)
        val kidId = preferenceManager.getCurrentKid()

        sex = kidNameDao.getKidInfo(kidId).type
/*

        val age = resources.getStringArray(R.array.p_age).toList()
        val bw3 = resources.getStringArray(R.array.p_w_b_3).toList()
        val bw5 = resources.getStringArray(R.array.p_w_b_5).toList()
        val bw10 = resources.getStringArray(R.array.p_w_b_10).toList()
        val bw25 = resources.getStringArray(R.array.p_w_b_25).toList()
        val bw50 = resources.getStringArray(R.array.p_w_b_50).toList()
        val bw75 = resources.getStringArray(R.array.p_w_b_75).toList()
        val bw90 = resources.getStringArray(R.array.p_w_b_90).toList()
        val bw95 = resources.getStringArray(R.array.p_w_b_95).toList()
        val bw97 = resources.getStringArray(R.array.p_w_b_97).toList()

        val bl3 = resources.getStringArray(R.array.p_l_b_3).toList()
        val bl5 = resources.getStringArray(R.array.p_l_b_5).toList()
        val bl10 = resources.getStringArray(R.array.p_l_b_10).toList()
        val bl25 = resources.getStringArray(R.array.p_l_b_25).toList()
        val bl50 = resources.getStringArray(R.array.p_l_b_50).toList()
        val bl75 = resources.getStringArray(R.array.p_l_b_75).toList()
        val bl90 = resources.getStringArray(R.array.p_l_b_90).toList()
        val bl95 = resources.getStringArray(R.array.p_l_b_95).toList()
        val bl97 = resources.getStringArray(R.array.p_l_b_97).toList()

        val bh3 = resources.getStringArray(R.array.p_h_b_3).toList()
        val bh5 = resources.getStringArray(R.array.p_h_b_5).toList()
        val bh10 = resources.getStringArray(R.array.p_h_b_10).toList()
        val bh25 = resources.getStringArray(R.array.p_h_b_25).toList()
        val bh50 = resources.getStringArray(R.array.p_h_b_50).toList()
        val bh75 = resources.getStringArray(R.array.p_h_b_75).toList()
        val bh90 = resources.getStringArray(R.array.p_h_b_90).toList()
        val bh95 = resources.getStringArray(R.array.p_h_b_95).toList()
        val bh97 = resources.getStringArray(R.array.p_h_b_97).toList()

        val gw3 = resources.getStringArray(R.array.p_w_g_3).toList()
        val gw5 = resources.getStringArray(R.array.p_w_g_5).toList()
        val gw10 = resources.getStringArray(R.array.p_w_g_10).toList()
        val gw25 = resources.getStringArray(R.array.p_w_g_25).toList()
        val gw50 = resources.getStringArray(R.array.p_w_g_50).toList()
        val gw75 = resources.getStringArray(R.array.p_w_g_75).toList()
        val gw90 = resources.getStringArray(R.array.p_w_g_90).toList()
        val gw95 = resources.getStringArray(R.array.p_w_g_95).toList()
        val gw97 = resources.getStringArray(R.array.p_w_g_97).toList()

        val gl3 = resources.getStringArray(R.array.p_l_g_3).toList()
        val gl5 = resources.getStringArray(R.array.p_l_g_5).toList()
        val gl10 = resources.getStringArray(R.array.p_l_g_10).toList()
        val gl25 = resources.getStringArray(R.array.p_l_g_25).toList()
        val gl50 = resources.getStringArray(R.array.p_l_g_50).toList()
        val gl75 = resources.getStringArray(R.array.p_l_g_75).toList()
        val gl90 = resources.getStringArray(R.array.p_l_g_90).toList()
        val gl95 = resources.getStringArray(R.array.p_l_g_95).toList()
        val gl97 = resources.getStringArray(R.array.p_l_g_97).toList()

        val gh3 = resources.getStringArray(R.array.p_h_g_3).toList()
        val gh5 = resources.getStringArray(R.array.p_h_g_5).toList()
        val gh10 = resources.getStringArray(R.array.p_h_g_10).toList()
        val gh25 = resources.getStringArray(R.array.p_h_g_25).toList()
        val gh50 = resources.getStringArray(R.array.p_h_g_50).toList()
        val gh75 = resources.getStringArray(R.array.p_h_g_75).toList()
        val gh90 = resources.getStringArray(R.array.p_h_g_90).toList()
        val gh95 = resources.getStringArray(R.array.p_h_g_95).toList()
        val gh97 = resources.getStringArray(R.array.p_h_g_97).toList()
*/


        //val weigth = if(sex == 1) listOf(bw3,bw5,bw10,bw25,bw50,bw75,bw90,bw95,bw97) else listOf(gw3,gw5,gw10,gw25,gw50,gw75,gw90,gw95,gw97)
        //val length = if(sex == 1) listOf(bl3,bl5,bl10,bl25,bl50,bl75,bl90,bl95,bl97) else listOf(gl3,gl5,gl10,gl25,gl50,gl75,gl90,gl95,gl97)
        //val head = if(sex == 1) listOf(bh3,bh5,bh10,bh25,bh50,bh75,bh90,bh95,bh97) else listOf(gh3,gh5,gh10,gh25,gh50,gh75,gh90,gh95,gh97)






        binding.btnBack.setOnClickListener { findNavController().popBackStack() }
        /*binding.btnRegister.setOnClickListener {
            if(binding.txtAge.text.toString().isNotEmpty() && binding.txtHeight.text.toString().isNotEmpty() && binding.txtWeight.text.toString().isNotEmpty() && binding.txtHead.text.toString().isNotEmpty()){
                binding.txtW.text = findPercentile(binding.txtAge.text.toString().toDouble(),binding.txtWeight.text.toString().toDouble(),weigth)
                binding.txtL.text = findPercentile(binding.txtAge.text.toString().toDouble(),binding.txtHeight.text.toString().toDouble(),length)
                binding.txtH.text = findPercentile(binding.txtAge.text.toString().toDouble(),binding.txtHead.text.toString().toDouble(),head)
                try {
                    growthDao.insert(KidGrowthModel(kidId,binding.txtAge.text.toString().toInt(),binding.txtHeight.text.toString().toDouble(),binding.txtWeight.text.toString().toDouble(),binding.txtHead.text.toString().toDouble()))
                    Toast.makeText(requireContext(),"با موفقیت ثبت شد.",Toast.LENGTH_SHORT).show()
                }catch (e: Exception){
                    Toast.makeText(requireContext(),e.toString(),Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(requireContext(),"ورود همه مقادیر الزامی است.",Toast.LENGTH_SHORT).show()
            }
        }*/

        return binding.root
    }


    private fun findPercentile(age: Double, weight: Double, data: List<List<String>>): String {
        val percentiles = listOf(3, 5, 10, 25, 50, 75, 90, 95, 97)

        // Find two closest age rows for interpolation
        val lowerRow = data.lastOrNull { it[0].toDouble() <= age } ?: data.first()
        val upperRow = data.firstOrNull { it[0].toDouble() >= age } ?: data.last()

        // If exact match, use it; otherwise, interpolate between two closest age rows
        val interpolatedWeights = if (lowerRow == upperRow) {
            lowerRow.subList(1, lowerRow.size)
        } else {
            val lowerAge = lowerRow[0].toDouble()
            val upperAge = upperRow[0].toDouble()
            val fraction = (age - lowerAge) / (upperAge - lowerAge)

            lowerRow.subList(1, lowerRow.size).zip(upperRow.subList(1, upperRow.size)) { low, high ->
                low + fraction * (high.toDouble() - low.toDouble())
            }
        }

        // Search the column to find percentile range
        if (weight < interpolatedWeights.first().toDouble()) return " پایین${percentiles.first()}%"
        if (weight > interpolatedWeights.last().toDouble()) return " بالای${percentiles.last()}%"

        for (i in 0 until interpolatedWeights.size - 1) {
            if (weight in interpolatedWeights[i].toDouble()..interpolatedWeights[i + 1].toDouble()) {
                return "بین " + "P${percentiles[i]}% " + "و " +" P${percentiles[i + 1]}%"
            }
        }

        return "پرسنتایل نامشخص"
    }


    override fun onResume() {
        super.onResume()
        binding.txtKidName.text = kidNameDao.getKidInfo(preferenceManager.getCurrentKid()).name
        sex = kidNameDao.getKidInfo(preferenceManager.getCurrentKid()).type
    }

}