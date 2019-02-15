package com.example.administrator.dpreservation.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.onNavDestinationSelected
import com.example.administrator.dpreservation.R
import com.example.administrator.dpreservation.databinding.ActivityMessageBinding

class MessageActivity : BaseActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding =  DataBindingUtil.setContentView<ActivityMessageBinding>(this,R.layout.activity_message)
        setBlueBar(binding.toolbar)
        setTitle("消息")
        navController = findNavController(R.id.m_nav)
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.message_menu,menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return item?.onNavDestinationSelected(navController)?:false||super.onOptionsItemSelected(item)
    }
}
