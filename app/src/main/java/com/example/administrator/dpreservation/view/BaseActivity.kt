package com.example.administrator.dpreservation.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import com.example.administrator.dpreservation.R
import com.example.administrator.dpreservation.utilities.Util

 open class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Util.setStatusBar(this)
    }

     protected fun setActionBar(toolbar: Toolbar){
         setSupportActionBar(toolbar)
         supportActionBar?.setDisplayHomeAsUpEnabled(true)
         supportActionBar?.setHomeButtonEnabled(true)
         supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_chevron_left_black_36dp)
         toolbar.setNavigationOnClickListener {
             onBackPressed()
         }
     }
}
