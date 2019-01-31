package com.example.administrator.dpreservation.data.clinic

import com.example.administrator.dpreservation.utilities.runOnNewThread

class ClinicRespository private constructor( private val clinicDao: ClinicDao){


     fun getNearClick() = clinicDao.getNearCinic()

     fun addClick(list: List<Clinic>){
        runOnNewThread {
            clinicDao.addClinic(list)
        }
    }


    companion object {

        @Volatile private var instance: ClinicRespository? = null

        fun getInstance(clinicDao: ClinicDao) =
            instance ?: synchronized(this) {
                instance ?: ClinicRespository(clinicDao).also { instance = it }
            }
    }
}