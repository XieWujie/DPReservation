package com.example.administrator.dpreservation.presenter

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.view.View
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.example.administrator.dpreservation.core.UserManage
import com.example.administrator.dpreservation.utilities.Util
import com.example.administrator.dpreservation.view.MainActivity
import com.google.android.material.snackbar.Snackbar
import java.lang.Exception

data class RegisterPresenter(
    @Bindable var userName:String = "",
    @Bindable var firstPassword:String = "",
    @Bindable var secondPassword:String = "",
    @Bindable var mailBox:String = ""
): BaseObservable(){

    private var progressDialog: Dialog? = null

    fun register(view: View){
        if (checkMailBox(view)){
            if (progressDialog == null){
                progressDialog = Util.createProgressDialog(view.context)
            }
            progressDialog?.show()
            view.isClickable = false
            UserManage.register(view,userName,firstPassword,mailBox){
                progressDialog?.dismiss()
                view.isClickable = true
                if (it != null){
                    Util.toActivity<MainActivity>(view.context)
                }
            }
        }
    }

    private fun firstLogin(view: View){

    }

    private fun checkPasswordSame(view: View):Boolean{
        if (firstPassword != secondPassword){
            Snackbar.make(view,"两次密码不一样", Snackbar.LENGTH_LONG).show()
            return false
        }
        return true
    }
    private fun checkMailBox(view: View):Boolean{

        return true
    }
}