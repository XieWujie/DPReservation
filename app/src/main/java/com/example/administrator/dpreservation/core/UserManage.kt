package com.example.administrator.dpreservation.core

import android.content.Context
import android.view.View
import com.avos.avoscloud.*
import com.avos.avoscloud.im.v2.AVIMClient
import com.avos.avoscloud.im.v2.AVIMException
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback
import com.example.administrator.dpreservation.data.AppDatabase
import com.example.administrator.dpreservation.data.Position
import com.example.administrator.dpreservation.data.user.User
import com.example.administrator.dpreservation.data.user.UserRepository
import com.example.administrator.dpreservation.utilities.AVATAR
import com.example.administrator.dpreservation.utilities.Util
import java.util.*

object UserManage{

    var respository:UserRepository? = null
    var client:AVIMClient? = null
    var user:User? = null
    var position:Position? = null

    fun login(view: View, username:String,password:String,loginCallback:(user:User?)->Unit){
        initRespository(view.context)
        AVUser.logInInBackground(username,password,object : LogInCallback<AVUser>(){
            override fun done(avUser: AVUser?, e: AVException?) {
                if (e==null){
                    val  c = AVIMClient.getInstance(avUser!!.objectId)
                    c.open(object : AVIMClientCallback(){
                        override fun done(c: AVIMClient?, e: AVIMException?) {
                            if (e==null){
                                val avatar = avUser.getString(AVATAR)
                                val mailBox = avUser.getString("email")
                                 val u = User(avUser.objectId,username,password, Date().time,mailBox,false,avatar)
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
}