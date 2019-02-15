package com.example.administrator.dpreservation.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import com.example.administrator.dpreservation.R
import com.example.administrator.dpreservation.databinding.ActivityFindPasswordBinding
import com.example.administrator.dpreservation.presenter.FindPasswordPresenter
import com.example.administrator.dpreservation.utilities.Util

class FindPasswordActivity : BaseActivity() {

    private lateinit var binding:ActivityFindPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_find_password)
        setBlueBar(binding.findToolbar)
        binding.presenter = FindPasswordPresenter("")
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> finish()
        }
        return true
    }


}
