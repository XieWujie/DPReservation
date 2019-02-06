package com.example.administrator.dpreservation.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.UserManager
import android.view.MenuItem
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.administrator.dpreservation.R
import com.example.administrator.dpreservation.core.DoctorManager
import com.example.administrator.dpreservation.core.UserManage
import com.example.administrator.dpreservation.data.doctor.Doctor
import com.example.administrator.dpreservation.databinding.ActivityDoctorBinding
import com.example.administrator.dpreservation.utilities.Util

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
        binding.sendEvaluation.setOnClickListener {
            navController.navigate(R.id.action_doctorDetailFragment_to_reservationFragment)
        }
        navController.addOnNavigatedListener { controller, destination ->
            when(destination.id){
                R.id.doctorDetailFragment->binding.bottom.visibility = View.VISIBLE
                R.id.reservationFragment->binding.bottom.visibility = View.GONE
            }
        }
        init()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            android.R.id.home->if (!navController.navigateUp()){
                finish()
            }
        }
        return true
    }

    fun init(){
        var doctor = intent.getSerializableExtra("doctor")
        if (doctor is Doctor){
            if (doctor.isAttention){
                binding.attention.text = "取消关注"
            }else{
                binding.attention.text = "添加关注"
            }
            binding.attention.setOnClickListener {
                val dialog = Util.createProgressDialog(this)
                when(binding.attention.text){
                    "添加关注"->{
                        UserManage.addAttention(this,doctor.id){
                            if (it == null){
                                DoctorManager.update(this,doctor.copy(isAttention = true))
                                binding.attention.text = "取消关注"
                                dialog.dismiss()
                            }else{
                                Util.log(binding.root,"更新失败")
                            }
                        }
                    }
                    "取消关注"->UserManage.removeAttention(this,doctor.id){
                        if (it == null){
                            DoctorManager.update(this,doctor.copy(isAttention = false))
                            binding.attention.text = "添加关注"
                            dialog.dismiss()
                        }else{
                            Util.log(binding.root,"更新失败")
                        }
                    }
                }
            }
        }
    }
}
