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

class SettingActivity : BaseActivity(){

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivitySettingBinding>(this,R.layout.activity_setting)
        setSupportActionBar(binding.toolbar)
        setTitle("设置")
        setActionBar(binding.toolbar)
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

