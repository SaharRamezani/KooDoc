package com.example.kidzi.di.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.kidzi.di.db.models.FamilyDiseaseModel

@Dao
interface FamilyDiseaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(familyDisease: FamilyDiseaseModel)

    @Query("SELECT * FROM family_disease")
    suspend fun getAll(): List<FamilyDiseaseModel>

    @Query("SELECT * FROM family_disease WHERE parentId = :id")
    suspend fun getParentInfo(id: Int): FamilyDiseaseModel?

    @Update
    suspend fun update(familyDisease: FamilyDiseaseModel)
}