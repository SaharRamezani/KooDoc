package com.example.kidzi.di.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.kidzi.di.db.models.KidDiseaseModel
import com.example.kidzi.di.db.models.KidNameModel

@Dao
interface KidDiseaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(kidDisease: KidDiseaseModel)

    @Query("SELECT * FROM kid_disease")
    suspend fun getAll(): List<KidDiseaseModel>

    @Query("SELECT * FROM kid_disease WHERE kidId = :id")
    suspend fun getKidInfo(id: Int): KidDiseaseModel?

    @Update
    suspend fun update(kidDisease: KidDiseaseModel)
}