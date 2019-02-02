package com.example.administrator.dpreservation.presenter

import android.view.View
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.avos.avoscloud.AVException
import com.avos.avoscloud.AVUser
import com.avos.avoscloud.RequestPasswordResetCallback
import com.example.administrator.dpreservation.utilities.Util
import com.example.administrator.dpreservation.view.LoginActivity

class FindPasswordPresenter(@Bindable var mailBox:String):BaseObservable(){

    fun find(view: View){
        if (mailBox.isNullOrBlank()){
            Util.log(view,"邮箱不能为空")
        }
        AVUser.requestPasswordResetInBackground(mailBox,object :RequestPasswordResetCallback(){

            override fun done(e: AVException?) {
                if (e == null){
                    Util.toActivity<LoginActivity>(view.context)
                }else{
                    Util.log(view,e.message)
                }
            }
        })
    }
}