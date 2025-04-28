package com.example.kidzi.di.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.kidzi.di.db.models.KidNameModel

@Dao
interface KidNameDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(kidName: KidNameModel) : Long

    @Query("SELECT * FROM kid_name")
    fun getAll(): List<KidNameModel>

    @Query("SELECT * FROM kid_name WHERE id = :id")
    fun getKidInfo(id: Int): KidNameModel

    @Update
    fun update(kidName: KidNameModel)

}