package com.example.administrator.dpreservation.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.administrator.dpreservation.R
import com.example.administrator.dpreservation.data.doctor.Doctor
import com.example.administrator.dpreservation.databinding.ActivityDoctorBinding

class DoctorActivity : AppCompatActivity() {

    private lateinit var binding:ActivityDoctorBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_doctor)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        navController = findNavController(R.id.doc_nav)
        val doctor = intent.getBundleExtra("doctor")
        navController.addOnNavigatedListener { controller, destination ->
            when(destination.id) {
               R.id.doctorDetailFragment-> if (doctor is Bundle) {
                    destination.setDefaultArguments(doctor)
                }
            }
        }
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
