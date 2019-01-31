package com.example.administrator.dpreservation.view

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.administrator.dpreservation.databinding.FragmentStartBinding
import android.content.pm.PackageManager
import android.os.Handler
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.administrator.dpreservation.R
import com.google.android.material.snackbar.Snackbar


class StartFragment:Fragment(){

    private lateinit var binging :FragmentStartBinding
    private lateinit var navController: NavController

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binging = FragmentStartBinding.inflate(LayoutInflater.from(context),null,false)
        return binging.root
    }

    override fun onResume() {
        super.onResume()
        requestPermission()
    }

    private fun requestPermission() {
        val context = requireContext()
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
            || ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
            || ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
            || ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.READ_PHONE_STATE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                activity as FragmentActivity,
                arrayOf(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.READ_PHONE_STATE
                ), 1
            )
        } else {
            val run = Runnable {
                toMapFragment()
            }
            Handler().postDelayed(run,1000)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            toMapFragment()
        } else {
            Snackbar.make(binging.root, "拒绝权限将不能正常使用该功能", Snackbar.LENGTH_LONG).show()
        }
    }

    private fun toMapFragment(){
       val intent = Intent(requireContext(),MainActivity::class.java)
        startActivity(intent)
    }
}