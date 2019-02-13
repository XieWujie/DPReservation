package com.example.administrator.dpreservation.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_order_detail)
        setActionBar(binding.toolbar)
        val order = intent.getSerializableExtra("order")
        if (order is Order){
            val newOrder =  if (order.state == NOT_START&&Util.getCurrentTimeStamp()>order.orderTime){
               order.copy(state = STARTING)
            }else{
                order
            }
            binding.order = newOrder
            eventHandle(newOrder)
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

    fun eventHandle(order: Order){
        binding.sendMessage.setOnClickListener {
            val intent = Intent(this,ChatActivity::class.java)
            intent.putExtra(CONVERSATION__NAME,order.doctorName)
            intent.putExtra(CONVERSATION_ID,order.doctorId)
            intent.putExtra(AVATAR,order.doctorName)
            startActivity(intent)
        }
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
        when(order.state){
            COMPLETE->{
                binding.bottomLayout.removeView(binding.cancel)
            }
            NOT_EVALUATION->{
                binding.cancel.text = "评价"
                binding.cancel.setOnClickListener {
                    val intent = Intent(this,EvaluateActivity::class.java)
                    intent.putExtra("order",order)
                    startActivity(intent)
                }
            }
            NOT_START, NOT_GENERATED->{
                binding.cancel.text = "取消订单"
                binding.cancel.setOnClickListener {
                    val dialog = Util.createProgressDialog(this)
                    dialog.show()
                    OrderManage.cancelOrder(order) {
                        dialog.dismiss()
                        if (it == null) {
                            Util.log(binding.root, "取消订单成功")
                            finish()
                        } else {
                            Util.log(binding.root, "取消订单失败")
                        }
                    }
                }
            }
            STARTING->{
                binding.cancel.text = "支付"
                binding.cancel.setOnClickListener {
                    Util.pay(binding.root)
                }
            }
        }
    }
}
