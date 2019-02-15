package com.example.administrator.dpreservation.view

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import com.example.administrator.dpreservation.R
import com.example.administrator.dpreservation.utilities.Util

 open class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

     protected fun setWhiteBar(toolbar: Toolbar){
         setSupportActionBar(toolbar)
         Util.setStatusBar(this,Color.WHITE)
         supportActionBar?.setDisplayHomeAsUpEnabled(true)
         supportActionBar?.setHomeButtonEnabled(true)
         supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_black_back)
         toolbar.setNavigationOnClickListener {
             onBackPressed()
         }
     }

     protected fun setBlueBar(toolbar: Toolbar){
         setSupportActionBar(toolbar)
         Util.setStatusBar(this,resources.getColor(R.color.blue_toolbar))
         supportActionBar?.setDisplayHomeAsUpEnabled(true)
         supportActionBar?.setHomeButtonEnabled(true)
         toolbar.setTitleTextColor(Color.WHITE)
         supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_white_back)
         toolbar.setNavigationOnClickListener {
             onBackPressed()
         }
     }
}
