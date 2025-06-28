package com.example.kidzi.di.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.kidzi.di.db.models.KidGrowthModel

@Dao
interface GrowthDataDao {
    @Insert
    fun insert(growthData: KidGrowthModel)

    @Query("SELECT * FROM kid_growth WHERE kidId = :kidId ORDER BY ageWeeks DESC")
    abstract fun getAllGrowthDataForKid(kidId: Int): List<KidGrowthModel>

    @Query("DELETE FROM kid_growth WHERE ageWeeks = :age AND kidId = :kidId")
    fun deleteByAgeAndKidId(age: Int, kidId: Int)
}
