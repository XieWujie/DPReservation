package com.example.administrator.dpreservation.core

import android.content.Context
import com.avos.avoscloud.*
import com.example.administrator.dpreservation.data.AppDatabase
import com.example.administrator.dpreservation.data.evaluation.Evaluation
import com.example.administrator.dpreservation.data.evaluation.EvaluationRepository
import com.example.administrator.dpreservation.data.order.Order
import com.example.administrator.dpreservation.data.user.User
import com.example.administrator.dpreservation.utilities.AVATAR
import com.example.administrator.dpreservation.utilities.COMPLETE
import java.lang.Exception

object EvaluateManager{

    private var repository:EvaluationRepository? = null

    private fun initRepository(context: Context){
        if (repository == null)
        repository = EvaluationRepository.getInstance(AppDatabase.getInstance(context).getEvaluationDao())
    }

    fun createEvaluation(context: Context, score:Float, content:String, user: User, order: Order, createCallback:(e:Exception?)->Unit){
        initRepository(context)
        val o = AVObject.create("Evaluation")
        with(o){
            put("doctorId",order.doctorId)
            put("score",score.toDouble())
            put("content",content)
            put("patientId",user.userId)
            put("name",user.name)
            put("avatar",user.avatar)
            saveInBackground(object :SaveCallback(){
                override fun done(e: AVException?) {
                    if (e == null){
                        val evaluation = Evaluation(objectId,o.createdAt.time,order.patientId,order.doctorId,score,user.avatar,user.name,content)
                        repository?.insert(evaluation)
                        OrderManage.evaluate(context,order){
                            if (it == null){
                                MessageManage.sendOrderMessage(order.doctorId,order.id)
                            }
                            createCallback(e)
                        }
                    }else{
                        createCallback(e)
                    }
                }
            })
        }
    }

    fun findDoctorEvaluation(context: Context,doctorId:String,findCallback:(e:Exception?)->Unit){
        initRepository(context)
        val q = AVQuery<AVObject>("Evaluation")
        q.whereEqualTo("doctorId",doctorId)
        q.findInBackground(object : FindCallback<AVObject>() {
            override fun done(list: MutableList<AVObject>?, e: AVException?) {
                if (e == null) {
                    list!!.forEach { o ->
                        with(o) {
                            val patientId = getString("patientId")
                            val score = getDouble("score").toFloat()
                            val avatar = getString(AVATAR)
                            val name = getString("name")
                            val content = getString("content")
                            val evaluation =
                                Evaluation(objectId, createdAt.time, patientId, doctorId, score, avatar, name, content)
                            repository?.insert(evaluation)
                        }
                    }
                    findCallback(null)
                }else{
                    findCallback(e)
                }
            }
        })
    }
}