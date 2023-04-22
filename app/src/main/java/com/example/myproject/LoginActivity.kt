package com.example.myproject

import SHAREDPREF_NAME
import SHAREDPREF_SESSION_TOKEN
import SHAREDPREF_SESSION_USERNAME
import SHAREDPREF_SESSION_USER_ID
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.myproject.dataclass.LoginInfo
import login
import myToast

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val etEmail = findViewById<EditText>(R.id.et_user_email)
        val etPassword = findViewById<EditText>(R.id.et_user_password)

        findViewById<TextView>(R.id.tv_register_login_activity).setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }

        findViewById<Button>(R.id.btn_submit_login).setOnClickListener {
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()

            if (email.isNotBlank() && password.isNotBlank()) {

                login(LoginInfo(email, password), loginCallback = {
                    with(it) {
                        applicationContext.getSharedPreferences(
                            SHAREDPREF_NAME,
                            android.content.Context.MODE_PRIVATE
                        )
                            .edit()
                            .putString(SHAREDPREF_SESSION_TOKEN, this.token)
                            .putInt(SHAREDPREF_SESSION_USER_ID, this.id)
                            .putString(SHAREDPREF_SESSION_USERNAME, this.username)
                            .apply()
                        startActivity(
                            Intent(
                                this@LoginActivity,
                                RecyclerViewCategoryActivity::class.java
                            )
                        )
                        finish()
                    }
                }, errorCallback = { error ->
                    myToast(error.toString())
                })

            } else {
                myToast("fields can't be empty")
            }
        }
    }
}
