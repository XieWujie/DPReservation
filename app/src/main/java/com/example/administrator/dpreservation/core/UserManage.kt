package com.example.administrator.dpreservation.core

import android.content.Context
import android.view.View
import com.avos.avoscloud.*
import com.avos.avoscloud.im.v2.AVIMClient
import com.avos.avoscloud.im.v2.AVIMException
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback
import com.example.administrator.dpreservation.data.AppDatabase
import com.example.administrator.dpreservation.data.Position
import com.example.administrator.dpreservation.data.doctor.Doctor
import com.example.administrator.dpreservation.data.user.User
import com.example.administrator.dpreservation.data.user.UserRepository
import com.example.administrator.dpreservation.utilities.AVATAR
import com.example.administrator.dpreservation.utilities.Util
import java.util.*
import kotlin.collections.ArrayList

object UserManage{

    var respository:UserRepository? = null
    var client:AVIMClient? = null
    var user:User? = null
    var position:Position? = null
    val atttention = ArrayList<String>()
    var avUser:AVUser? = null

    fun login(view: View, username:String,password:String,loginCallback:(user:User?)->Unit){
        initRespository(view.context)
        AVUser.logInInBackground(username,password,object : LogInCallback<AVUser>(){
            override fun done(aU: AVUser?, e: AVException?) {
                if (e==null){
                    aU!!
                    avUser = aU
                    val  c = AVIMClient.getInstance(avUser!!.objectId)
                    c.open(object : AVIMClientCallback(){
                        override fun done(c: AVIMClient?, e: AVIMException?) {
                            if (e==null){
                                val avatar = aU.getString(AVATAR)
                                val mailBox = aU.getString("email")
                                val a = aU.getList("attention")
                                if (a != null) {
                                    atttention.clear()
                                    atttention.addAll(a.map { it as String })
                                }
                                 val u = User(aU.objectId,username,password, Date().time,mailBox,false,avatar)
                                respository?.addUser(u)
                                user = u
                                client = c
                                loginCallback(user)
                                return
                            }else{
                                Util.log(view,e.message)
                                loginCallback(null)
                            }
                        }
                    })

                }else{
                    Util.log(view,e?.message)
                    loginCallback(null)
                }
            }

        })
    }

     fun register( view: View,username:String,password:String,mailBox:String,callback:(user:User?)->Unit) {
         initRespository(view.context)
        val user = AVUser()
        user.username = username
        user.setPassword(password)
        user.email = mailBox
        user.signUpInBackground(object : SignUpCallback(){
            override fun done(e: AVException?) {
                if (e!=null){
                    callback(null)
                    Util.log(view,e.message)

                }else{
                    avUser = user
                    val id = user.objectId
                    val  c = AVIMClient.getInstance(id)
                    c.open(object : AVIMClientCallback(){
                        override fun done(c: AVIMClient?, e: AVIMException?) {
                            if (e==null){
                                val u = User(id,username,password, Date().time,mailBox,false,null)
                                respository?.addUser(u)
                                UserManage.user = u
                                client = c
                                callback(u)
                                return
                            }else{
                                Util.log(view,e.message)
                                callback(null)
                            }
                        }
                    })

                }
            }

        })
    }



    private fun initRespository(context: Context){
        if (respository == null)
        respository = UserRepository.getInstance(AppDatabase.getInstance(context).getUserDao())
    }

     fun setAvatar(path: String,upDateCallback:(e:Exception?)->Unit) {
         if (user == null)return
        val avFile = AVFile.withAbsoluteLocalPath("${user!!.name}.jpg",path)
        avFile.saveInBackground(object :SaveCallback(){
            override fun done(e: AVException?) {
                if (e == null){
                    val o = AVObject.createWithoutData("_User", user!!.userId)
                    o.put(AVATAR,avFile.url)
                    o.saveInBackground()
                    val u = user!!.copy(avatar = avFile.url)
                    respository?.updata(u)
                    user = u
                    upDateCallback(null)
                }else{
                    upDateCallback(e)
                }
            }
        })
    }

    fun logout(){
        if (user == null)return
        respository?.deleteUser(user!!)
        user = null
        client?.close(null)
        client = null
    }

    fun addAttention(context: Context,doctorId:String,addCallback:(e:Exception?)->Unit){
        initRespository(context)
        val o = AVObject.createWithoutData("_User", user!!.userId)
        o.add("attention",doctorId)
        o.saveInBackground(object :SaveCallback(){
            override fun done(e: AVException?) {
                if (e == null){
                    atttention.add(doctorId)
                }
                addCallback(e)
            }
        })
    }

    fun removeAttention(context: Context,doctorId: String,removeCallback: (e: Exception?) -> Unit){
        initRespository(context)
        val o = AVObject.createWithoutData("_User", user!!.userId)
        if (atttention == null){
            removeCallback(null)
            return
        }
        atttention.remove(doctorId)
        o.remove("attention")
        o.addAllUnique("attention", atttention)
        o.saveInBackground(object :SaveCallback(){
            override fun done(e: AVException?) {
                removeCallback(e)
            }
        })
    }

}