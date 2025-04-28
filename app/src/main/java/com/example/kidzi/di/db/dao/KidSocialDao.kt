package com.example.kidzi.di.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.kidzi.di.db.models.KidAlergyModel
import com.example.kidzi.di.db.models.KidDiseaseModel
import com.example.kidzi.di.db.models.KidSocialModel

@Dao
interface KidSocialDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(kidSocialModel: KidSocialModel)

    @Query("SELECT * FROM kid_social")
    fun getAll(): List<KidSocialModel>

    @Query("SELECT * FROM kid_social WHERE kidId = :id")
    fun getKidInfo(id: Int): KidSocialModel

    @Update
    fun update(kidSocialModel: KidSocialModel)

}