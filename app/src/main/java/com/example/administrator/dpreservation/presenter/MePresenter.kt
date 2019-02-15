package com.example.administrator.dpreservation.presenter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.example.administrator.dpreservation.utilities.CONVERSATION_ID
import com.example.administrator.dpreservation.utilities.CONVERSATION__NAME
import com.example.administrator.dpreservation.view.ChatActivity
import com.example.administrator.dpreservation.view.LoginActivity
import com.example.administrator.dpreservation.view.OrderActivity
import com.example.administrator.dpreservation.view.SettingActivity

class MePresenter(var avatar:String? = null ,var acount:String = "登陆"){

    fun clickOrder(view:View){
        toActivity<OrderActivity>(view.context)
    }

    fun clickWallet(view: View){

    }

    fun login(view: View){
        if (acount == "登陆"){
            toActivity<LoginActivity>(view.context)
        }
    }

    fun clickSafety(view: View){

    }

    fun clickCustomer(view: View){
        val context = view.context
        val builder = AlertDialog.Builder(context)
        val a = arrayOf("拨打电话","发送消息")
        builder.setItems(a){d,w->
            when(w){
                0->{
                    val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:13677626587"))
                    context.startActivity(intent)
                }
                1->{
                    val id = "5c66404f303f390047fd1867"
                    val intent = Intent(context,ChatActivity::class.java)
                    intent.putExtra(CONVERSATION__NAME,"客服")
                    intent.putExtra(CONVERSATION_ID,id)
                    context.startActivity(intent)
                }
            }
        }.setCancelable(true).show()
    }

    fun clickSetting(view: View){
        toActivity<SettingActivity>(view.context)
    }

    private inline fun <reified T> toActivity(context: Context){
        val intent = Intent(context,T::class.java)
        context.startActivity(intent)
    }
}