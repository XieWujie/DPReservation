package com.example.administrator.dpreservation.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.administrator.dpreservation.R
import com.example.administrator.dpreservation.core.EvaluateManager
import com.example.administrator.dpreservation.data.order.Order
import com.example.administrator.dpreservation.databinding.ActivityEvaluateBinding
import com.example.administrator.dpreservation.utilities.Util

class EvaluateActivity : AppCompatActivity() {

    private lateinit var binding:ActivityEvaluateBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_evaluate)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setTitle("发布评论")
        sendEvaluationEvent()
        binding.ratingBar2.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
          binding.textView3.text =   when(rating){
                1f->"差"
              2f->"非常差"
              3f->"一般"
              4f->"好"
              else->"非常好"
            }
        }
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun sendEvaluationEvent(){
        val order = intent.getSerializableExtra("order")
        if (order is Order){
            binding.sendEvaluation.setOnClickListener { v->
                val dialog = Util.createProgressDialog(this)
                dialog.show()
                EvaluateManager.createEvaluation(this,binding.ratingBar2.rating.toInt(),binding.contentText.text.toString(),order){e->
                    dialog.dismiss()
                    if (e == null){
                        finish()
                    }else{
                        Util.log(binding.root,e.message)
                    }
                }
                true
            }
        }else{
            Util.log(binding.root,"获取订单失败")
        }
    }
}
