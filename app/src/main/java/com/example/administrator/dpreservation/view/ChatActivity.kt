package com.example.administrator.dpreservation.view

import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import com.example.administrator.dpreservation.R
import com.example.administrator.dpreservation.core.MessageManage
import com.example.administrator.dpreservation.databinding.ActivityChatBinding
import com.example.administrator.dpreservation.utilities.AVATAR
import com.example.administrator.dpreservation.utilities.CONVERSATION_ID
import com.example.administrator.dpreservation.utilities.CONVERSATION__NAME
import com.example.administrator.dpreservation.utilities.Util

class ChatActivity : AppCompatActivity(){

    private lateinit var chatFragment: ChatFragment
    private lateinit var binding: ActivityChatBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_chat)
        chatFragment = supportFragmentManager.findFragmentById(R.id.chat_fragment) as ChatFragment
        initView()
        initConversation()
    }

    private fun initConversation(){
        val conversationId = intent.getStringExtra(CONVERSATION_ID)
        val conversationName = intent.getStringExtra(CONVERSATION__NAME)
        val avatar = intent.getStringExtra(AVATAR)
        MessageManage.finConversationById(conversationId) { conversation ->
            Util.findConversationTitle(conversation, conversationName) {
                binding.centerText.text = it
                setTitle("消息")
                chatFragment.begin(conversationId, it, avatar, conversation)
            }
        }
    }

    private fun initView(){
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_chevron_left_black_24dp)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            android.R.id.home->finish()
        }
        return true
    }
}

