package com.example.administrator.dpreservation.utilities

import android.content.Context
import android.view.View
import android.widget.SlidingDrawer
import androidx.annotation.NonNull
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.administrator.dpreservation.R
import com.example.administrator.dpreservation.adapter.NearAdapter
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog

object DialogUtil{


    fun  showSheetDialog(context: Context,adapter: NearAdapter) {
        val view = RecyclerView(context)
        view.layoutManager= LinearLayoutManager(context)
        view.adapter = adapter
        val bottomSheetDialog =  BottomSheetDialog(context, R.style.bottom_sheet_dialog);
        bottomSheetDialog.setContentView(view);
        val mDialogBehavior = BottomSheetBehavior.from(( view.getParent() as View))
        mDialogBehavior.peekHeight = getWindowHeight(context)/2
//        mDialogBehavior.setBottomSheetCallback(object :BottomSheetBehavior.BottomSheetCallback() {
//            override fun onSlide(p0: View, p1: Float) {
//
//            }
//
//            override fun onStateChanged(p0: View, newState: Int) {
//                if (newState == BottomSheetBehavior.STATE_HIDDEN) {
//                    bottomSheetDialog.dismiss();
//                    mDialogBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
//                }
//            }
//
//        });
        mDialogBehavior.isHideable = false
        bottomSheetDialog.setOnDismissListener {
            mDialogBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }
        bottomSheetDialog.show()
    }

    fun getWindowHeight(context: Context):Int{
        val res = context.resources
        val  displayMetrics = res.displayMetrics
        return displayMetrics.heightPixels;
    }
}