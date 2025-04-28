package com.example.kidzi.di.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.kidzi.di.db.models.FamilyDiseaseModel
import com.example.kidzi.di.db.models.KidDiseaseModel
import com.example.kidzi.di.db.models.KidNameModel

@Dao
interface FamilyDiseaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(familyDisease: FamilyDiseaseModel)

    @Query("SELECT * FROM family_disease")
    fun getAll(): List<FamilyDiseaseModel>

    @Query("SELECT * FROM family_disease WHERE parentId = :id")
    fun getParentInfo(id: Int): FamilyDiseaseModel

    @Update
    fun update(familyDisease: FamilyDiseaseModel)

}