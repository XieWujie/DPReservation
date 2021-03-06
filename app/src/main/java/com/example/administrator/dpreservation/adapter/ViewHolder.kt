package com.example.administrator.dpreservation.adapter

import android.app.AlertDialog
import android.content.Intent
import com.example.administrator.dpreservation.core.MessageManage
import com.example.administrator.dpreservation.core.UserManage
import com.example.administrator.dpreservation.data.message.Message
import com.example.administrator.dpreservation.databinding.MessageItemBinding
import com.example.administrator.dpreservation.utilities.*
import com.example.administrator.dpreservation.view.ChatActivity

class ViewHolder(val binding: MessageItemBinding):BaseHolder(binding.root){

        val owenrId = UserManage.user?.userId!!
        val a = 8*3600000

        override fun bind(any: Any) {
            if (any is Message){
                if (any.fromId == owenrId){
                    binding.message =  any.copy(createAt = any.createAt)
                }else{
                    binding.message = any
                }
                val t = binding.contentText
                when(any.type){
                    IMAGE_MESSAGE ->t.text = "图片"
                    TEXT_MESSAGE ->t.text = any.message
                    VOICE_MESSAGE->t.text = "语音"
                }
                binding.root.setOnClickListener {
                    val context = binding.root.context
                    val intent = Intent(context, ChatActivity::class.java)
                    intent.putExtra(CONVERSATION_ID,any.conversationId)
                    intent.putExtra(CONVERSATION__NAME,any.conversationName)
                    intent.putExtra(AVATAR,any.avatar)
                    val newMessage = any.copy(unReadCount = 0)
                    MessageManage.cacheMessage(newMessage,true)
                    context.startActivity(intent)
                }
                binding.root.setOnLongClickListener {
                    val dialog =  AlertDialog.Builder(binding.root.context)
                        .setItems(arrayOf("删除消息")){ d,positon->
                            when(positon){
                                0->MessageManage.deleteMessages(any.conversationId)
                            }
                        }.show()
                    true
                }
            }
        }
    }