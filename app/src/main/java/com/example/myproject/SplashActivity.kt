package com.example.myproject

import SHAREDPREF_NAME
import SHAREDPREF_SESSION
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({

            applicationContext.getSharedPreferences(SHAREDPREF_NAME, Context.MODE_PRIVATE).apply {

                (
                        getString(SHAREDPREF_SESSION, null)
                            ?.run {
                                CategoryActivity::class.java
                            }
                            ?: LoginActivity::class.java
                        )
                    .let {c->
                        startActivity(Intent(this@SplashActivity, c))
                        finish()
                    }

            }

        }, 2000)

    }
}