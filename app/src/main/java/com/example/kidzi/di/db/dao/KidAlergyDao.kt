package com.example.kidzi.di.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.kidzi.di.db.models.KidAlergyModel

@Dao
interface KidAlergyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(kidAlergyModel: KidAlergyModel)

    @Update
    suspend fun update(kidAlergyModel: KidAlergyModel)

    @Query("SELECT * FROM kid_alergy")
    suspend fun getAll(): List<KidAlergyModel>

    @Query("SELECT * FROM kid_alergy WHERE kidId = :id")
    suspend fun getKidInfo(id: Int): KidAlergyModel?
}
