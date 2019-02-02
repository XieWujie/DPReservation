package com.example.administrator.dpreservation.data.doctor

import com.example.administrator.dpreservation.utilities.runOnNewThread

class DoctorRespository private constructor(private val doctorDao: DoctorDao){


     fun getNearDoctor() = doctorDao.getNearDoctor()

     fun addClick(list: List<Doctor>){
        runOnNewThread {
            doctorDao.addClinic(list)
        }
    }


    companion object {

        @Volatile private var instance: DoctorRespository? = null

        fun getInstance(doctorDao: DoctorDao) =
            instance ?: synchronized(this) {
                instance ?: DoctorRespository(doctorDao).also { instance = it }
            }
    }
}