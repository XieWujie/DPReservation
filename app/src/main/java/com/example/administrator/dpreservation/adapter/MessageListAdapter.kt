package com.example.administrator.dpreservation.adapter

import android.app.AlertDialog
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import com.example.administrator.dpreservation.core.MessageManage
import com.example.administrator.dpreservation.core.UserManage
import com.example.administrator.dpreservation.data.message.Message
import com.example.administrator.dpreservation.databinding.MessageItemBinding
import com.example.administrator.dpreservation.utilities.*
import com.example.administrator.dpreservation.view.ChatActivity

class MessageListAdapter:PagedListAdapter<Message,BaseHolder>(MessageDiffCallback()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder {
        val binding = MessageItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MessageListHolder(binding)
    }

    override fun onBindViewHolder(holder: BaseHolder, position: Int) {
       holder.bind(getItem(position)!!)
    }
}