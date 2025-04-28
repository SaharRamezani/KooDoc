package com.example.kidzi.di.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.kidzi.di.db.models.KidGrowthModel

@Dao
interface KidGrowthDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(kidGrowthModel: KidGrowthModel)


    @Query("SELECT * FROM growth  WHERE kidId = :id")
    fun getAll(id: Int): List<KidGrowthModel>



    @Query("SELECT * FROM growth  WHERE kidId = :id and age = :age")
    fun getByAge(id: Int,age: Int): KidGrowthModel

    @Update
    fun update(kidGrowthModel: KidGrowthModel)

}