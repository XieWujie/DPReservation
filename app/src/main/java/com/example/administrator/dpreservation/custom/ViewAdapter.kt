package com.example.administrator.dpreservation.custom

import android.text.format.DateUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.amap.api.location.CoordinateConverter
import com.amap.api.location.DPoint
import com.amap.api.maps2d.AMapUtils
import com.amap.api.maps2d.model.LatLng
import com.bumptech.glide.Glide
import com.example.administrator.dpreservation.R
import com.example.administrator.dpreservation.core.UserManage
import com.example.administrator.dpreservation.data.Position
import java.text.SimpleDateFormat
import java.util.*

class ViewAdapter{
    companion object {

        @JvmStatic
        @BindingAdapter("imageSrc")
        fun setImage(view: ImageView, src:String?){
            if (src.isNullOrBlank()){
                view.setImageResource(R.drawable.doctor_default_avatar)
            }else {
                Glide.with(view)
                    .load(src)
                    .into(view)
            }
        }

        @JvmStatic
        @BindingAdapter("patient_image")
        fun setDoctorImage(view: ImageView, src:String?){
            if (src.isNullOrBlank()){
                view.setImageResource(R.drawable.patient_default_avatar)
            }else {
                Glide.with(view)
                    .load(src)
                    .into(view)
            }
        }

        @JvmStatic
        @BindingAdapter("orderTime")
        fun setOrderTime(view: TextView,timeStamp: Long){
            val d = Date(timeStamp)
            val f = SimpleDateFormat("MM月dd日 HH:mm")
            val s = f.format(d)
            view.text = s
        }

        @JvmStatic
        @BindingAdapter("distance")
        fun setDistance(view: TextView,position: Position){
            val o = UserManage.position
            if (o == null)return
            val dPoint = LatLng(position.latitude,position.longitude)
            val mPoint = LatLng(o.latitude,o.longitude)
            val distance = AMapUtils.calculateLineDistance(dPoint,mPoint)
            if (distance>1000){
                view.text = "${(distance/100)/10f}千米"
            }else {
                view.text = "$distance 米"
            }
        }


        const val a = 3600000
        var lastTime = 0L
        @JvmStatic
        @BindingAdapter("time")
        fun setTime(view: TextView, timeStamp:Long){
            if (timeStamp - lastTime > a) {
                lastTime = timeStamp
                val d = Date(timeStamp)
                if (DateUtils.isToday(timeStamp)) {
                    val c = if (GregorianCalendar()[GregorianCalendar.AM_PM] == 0) "上午" else "下午"
                    val f = SimpleDateFormat("hh:mm:ss")
                    val s = f.format(d)
                    view.text = "$c $s"
                } else {
                    val f = SimpleDateFormat("MM-dd HH:mm")
                    val s = f.format(d)
                    view.text = s
                }
            }
        }



        @JvmStatic
        @BindingAdapter("message_list_time")
        fun setMessageListTime(view: TextView, timeStamp: Long){
            val d = Date(timeStamp)
            if (DateUtils.isToday(timeStamp)) {
                val c = if (GregorianCalendar()[GregorianCalendar.AM_PM] == 0) "上午" else "下午"
                val f = SimpleDateFormat("hh:mm:ss")
                val s = f.format(d)
                view.text = "$c $s"
            } else {
                val f = SimpleDateFormat("MM-dd HH:mm")
                val s = f.format(d)
                view.text = s
            }
        }

        @JvmStatic
        @BindingAdapter("unReadCount")
        fun setUnReadCount(view: TextView, count:Int){
            if (count>0) {
                view.text = count.toString()
            }
        }

        @JvmStatic
        @BindingAdapter("voiceTime")
        fun setVoiceTime(view: TextView, time:Double){
            val l = time.toString().split(".")
            if (l.size == 2){
                val t = """${l[0]}"${l[1].toInt()/100}"""
                view.text = t
            }else{
                view.text = time.toString()
            }
        }
    }
}