package com.example.administrator.dpreservation.presenter

import android.app.Activity
import android.content.Intent
import android.view.View
import android.widget.TextView
import androidx.navigation.findNavController
import com.example.administrator.dpreservation.R
import com.example.administrator.dpreservation.core.MessageManage
import com.example.administrator.dpreservation.core.UserManage
import com.example.administrator.dpreservation.data.AppDatabase
import com.example.administrator.dpreservation.data.cache.GlideCacheUtil
import com.example.administrator.dpreservation.data.user.UserRepository
import com.example.administrator.dpreservation.utilities.Util
import com.example.administrator.dpreservation.utilities.runOnNewThread
import com.example.administrator.dpreservation.view.MainActivity

class SettingPresenter{

    fun acountAndSafety(view: View){
        view.findNavController().navigate(R.id.action_settingFragment_to_acountAndSafetyFragment)
    }


    fun clearCache(view: View){
        val text = view.findViewById<TextView>(R.id.cacheSize)
        val context = view.context
        val size = GlideCacheUtil.getCacheSize(context)
        var isSucceed = false
        GlideCacheUtil.clearImageAllCache(context){ e->
            if (e == null){
                isSucceed = true
            }else{
                isSucceed = true
                Util.log(view,"清除磁盘缓存失败")
            }
        }
        while (true){
            if (isSucceed){
                text.text = GlideCacheUtil.getCacheSize(context)
                Util.log(view,"清除成功")
                break
            }
        }
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
        runOnNewThread {
            AppDatabase.getInstance(view.context).clearAllTables()
            val intent = Intent(view.context,MainActivity::class.java)
            UserManage?.logout()
             MessageManage?.logout()
            view.context.startActivity(intent)
        }
    }
}