package com.example.kidzi.di.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.kidzi.di.db.models.KidAlergyModel
import com.example.kidzi.di.db.models.KidDiseaseModel

@Dao
interface KidAlergyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(kidAlergyModel: KidAlergyModel)

    @Query("SELECT * FROM kid_alergy")
    fun getAll(): List<KidAlergyModel>

    @Query("SELECT * FROM kid_alergy WHERE kidId = :id")
    fun getKidInfo(id: Int): KidAlergyModel

    @Update
    fun update(kidAlergyModel: KidAlergyModel)
}