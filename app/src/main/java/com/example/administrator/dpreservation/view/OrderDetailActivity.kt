package com.example.administrator.dpreservation.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import com.example.administrator.dpreservation.R
import com.example.administrator.dpreservation.core.OrderManage
import com.example.administrator.dpreservation.data.order.Order
import com.example.administrator.dpreservation.databinding.ActivityOrderDetailBinding
import com.example.administrator.dpreservation.utilities.*
import java.text.SimpleDateFormat
import java.util.*


class OrderDetailActivity : BaseActivity() {

    private lateinit var binding:ActivityOrderDetailBinding
    protected var menu: Menu? = null
    private var order:Order? = null
    val CANCEL = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_order_detail)
        setBlueBar(binding.toolbar)
        val order = intent.getSerializableExtra("order")
        if (order is Order){
            val newOrder =  if (order.state == NOT_START&&Util.getCurrentTimeStamp()>order.orderTime){
               order.copy(state = STARTING)
            }else{
                order
            }
            this.order = newOrder
            binding.order = newOrder
            initSchedule(newOrder)
        }
        setTitle("订单详情")
    }

    private fun initSchedule(order: Order){
        val f = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val state = order.state
        val schedule = binding.orderSchedule
        when(state){
            NOT_GENERATED->schedule.init(0, listOf(f.format(Date(order.createTime))))
            NOT_START->schedule.init(1, listOf(f.format(Date(order.createTime)),f.format(Date(order.agreeTime))))
            STARTING->schedule.init(2, listOf(f.format(Date(order.createTime)),f.format(Date(order.agreeTime)),
                f.format(Date(order.orderTime))))
            NOT_EVALUATION->schedule.init(3, listOf(f.format(Date(order.createTime)),f.format(Date(order.agreeTime)),
                f.format(Date(order.orderTime)),f.format(Date(order.endTreatmentTime))))
            COMPLETE->schedule.init(4, listOf(f.format(Date(order.createTime)),f.format(Date(order.agreeTime)),
                f.format(Date(order.orderTime)),f.format(Date(order.endTreatmentTime)),f.format(Date(order.completeTime))))
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        this.menu = menu
        MenuInflater(this).inflate(R.menu.order_detail_menu,menu)
        when(order?.state){
            NOT_GENERATED, NOT_START->{
                menu?.add(1,CANCEL,2,"取消订单")
            }
            STARTING->{
                menu?.add(1, STARTING,2,"支付")
            }
            NOT_EVALUATION->menu?.add(1, NOT_EVALUATION,2,"评价")
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (order == null)return false
        when(item?.itemId){
            R.id.send_message->{
                val intent = Intent(this,ChatActivity::class.java)
                intent.putExtra(CONVERSATION__NAME,order!!.doctorName)
                intent.putExtra(CONVERSATION_ID,order!!.doctorId)
                intent.putExtra(AVATAR,order!!.doctorName)
                startActivity(intent)
            }
            CANCEL->{
                val dialog = Util.createProgressDialog(this)
                dialog.show()
                OrderManage.cancelOrder(order!!) {
                    dialog.dismiss()
                    if (it == null) {
                        Util.log(binding.root, "取消订单成功")
                        finish()
                    } else {
                        Util.log(binding.root, "取消订单失败")
                    }
                }
            }
            NOT_EVALUATION->{
                val intent = Intent(this,EvaluateActivity::class.java)
                intent.putExtra("order",order)
                startActivity(intent)
            }
            STARTING->Util.pay(binding.root)
        }
        return true
    }
}
