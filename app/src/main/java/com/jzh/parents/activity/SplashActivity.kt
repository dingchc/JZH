package com.jzh.parents.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.jzh.parents.R

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


        Handler().postDelayed({
            val intent = Intent(this@SplashActivity, LoginActivity::class.java)

            startActivity(intent)

            finish()
        }, 3000L)

    }
}
