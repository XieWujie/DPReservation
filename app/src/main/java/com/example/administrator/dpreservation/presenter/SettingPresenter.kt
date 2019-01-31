package com.example.administrator.dpreservation.presenter

import android.view.View
import androidx.navigation.findNavController
import com.example.administrator.dpreservation.R

class SettingPresenter{

    fun acountAndSafety(view: View){
        view.findNavController().navigate(R.id.action_settingFragment_to_acountAndSafetyFragment)
    }

    fun switchLanguage(view: View){

    }

    fun clearCache(view: View){

    }

    fun userGuide(view: View){

    }

    fun feedback(view: View){

    }

    fun versionUpdate(view: View){

    }

    fun about(view: View){

    }
}