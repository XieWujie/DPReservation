package com.example.administrator.dpreservation.core

import android.content.Context
import com.avos.avoscloud.*
import com.example.administrator.dpreservation.data.AppDatabase
import com.example.administrator.dpreservation.data.Position
import com.example.administrator.dpreservation.data.doctor.Doctor
import com.example.administrator.dpreservation.data.doctor.DoctorRepository
import java.lang.Exception

object DoctorManager{

    private val doctorMap = HashMap<String,Doctor>()
    private var repository :DoctorRepository? = null

    fun findDoctorById(context: Context,doctorId:String,findCallback:(doctor:Doctor?)->Unit){
        initRepository(context)
        if (doctorMap.containsKey(doctorId)){
            findCallback(doctorMap[doctorId])
        }else{
            findDoctorByNet(doctorId,findCallback)
        }
    }


    fun requestDoctors(context: Context,list: List<String>,requestCallback: (e: Exception?) -> Unit){
        for (i in list){
            findDoctorById(context,i){

            }
        }
    }
    fun requestDoctor(context: Context,requestCallback:(e:Exception?)->Unit){
        initRepository(context)
        val o = AVQuery<AVObject>("_User")
     //   o.whereEqualTo("doctorCertification",false)
        o.findInBackground(object :FindCallback<AVObject>(){
            override fun done(list: MutableList<AVObject>?, e: AVException?) {
                if (e == null){
                    list?.forEach {
                       with(it){
                           val name:String = getString("username")
                           val avatar = getString("avatar")
                           val qualification:String = getString("qualification")?:"未知"
                           val education:String = getString("education")?:"未知"
                           val doctorCertification:Boolean = getBoolean("doctorCertification")?:false
                           val authentication:Boolean = getBoolean("authentication")?:false
                           val workerTime:String = getString("workerTime")?:"未知"
                           val graduatedSchool:String = getString("graduatedSchool")?:"未知"
                           val workerAddress:String = getString("workerAddress")?:"未知"
                           val phone:String = getString("phone")?:"未知"
                           val historyOrderCount:Int = getInt("historyOrder")?:0
                           val goodAt:String = getString("goodAt")?:"未知"
                           val praise:Float = getDouble("praise").toFloat()
                           val country:String = getString("country")?:""
                           val province:String = getString("province")?:""
                           val city:String = getString("city")?:"未知"
                           val district:String = getString("district")?:""
                           val streetNumber:String = getString("street")?:""
                           val latitude:Double = getDouble("latitude")
                           val longitude:Double = getDouble("longitude")
                           val description = getString("description")?:"未知"
                           val attention = UserManage.atttention
                           var isAttention = false
                           if (attention.contains(objectId)){
                               isAttention = true
                           }
                           val position = Position(country,province,city,district,streetNumber,description,latitude,longitude)
                           val doctor = Doctor(objectId,name,avatar,position,qualification,education,doctorCertification,
                               authentication,workerTime,graduatedSchool,workerAddress,phone,historyOrderCount,goodAt,praise,isAttention)
                           repository?.addDoctor(doctor)
                           doctorMap[objectId] = doctor
                           requestCallback(null)
                       }
                    }
                }else{
                    requestCallback(e)
                }
            }
        })
    }

    private fun findDoctorByNet(doctorId:String,findCallback:(doctor:Doctor?)->Unit){
        val o = AVObject.createWithoutData("_User",doctorId)
        o.fetchInBackground(object :GetCallback<AVObject>(){
            override fun done(o: AVObject?, e: AVException?) {
                if (e == null){
                    with(o!!){
                        val name:String = getString("username")
                        val avatar:String = getString("avatar")
                        val qualification:String = getString("qualification")
                        val education:String = getString("education")
                        val doctorCertification:Boolean = getBoolean("doctorCertification")
                        val authentication:Boolean = getBoolean("authentication")
                        val workerTime:String = getString("workerTime")
                        val graduatedSchool:String = getString("graduatedSchool")
                        val workerAddress:String = getString("workerAddress")
                        val phone:String = getString("phone")
                        val historyOrderCount:Int = getInt("historyOrder")
                        val goodAt:String = getString("goodAt")
                        val praise:Float = getDouble("praise").toFloat()
                        val country:String = getString("country")
                        val province:String = getString("province")
                        val city:String = getString("city")
                        val district:String = getString("district")
                        val streetNumber:String = getString("streetNumber")
                        val latitude:Double = getDouble("latitude")
                        val longitude:Double = getDouble("longitude")
                        val description  = getString("description")
                        val attention = UserManage.atttention
                        var isAttention = false
                        if (attention.contains(objectId)){
                            isAttention = true
                        }
                        val position = Position(country,province,city,district,streetNumber,description,latitude,longitude)
                        val doctor = Doctor(doctorId,name,avatar,position,qualification,education,doctorCertification,
                            authentication,workerTime,graduatedSchool,workerAddress,phone,historyOrderCount,goodAt,praise,isAttention)
                        repository?.addDoctor(doctor)
                        doctorMap[doctorId] = doctor
                        findCallback(doctor)
                    }
                }else{
                    findCallback(null)
                }
            }
        })
    }

    fun update(context: Context,doctor: Doctor){
        initRepository(context)
        repository?.addDoctor(doctor)
    }

    private fun initRepository(context: Context){
        if (repository == null)
        repository = DoctorRepository.getInstance(AppDatabase.getInstance(context).getClinicDao())
    }
}