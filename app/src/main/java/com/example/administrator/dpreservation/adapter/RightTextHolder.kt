package com.example.administrator.dpreservation.adapter


import android.view.View
import com.example.administrator.dpreservation.core.MessageManage
import com.example.administrator.dpreservation.data.message.Message
import com.example.administrator.dpreservation.databinding.RightLayoutTextBinding
import com.example.administrator.dpreservation.utilities.SENDING
import com.example.administrator.dpreservation.utilities.SEND_FAIL
import com.example.administrator.dpreservation.utilities.SEND_SUCCEED


class RightTextHolder(val bind:RightLayoutTextBinding):BaseHolder(bind.root){

    override fun bind(any: Any) {
        if (any is Message){
            when(any.sendState){
                SENDING ->{
                    bind.chatRightProgressbar.visibility = View.VISIBLE
                    bind.chatRightTvError.visibility = View.GONE
                }
                SEND_FAIL ->{
                    bind.chatRightProgressbar.visibility = View.GONE
                    bind.chatRightTvError.visibility = View.VISIBLE
                    bind.chatRightTvError.setOnClickListener {
                        resendEvent(any)
                        it.visibility = View.GONE
                        bind.chatRightProgressbar.visibility = View.VISIBLE
                    }
                }
                SEND_SUCCEED ->{
                    bind.chatRightProgressbar.visibility = View.GONE
                    bind.chatRightTvError.visibility = View.GONE
                }
            }
            bind.message = any
        }
    }

    private fun resendEvent(message: Message){
        MessageManage.sendMessage(message){

        }
    }
}