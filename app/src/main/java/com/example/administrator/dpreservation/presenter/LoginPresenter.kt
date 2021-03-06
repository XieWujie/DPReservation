package com.example.administrator.dpreservation.presenter

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.os.UserManager
import android.util.Log
import android.view.View
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import com.avos.avoscloud.AVUser
import com.example.administrator.dpreservation.R
import com.example.administrator.dpreservation.core.MessageManage
import com.example.administrator.dpreservation.core.UserManage
import com.example.administrator.dpreservation.utilities.Util
import com.example.administrator.dpreservation.view.FindPasswordActivity
import com.example.administrator.dpreservation.view.MainActivity
import com.google.android.material.snackbar.Snackbar
import java.lang.ref.WeakReference

class LoginPresenter(
    @Bindable var userName:String,
    @Bindable var password:String
): BaseObservable() {

    var progressDialog: Dialog? = null
    var activity: WeakReference<FragmentActivity>? = null
    fun login(view: View) {
        if (userName.isNullOrBlank()) {
            Snackbar.make(view, "用户名不能为空", Snackbar.LENGTH_LONG).show()
            return
        }
        if (progressDialog == null) {
            progressDialog = Util.createProgressDialog(view.context)
        }
        progressDialog?.show()
        view.isClickable = false
        UserManage.login(view,userName,password){
            progressDialog?.dismiss()
            view.isClickable = true
            if (it != null){
                MessageManage.init(view.context,it)
                Util.toActivity<MainActivity>(view.context)
            }
        }

    }

    fun register(view: View) {
        view.findNavController().navigate(R.id.action_logInFragment_to_registerFragment)
    }

    fun findPassword(view: View){
        Util.toActivity<FindPasswordActivity>(view.context)
    }

}