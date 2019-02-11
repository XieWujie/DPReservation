package com.example.administrator.dpreservation.view

import android.icu.util.TimeZone
import android.os.Build
import android.os.Bundle
import android.text.format.DateUtils
import android.util.Log
import android.util.TimeUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.DatePicker
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.administrator.dpreservation.core.OrderManage
import com.example.administrator.dpreservation.core.UserManage
import com.example.administrator.dpreservation.data.doctor.Doctor
import com.example.administrator.dpreservation.data.order.Order
import com.example.administrator.dpreservation.databinding.FragmentRegisterBinding
import com.example.administrator.dpreservation.databinding.FragmentReservationBinding
import com.example.administrator.dpreservation.utilities.Util
import com.example.administrator.dpreservation.utilities.ViewModelFactory
import com.example.administrator.dpreservation.viewmodel.OrderModel
import java.sql.Timestamp
import java.util.*
import kotlin.collections.ArrayList

class ReservationFragment:Fragment(){

    private lateinit var binding:FragmentReservationBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentReservationBinding.inflate(inflater,container,false)
        val doctor = activity?.intent?.getSerializableExtra("doctor")
        if (doctor is Doctor){
            activity?.setTitle(doctor.name)
            OrderManage.requestDoctorOrder(requireContext(),doctor){

            }
        }
        val CD = Calendar.getInstance()
        val YY = CD.get(Calendar.YEAR)
        val MM = CD.get(Calendar.MONTH)
        val DD = CD.get(Calendar.DATE)
        val HH = CD.get(Calendar.HOUR)
        val m = CD.get(Calendar.MINUTE)
        val timeView = binding.timePicker
        val dateView = binding.datePicker
        val cTime = Date(YY-1900,MM,DD,HH,m).time
        dateView.updateDate(YY,MM,DD)
        if (Build.VERSION.SDK_INT>=23){
            timeView.hour = HH
            timeView.minute = m
        }
        binding.button.setOnClickListener {
            val user =UserManage.user
            if (user == null ){
                Util.log(it,"请先登陆")
            }else if (!(doctor is Doctor)){
               Util.log(it,"获取信息失败")
            }else{
                var time = if (Build.VERSION.SDK_INT>=23) {
                    Date(dateView.year-1900, dateView.month, dateView.dayOfMonth, timeView.hour, timeView.minute).time
                }else{
                    Date(dateView.year-1900,dateView.month,dateView.dayOfMonth).time
                }
                if (cTime>time){
                    Util.log(binding.root,"请往后预约")
                }else{
                val dialog = Util.createProgressDialog(it.context)
                dialog.show()
                it.isClickable = false
                OrderManage.createAnOrder(requireContext(),user,doctor as Doctor,time,binding.descriptionView.text.toString()) { e ->
                    dialog.dismiss()
                    it.isClickable = true
                    if (e == null) {
                        Util.toActivity<OrderActivity>(it.context)
                        activity?.finish()
                    } else {
                        Util.log(binding.root, e?.message)
                    }
                }
                }
            }
        }
        return binding.root
    }
}