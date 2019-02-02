package com.example.administrator.dpreservation.custom

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.customview.widget.ViewDragHelper

class DragLayout:RelativeLayout{

    private lateinit var viewDragHelper:ViewDragHelper

    constructor(context: Context):super(context){
        init()
    }

    constructor(context: Context,attributeSet: AttributeSet):super(context,attributeSet){
        init()
    }

    private fun init(){
        viewDragHelper = ViewDragHelper.create(this,1.0f,MyDragHelper())
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        when(ev?.action){
            MotionEvent.ACTION_CANCEL,MotionEvent.ACTION_DOWN->return false
        }
        viewDragHelper.processTouchEvent(ev!!)
        return true
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        viewDragHelper.processTouchEvent(event!!)
        return true
    }

    inner class MyDragHelper : ViewDragHelper.Callback() {

        override fun tryCaptureView(child: View, pointerId: Int): Boolean {
            return true
        }

        override fun clampViewPositionHorizontal(child: View, left: Int, dx: Int): Int {
            return left
        }

        override fun clampViewPositionVertical(child: View, top: Int, dy: Int): Int {
            return top
        }

    }
}