package com.example.kidzi.di.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.kidzi.di.db.models.KidSocialModel

@Dao
interface KidSocialDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(kidSocialModel: KidSocialModel)

    @Update
    suspend fun update(kidSocialModel: KidSocialModel)

    @Query("SELECT * FROM kid_social")
    suspend fun getAll(): List<KidSocialModel>

    @Query("SELECT * FROM kid_social WHERE kidId = :id")
    suspend fun getKidInfo(id: Int): KidSocialModel?
}