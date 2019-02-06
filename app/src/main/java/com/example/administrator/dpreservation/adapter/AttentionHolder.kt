package com.example.administrator.dpreservation.adapter

import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import com.example.administrator.dpreservation.data.doctor.Doctor
import com.example.administrator.dpreservation.databinding.AttentionListItemBinding
import com.example.administrator.dpreservation.utilities.AVATAR
import com.example.administrator.dpreservation.utilities.CONVERSATION_ID
import com.example.administrator.dpreservation.utilities.CONVERSATION__NAME
import com.example.administrator.dpreservation.view.ChatActivity

class AttentionHolder(val binding:AttentionListItemBinding):BaseHolder(binding.root){

    override fun bind(any: Any) {
        if (any is Doctor){
            binding.doctor = any
            binding.root.setOnClickListener {
                val context = it.context
                val intent = Intent(context,ChatActivity::class.java)
                intent.putExtra(CONVERSATION__NAME,any.name)
                intent.putExtra(CONVERSATION_ID,any.id)
                intent.putExtra(AVATAR,any.avatar)
                context.startActivity(intent)
            }
        }
    }
}