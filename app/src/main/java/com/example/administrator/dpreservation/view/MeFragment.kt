package com.example.administrator.dpreservation.view

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.administrator.dpreservation.databinding.FragmentMeBinding
import com.example.administrator.dpreservation.presenter.MePresenter
import java.lang.reflect.Array.setInt
import java.lang.reflect.AccessibleObject.setAccessible
import androidx.customview.widget.ViewDragHelper
import androidx.drawerlayout.widget.DrawerLayout
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Point
import android.net.Uri
import android.os.UserManager
import android.provider.MediaStore
import androidx.annotation.NonNull
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.administrator.dpreservation.core.UserManage
import com.example.administrator.dpreservation.data.user.UserRepository
import com.example.administrator.dpreservation.utilities.Util
import com.google.android.material.snackbar.Snackbar


class MeFragment:Fragment(){

    private lateinit var binding:FragmentMeBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentMeBinding.inflate(inflater,container,false)
        binding.avatar.setOnClickListener {
            if (UserManage.user == null){
                Util.log(binding.root,"请先登陆")
            }else{
                requestPermission()
            }
        }
        return binding.root
    }



    override fun onStart() {
        super.onStart()
        val user = UserManage.user
        binding.presenter = MePresenter(user?.avatar,user?.name?:"登陆")
    }

    private fun requestPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity as Activity,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
        } else {
            dispatchPictureIntent()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, @NonNull permissions: Array<String>, @NonNull grantResults: IntArray) {
        if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            when (requestCode) {
                1 ->dispatchPictureIntent()
            }
        }else {
            Util.log(binding.root, "拒绝权限将不能正常使用该功能")
        }
    }



    private fun dispatchPictureIntent() {
        val photoPickerIntent = Intent(Intent.ACTION_PICK, null)
        photoPickerIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
        startActivityForResult(photoPickerIntent, 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (Activity.RESULT_OK == resultCode) {
            if (data.data!=null){
                initAvatar(data.data)
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun initAvatar(uri: Uri){
        val realPath = Util.getRealPathFromURI(requireContext(),uri)
        Glide.with(this).load(realPath).into(binding.avatar)
        if (realPath == null){
            Util.log(binding.root,"获取图片失败")
        }else {
            UserManage.setAvatar(realPath) {
                if (it == null){
                    Util.log(binding.root,"头像更新成功")
                }else{
                    Util.log(binding.root,it.message)
                }
            }
        }
    }
}