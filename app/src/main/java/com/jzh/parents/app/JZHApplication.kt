package com.jzh.parents.app

import android.app.Application
import com.tunes.library.wrapper.network.TSHttpController

/**
 * 主App
 * @author ding
 * Created by Ding on 2018/8/16.
 */
class JZHApplication : Application() {

    companion object {

        var instance : JZHApplication? = null
    }

    override fun onCreate() {
        super.onCreate()

        instance = this@JZHApplication
        TSHttpController.INSTANCE.setAppContext(this)
    }
}