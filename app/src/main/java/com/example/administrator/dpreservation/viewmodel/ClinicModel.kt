package com.example.administrator.dpreservation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.administrator.dpreservation.data.Position
import com.example.administrator.dpreservation.data.doctor.Doctor
import com.example.administrator.dpreservation.data.doctor.DoctorRespository

class ClinicModel internal constructor(private val respository: DoctorRespository):ViewModel(){

    fun getNearClinic() = LivePagedListBuilder<Int,Doctor>(respository.getNearDoctor(), PagedList.Config.Builder()
        .setPageSize(20)
        .setEnablePlaceholders(false)
        .setInitialLoadSizeHint(40)
        .build())
        .build()


     fun initData(){
         val position = Position("1","1","1","1","1",20.0,110.0)
         val list = ArrayList<Doctor>()
         val doctor = Doctor("id","医生1",null,position.copy(latitude = 20.toDouble()),
             "1年","大学生",true,true,
             "9-17点","重庆邮电大学","重庆市南岸区崇文路2号",
             "555456001",8,"换牙 洗牙",4.5f)
         for (i in 0..30){
             list.add(doctor.copy(id = "id$i", name = "医生$i",position = position.copy(latitude = 20.0+i)))
         }
        respository.addClick(list)
    }
}