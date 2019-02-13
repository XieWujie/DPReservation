package com.example.administrator.dpreservation.core

import android.content.Context
import com.avos.avoscloud.*
import com.example.administrator.dpreservation.data.AppDatabase
import com.example.administrator.dpreservation.data.doctor.Doctor
import com.example.administrator.dpreservation.data.order.Order
import com.example.administrator.dpreservation.data.order.OrderRepository
import com.example.administrator.dpreservation.data.user.User
import com.example.administrator.dpreservation.utilities.COMPLETE
import com.example.administrator.dpreservation.utilities.NOT_EVALUATION
import com.example.administrator.dpreservation.utilities.NOT_GENERATED
import java.util.*


object OrderManage{

    private var respository:OrderRepository? = null

    fun createAnOrder(context: Context,user: User,doctor: Doctor, time:Long, description:String, createCallback:(e:Exception?)->Unit){
        if (respository == null){
            initRepository(context)
        }
        val o = AVObject.create("Order")
        o.put("doctorId",doctor.id)
        o.put("patientId",user.userId)
        o.put("description",description)
        o.put("time",time)
        o.put("state", NOT_GENERATED)
        o.saveInBackground(object :SaveCallback(){
            override fun done(e: AVException?) {
                if (e == null){
                    val order = Order(o.objectId,user.userId,doctor.id,time,description,
                        NOT_GENERATED,doctor.avatar,doctor.name,"${doctor.position.toString()}" ,
                        1f,doctor.phone,0,o.createdAt.time,0,0)
                    respository?.addOrder(order)
                    MessageManage.sendOrderMessage(doctor.id,o.objectId)
                    createCallback(null)
                }else{
                    createCallback(e)
                }
            }
        })
    }

    private fun initRepository(context: Context){
        if (respository == null)
        respository = OrderRepository.getInstance(AppDatabase.getInstance(context).getOrder())
    }

    fun requestOneOrder(context: Context,orderId:String,requestCallback: (e: Exception?) -> Unit){
        initRepository(context)
        val o = AVObject.createWithoutData("Order",orderId)
        o.fetchInBackground(object :GetCallback<AVObject>(){
            override fun done(o: AVObject?, e: AVException?) {
                if (e == null) {
                    with(o!!) {
                        val doctorId = getString("doctorId")
                        if (doctorId == null) {
                            requestCallback(null)
                            return
                        }
                        DoctorManager.findDoctorById(context, doctorId) {
                            if (it == null) {
                                requestCallback(null)
                                return@findDoctorById
                            } else {
                                val doctorAvatar = it.avatar
                                val doctorName = it.name
                                val time = getLong("time")
                                val description = getString("description")
                                val state = getInt("state")
                                val patientId: String = getString("patientId")
                                val score = getDouble("score").toFloat()
                                val agreeTime = getLong("agreeTime")
                                val completeTime = getLong("completeTime")
                                val createTime = createdAt.time
                                val endTreatmentTime = o.getLong("endTreatmentTime")
                                val order = Order(
                                    objectId, patientId, doctorId, time, description, state, doctorAvatar,
                                    doctorName, it.workerAddress, score, it.phone, agreeTime, createTime, completeTime,endTreatmentTime
                                )
                                respository?.addOrder(order)
                            }
                        }
                    }
                }else{
                    requestCallback(e)
                }
            }
        })
    }
    fun requestPatientOrder(context: Context,patientId:String,requestCallback:(e:Exception?)->Unit){
        initRepository(context)
        val q = AVQuery<AVObject>("Order")
        q.whereEqualTo("patientId",patientId)
        q.findInBackground(object :FindCallback<AVObject>(){
            override fun done(list: MutableList<AVObject>?, e: AVException?) {
                if (e == null){
                    val size = list!!.size
                    var count = 0
                    list!!.forEach {
                        count++
                        with(it) {
                            val doctorId = getString("doctorId")
                            if (doctorId == null){
                                requestCallback(null)
                                return
                            }
                            DoctorManager.findDoctorById(context,doctorId){
                                if (it == null){
                                    requestCallback(null)
                                    return@findDoctorById
                                }else{
                                    val doctorAvatar = it.avatar
                                    val doctorName = it.name
                                    val time = getLong("time")
                                    val description = getString("description")
                                    val state = getInt("state")
                                    val patientId: String = getString("patientId")
                                    val score = getDouble("score").toFloat()
                                    val agreeTime = getLong("agreeTime")
                                    val completeTime = getLong("completeTime")
                                    val createTime = createdAt.time
                                    val endTreatmentTime = getLong("endTreatmentTime")
                                    val order = Order(objectId,patientId,doctorId,time,description,state,doctorAvatar,
                                        doctorName,it.workerAddress,score,it.phone,agreeTime,createTime,completeTime,endTreatmentTime)
                                    respository?.addOrder(order)
                                    if (count == size){
                                        requestCallback(null)
                                        return@findDoctorById
                                    }
                                }
                            }
                        }
                    }
                }
            }
        })
    }

    fun requestDoctorOrder(context: Context,doctor: Doctor,requestCallback: (e: Exception?) -> Unit){
        if (respository == null){
            initRepository(context)
        }
        val q = AVQuery<AVObject>("Order")
        q.whereEqualTo("doctorId",doctor.id)
        q.findInBackground(object :FindCallback<AVObject>(){
            override fun done(l: MutableList<AVObject>?, e: AVException?) {
                if (e == null){
                    l!!.forEach {o->
                        val time = o.getLong("time")
                        val description = o.getString("description")
                        val state = o.getInt("state")
                        val patientId = o.getString("patientId")
                        val agreeTime = o.getLong("agreeTime")
                        val completeTime = o.getLong("completeTime")
                        val createTime = o.createdAt.time
                        val endTreatmentTime = o.getLong("endTreatmentTime")
                        val order = Order(o.objectId,patientId,doctor.id,time,description,state,doctor.avatar,doctor.name,doctor.position.toString(),
                            0f,doctor.phone,agreeTime,createTime,completeTime,endTreatmentTime)
                        respository?.addOrder(order)
                        requestCallback(null)
                    }
                }else{
                    requestCallback(e)
                }
            }
        })
    }

    fun cancelOrder(order: Order,cancelCallback:(e:Exception?)->Unit){
        val o = AVObject.createWithoutData("Order",order.id)
            o.deleteInBackground(object :DeleteCallback(){
                override fun done(e: AVException?) {
                    if (e == null){
                        respository?.delete(order)
                        cancelCallback(null)
                    }else{
                        cancelCallback(e)
                    }
                }
            })
    }


    fun evaluate(context:Context,order:Order,changeCallback:(e:Exception?)->Unit){
        initRepository(context)
        val o = AVObject.createWithoutData("Order",order.id)
        val completeTime = Date().time
        o.put("state", COMPLETE)
        o.saveInBackground(object :SaveCallback(){
            override fun done(e: AVException?) {
                if (e == null){
                    respository?.addOrder(order.copy(state = COMPLETE,completeTime = completeTime))
                    MessageManage.sendOrderMessage(order.doctorId,order.id)
                    changeCallback(null)
                }else{
                    changeCallback(e)
                }
            }
        })
    }
}