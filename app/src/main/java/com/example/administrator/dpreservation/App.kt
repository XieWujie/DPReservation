package com.example.administrator.dpreservation

import android.app.Application
import com.avos.avoscloud.AVOSCloud
import com.avos.avoscloud.PushService
import com.avos.avoscloud.im.v2.AVIMMessageManager
import com.example.administrator.dpreservation.core.MessageHandler

class App:Application(){

//     val APP_ID = "FTDVimcFscVf6mA2n9ebuCue-gzGzoHsz"
//     val APP_KEY = "eOK5mMX27ts5zjfhFDrQTVTc"
    val APP_ID = "i2TacWout59nxbKyt0NawBHJ-gzGzoHsz"
    val APP_KEY = "kxp8G4U9vux2zuv68rrm2sV3"

    override fun onCreate() {
        super.onCreate()
        AVOSCloud.initialize(this, APP_ID, APP_KEY)
        AVOSCloud.setDebugLogEnabled(true)
        AVIMMessageManager.registerDefaultMessageHandler(MessageHandler())
    }
}