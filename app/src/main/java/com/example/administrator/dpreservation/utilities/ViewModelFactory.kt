package com.example.administrator.dpreservation.utilities

import android.content.Context
import com.example.administrator.dpreservation.data.AppDatabase
import com.example.administrator.dpreservation.data.doctor.DoctorRepository
import com.example.administrator.dpreservation.data.evaluation.EvaluationRepository
import com.example.administrator.dpreservation.data.message.MessageRepository
import com.example.administrator.dpreservation.data.order.OrderRepository
import com.example.administrator.dpreservation.data.user.UserRepository
import com.example.administrator.dpreservation.viewmodel.*

object ViewModelFactory{

    private fun getDatabase(context: Context) = AppDatabase.getInstance(context)

    fun getClinicModelFactory(context: Context) =
            DoctorFactory(DoctorRepository.getInstance(getDatabase(context).getClinicDao()))

    fun getUserModelFactory(context: Context) =
            UserModelFactory(UserRepository.getInstance(getDatabase(context).getUserDao()))

    fun getMessageModelFactory(context: Context) =
        MessageModelFactory(MessageRepository.getInstance(getDatabase(context).getMessageDao()))

    fun getOrderModelFactory(context: Context) =
            OrderModelFactory(OrderRepository.getInstance(getDatabase(context).getOrder()))

    fun getEvaluationFactory(context: Context) =
            EvaluationFactory(EvaluationRepository.getInstance(getDatabase(context).getEvaluationDao()))

}