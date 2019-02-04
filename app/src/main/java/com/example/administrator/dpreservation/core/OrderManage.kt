package com.example.administrator.dpreservation.core

import android.content.Context
import com.avos.avoscloud.*
import com.example.administrator.dpreservation.data.AppDatabase
import com.example.administrator.dpreservation.data.doctor.Doctor
import com.example.administrator.dpreservation.data.order.Order
import com.example.administrator.dpreservation.data.order.OrderRepository
import com.example.administrator.dpreservation.data.user.User


object OrderManage{

    private var respository:OrderRepository? = null

    fun createAnOrder(context: Context,user: User,doctor: Doctor, time:Long, description:String, createCallback:(e:Exception?)->Unit){
        if (respository == null){
            initRepository(context)
        }
        val o = AVObject.create("Order")
        o.put("doctortId",doctor.id)
        o.put("patientId",user.userId)
        o.put("description",description)
        o.put("time",time)
        o.put("state",1)
        o.saveInBackground(object :SaveCallback(){
            override fun done(e: AVException?) {
                if (e == null){
                    val order = Order(o.objectId,user.userId,doctor.id,time,description,1,doctor.avatar,doctor.name,"${doctor.position.toString()}" ,1f)
                    respository?.addOrder(order)
                    createCallback(null)
                }else{
                    createCallback(e)
                }
            }
        })
    }

    private fun initRepository(context: Context){
        respository = OrderRepository.getInstance(AppDatabase.getInstance(context).getOrder())
    }

    fun requestPatientOrder(context: Context,patientId:String,requestCallback:(e:Exception?)->Unit){

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
                        val doctorId = o.getString("doctorId")
                        val time = o.getLong("time")
                        val description = o.getString("description")
                        val state = o.getInt("state")
                        val patientId = o.getString("patientId")
                        val order = Order(o.objectId,patientId,doctor.id,time,description,state,doctor.avatar,doctor.name,doctor.position.toString(), 0f)
                        respository?.addOrder(order)
                        requestCallback(null)
                    }
                }else{
                    requestCallback(e)
                }
            }
        })
    }

}