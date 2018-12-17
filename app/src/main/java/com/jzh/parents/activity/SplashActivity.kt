package com.jzh.parents.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.jzh.parents.R
import com.jzh.parents.utils.PreferenceUtil

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


        // 已登录
        if (PreferenceUtil.instance.isAlreadyLogin()) {

            val intent = Intent(this@SplashActivity, HomeActivity::class.java)

            startActivity(intent)
        }
        // 显示引导图
        else if (PreferenceUtil.instance.isShowGuidePage()) {

            val intent = Intent(this@SplashActivity, GuideActivity::class.java)

            startActivity(intent)
        }
        // 未登录
        else {

            val intent = Intent(this@SplashActivity, LoginActivity::class.java)

            startActivity(intent)
        }

        finish()

    }
}
