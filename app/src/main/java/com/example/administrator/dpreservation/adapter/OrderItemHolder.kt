package com.example.administrator.dpreservation.adapter

import android.content.Intent
import android.view.View
import com.example.administrator.dpreservation.core.OrderManage
import com.example.administrator.dpreservation.data.order.Order
import com.example.administrator.dpreservation.databinding.OrderListItemBinding
import com.example.administrator.dpreservation.utilities.*
import com.example.administrator.dpreservation.view.ChatActivity
import com.example.administrator.dpreservation.view.EvaluateActivity
import com.example.administrator.dpreservation.view.OrderDetailActivity
import java.util.*

class OrderItemHolder(private val binding:OrderListItemBinding):BaseHolder(binding.root){

    override fun bind(any: Any) {
        if (any is Order){
            binding.order = any
           binding.state.text =  when(any.state){
                NOT_GENERATED->"待确认"
               NOT_START->if (Util.getCurrentTimeStamp()<any.orderTime){
                   "未开始"
               }else{
                   binding.cancel.text = "已结束治疗"
                   "已开始"
               }
               STARTING->{
                   binding.cancel.text = "已结束治疗"
                   "已开始"
               }
               COMPLETE->{
                   binding.cancel.visibility = View.INVISIBLE
                   "已完成"
               }
               NOT_EVALUATION->{
                   binding.cancel.text = "评价"
                   "待评价"
               }
               else->"未知"
            }
            binding.message.setOnClickListener {
                val intent = Intent(it.context,ChatActivity::class.java)
                intent.putExtra(CONVERSATION_ID,any.doctorId)
                intent.putExtra(CONVERSATION__NAME,any.doctorName)
                intent.putExtra(AVATAR,any.doctorAvatar)
                it.context.startActivity(intent)
            }
            binding.root.setOnClickListener {
                val context = it.context
                val intent = Intent(context,OrderDetailActivity::class.java)
                intent.putExtra("order",any)
                context.startActivity(intent)
            }
            binding.cancel.setOnClickListener {
                when(binding.cancel.text){
                    "取消订单"->{
                        val dialog = Util.createProgressDialog(it.context)
                        dialog.show()
                        OrderManage.cancelOrder(any){
                            dialog.dismiss()
                            if (it != null){
                                Util.log(binding.root,"取消失败")
                            }
                        }
                    }
                    "评价"->{
                        val context = it.context
                        val intent = Intent(context,EvaluateActivity::class.java)
                        intent.putExtra("order",any)
                        context.startActivity(intent)
                    }
                    "已结束治疗"->{
                        OrderManage.endTreatment(context,any){

                        }
                    }
                }
            }
        }
    }
}