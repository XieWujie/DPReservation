package com.example.administrator.dpreservation.view

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.onNavDestinationSelected
import com.example.administrator.dpreservation.R
import com.example.administrator.dpreservation.databinding.ActivityMessageBinding

class MessageActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       val binding =  DataBindingUtil.setContentView<ActivityMessageBinding>(this,R.layout.activity_message)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setTitle("消息")
        binding.toolbar.setTitleTextColor(Color.WHITE)
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
        navController = findNavController(R.id.m_nav)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.message_menu,menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return item?.onNavDestinationSelected(navController)?:false||super.onOptionsItemSelected(item)
    }
}
