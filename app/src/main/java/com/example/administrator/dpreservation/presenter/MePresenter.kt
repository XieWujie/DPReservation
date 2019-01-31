package com.example.administrator.dpreservation.presenter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import com.example.administrator.dpreservation.view.OrderActivity
import com.example.administrator.dpreservation.view.SettingActivity

class MePresenter{

    fun clickOrder(view:View){
        toActivity<OrderActivity>(view.context)
    }

    fun clickWallet(view: View){

    }

    fun clickSafety(view: View){

    }

    fun clickCustomer(view: View){

    }

    fun clickSetting(view: View){
        toActivity<SettingActivity>(view.context)
    }

    private inline fun <reified T> toActivity(context: Context){
        val intent = Intent(context,T::class.java)
        context.startActivity(intent)
    }
}