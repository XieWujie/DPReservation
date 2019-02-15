package com.example.administrator.dpreservation.data.doctor

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface DoctorDao{

    @Query("SELECT * FROM doctor ORDER BY streetNumber=:street DESC,district=:district DESC, city=:city DESC,name")
    fun getNearDoctor(city:String,district:String,street:String):DataSource.Factory<Int,Doctor>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addDoctor(list:List<Doctor>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addDoctor(doctor: Doctor)

    @Query("SELECT * FROM doctor WHERE isAttention = :isAttention")
    fun getAttention(isAttention:Boolean = true):DataSource.Factory<Int,Doctor>

    @Query("SElECT * FROM doctor WHERE name LIKE :keyword")
    fun queryByKeyword(keyword:String):DataSource.Factory<Int,Doctor>
}