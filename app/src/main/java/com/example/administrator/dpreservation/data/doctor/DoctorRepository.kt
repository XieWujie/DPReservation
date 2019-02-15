package com.example.administrator.dpreservation.data.doctor

import com.example.administrator.dpreservation.utilities.runOnNewThread

class DoctorRepository private constructor(private val doctorDao: DoctorDao){


     fun getNearDoctor(city:String,district:String,street:String) = doctorDao.getNearDoctor(city,district,street)

    fun queryByKeyword(keyword:String) = doctorDao.queryByKeyword(keyword)

    fun getAttention() = doctorDao.getAttention()

     fun addDoctor(list: List<Doctor>){
        runOnNewThread {
            doctorDao.addDoctor(list)
        }
    }

    fun addDoctor(doctor:Doctor){
       runOnNewThread {
           doctorDao.addDoctor(doctor)
       }
    }


    companion object {

        @Volatile private var instance: DoctorRepository? = null

        fun getInstance(doctorDao: DoctorDao) =
            instance ?: synchronized(this) {
                instance ?: DoctorRepository(doctorDao).also { instance = it }
            }
    }
}