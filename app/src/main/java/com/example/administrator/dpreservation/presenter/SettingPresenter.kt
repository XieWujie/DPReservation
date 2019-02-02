package com.example.administrator.dpreservation.presenter

import android.app.Activity
import android.content.Intent
import android.view.View
import androidx.navigation.findNavController
import com.example.administrator.dpreservation.R
import com.example.administrator.dpreservation.core.MessageManage
import com.example.administrator.dpreservation.core.UserManage
import com.example.administrator.dpreservation.data.user.UserRepository
import com.example.administrator.dpreservation.view.MainActivity

class SettingPresenter{

    fun acountAndSafety(view: View){
        view.findNavController().navigate(R.id.action_settingFragment_to_acountAndSafetyFragment)
    }

    fun switchLanguage(view: View){

    }

    fun clearCache(view: View){

    }

    fun userGuide(view: View){

    }

    fun feedback(view: View){

    }

    fun versionUpdate(view: View){

    }

    fun about(view: View){

    }

    fun logout(view: View){
        UserManage?.logout()
        MessageManage?.logout()
        val intent = Intent(view.context,MainActivity::class.java)
        view.context.startActivity(intent)
    }
}