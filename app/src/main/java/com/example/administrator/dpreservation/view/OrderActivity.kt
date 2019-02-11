package com.example.administrator.dpreservation.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.UserManager
import android.view.MenuItem
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.ViewPager
import com.example.administrator.dpreservation.R
import com.example.administrator.dpreservation.adapter.FragmentViewPagerAdapter
import com.example.administrator.dpreservation.core.OrderManage
import com.example.administrator.dpreservation.core.UserManage
import com.example.administrator.dpreservation.databinding.ActivityOrderBinding
import com.example.administrator.dpreservation.utilities.ALL
import com.example.administrator.dpreservation.utilities.COMPLETE
import com.example.administrator.dpreservation.utilities.NOT_EVALUATION
import com.example.administrator.dpreservation.utilities.Util
import java.util.*
import kotlin.collections.ArrayList

class OrderActivity : AppCompatActivity(),ViewPager.OnPageChangeListener{

    lateinit var binding:ActivityOrderBinding
    var displayWidth = 0
    var width = 0
    var leftMargin = 0
    private lateinit var layoutParams: LinearLayout.LayoutParams

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_order)
        initUI()
        dispatchEvent()
    }

    private fun dispatchEvent() {
        val viewPager = binding.viewPager
        binding.all.setOnClickListener {
            viewPager.currentItem = 0
        }
        binding.notGenerate.setOnClickListener {
            viewPager.currentItem = 1
        }
        binding.notStart.setOnClickListener {
            viewPager.currentItem = 2
        }
        binding.starting.setOnClickListener {
            viewPager.currentItem = 3
        }
        binding.complete.setOnClickListener {
            viewPager.currentItem = 5
        }
        binding.notEvaluation.setOnClickListener {
            viewPager.currentItem = 4
        }
    }

    fun initUI(){
        setTitle("订单")
        val ownerId = UserManage.user?.userId
        if (ownerId == null){
           Util.log(window.decorView,"请先登陆")
            return
        }
        OrderManage.requestPatientOrder(this,ownerId){
            if (it != null){
                Util.log(window.decorView,"网络请求订单失败")
            }
        }
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_chevron_left_black_24dp)
        layoutParams = binding.tab.layoutParams as LinearLayout.LayoutParams
        val tabWidth = layoutParams.width
        displayWidth = windowManager.defaultDisplay.width
        width = displayWidth/6
        leftMargin = width/2 - tabWidth/2
        layoutParams.leftMargin = leftMargin
        binding.viewPager.setOnPageChangeListener(this)
        val list = ArrayList<OrderListFragment>()
        for (i in ALL until  COMPLETE+1){
            val fragment = OrderListFragment()
            list.add(fragment)
            val bundle = Bundle()
            bundle.putString("ownerId",ownerId)
            bundle.putInt("type",i)
            fragment.arguments = bundle
        }
        val adapter = FragmentViewPagerAdapter(supportFragmentManager,list)
        binding.viewPager.adapter = adapter
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            android.R.id.home->finish()
        }
        return true
    }

    override fun onPageScrollStateChanged(state: Int) {

    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        layoutParams.leftMargin = (leftMargin+(position + positionOffset)*width ).toInt()
        binding.tab.layoutParams = layoutParams
    }

    override fun onPageSelected(position: Int) {

    }
}
