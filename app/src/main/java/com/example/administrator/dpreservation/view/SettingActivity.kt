package com.example.administrator.dpreservation.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.administrator.dpreservation.R
import com.example.administrator.dpreservation.databinding.ActivitySettingBinding

class SettingActivity : AppCompatActivity(){

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivitySettingBinding>(this,R.layout.activity_setting)
        setSupportActionBar(binding.toolbar)
        setTitle("设置")
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_chevron_left_black_24dp)
        navController = findNavController(R.id.setting_nav)
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            android.R.id.home->if (!navController.navigateUp()){
                finish()
            }
        }
        return true
    }
}

