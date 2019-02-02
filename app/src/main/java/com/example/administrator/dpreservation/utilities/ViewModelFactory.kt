package com.example.administrator.dpreservation.utilities

import android.content.Context
import com.example.administrator.dpreservation.data.AppDatabase
import com.example.administrator.dpreservation.data.doctor.DoctorRespository
import com.example.administrator.dpreservation.data.message.MessageRepository
import com.example.administrator.dpreservation.data.user.UserRepository
import com.example.administrator.dpreservation.viewmodel.ClinicFactory
import com.example.administrator.dpreservation.viewmodel.MessageModelFactory
import com.example.administrator.dpreservation.viewmodel.UserModelFactory

object ViewModelFactory{

    private fun getDatabase(context: Context) = AppDatabase.getInstance(context)

    fun getClinicModelFactory(context: Context) =
            ClinicFactory(DoctorRespository.getInstance(getDatabase(context).getClinicDao()))

    fun getUserModelFactory(context: Context) =
            UserModelFactory(UserRepository.getInstance(getDatabase(context).getUserDao()))

    fun getMessageModelFactory(context: Context) =
        MessageModelFactory(MessageRepository.getInstance(getDatabase(context).getMessageDao()))

}