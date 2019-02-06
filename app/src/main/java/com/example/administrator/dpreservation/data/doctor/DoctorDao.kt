package com.example.administrator.dpreservation.data.doctor

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface DoctorDao{

    @Query("SELECT * FROM doctor")
    fun getNearDoctor():DataSource.Factory<Int,Doctor>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addDoctor(list:List<Doctor>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addDoctor(doctor: Doctor)

    @Query("SELECT * FROM doctor WHERE isAttention = :isAttention")
    fun getAttention(isAttention:Boolean = true):DataSource.Factory<Int,Doctor>
}