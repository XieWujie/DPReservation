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


class OrderDetailActivity : AppCompatActivity() {

    private lateinit var binding:ActivityOrderDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_order_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        val order = intent.getSerializableExtra("order")
        setSupportActionBar(binding.toolbar)
        if (order is Order){
            binding.order = order
            eventHandle(order)
        }
    }

    fun eventHandle(order: Order){
        if (order.state == NOT_EVALUATION){
            binding.cancel.text = "评价"
            binding.cancel.setOnClickListener {
                val intent = Intent(this,EvaluateActivity::class.java)
                intent.putExtra("order",order)
                startActivity(intent)
            }
        }else {
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
    }
}
