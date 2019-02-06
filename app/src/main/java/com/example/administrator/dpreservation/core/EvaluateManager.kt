package com.example.administrator.dpreservation.core

import android.content.Context
import com.avos.avoscloud.AVException
import com.avos.avoscloud.AVObject
import com.avos.avoscloud.SaveCallback
import com.example.administrator.dpreservation.data.AppDatabase
import com.example.administrator.dpreservation.data.evaluation.Evaluation
import com.example.administrator.dpreservation.data.evaluation.EvaluationRepository
import com.example.administrator.dpreservation.data.order.Order
import com.example.administrator.dpreservation.utilities.COMPLETE
import java.lang.Exception

object EvaluateManager{

    private var repository:EvaluationRepository? = null

    private fun initRepository(context: Context){
        if (repository == null)
        repository = EvaluationRepository.getInstance(AppDatabase.getInstance(context).getEvaluationDao())
    }

    fun createEvaluation(context: Context,score:Int,content:String,order: Order,createCallback:(e:Exception?)->Unit){
        initRepository(context)
        val o = AVObject.create("Evaluation")
        with(o){
            put("doctorId",order.doctorId)
            put("score",score)
            put("content",content)
            put("userId",order.patientId)
            saveInBackground(object :SaveCallback(){
                override fun done(e: AVException?) {
                    if (e == null){
                        val user = UserManage.user!!
                        val evaluation = Evaluation(objectId,o.createdAt.time,order.patientId,order.doctorId,score,user.avatar,user.name,content)
                        repository?.insert(evaluation)
                        OrderManage.changeState(context,order, COMPLETE){
                            createCallback(e)
                        }
                    }else{
                        createCallback(e)
                    }
                }
            })
        }
    }

}