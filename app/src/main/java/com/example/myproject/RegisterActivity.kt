package com.example.myproject

import SHAREDPREF_NAME
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.myproject.dataclass.RegisterInfo
import myToast
import register

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val etEmail = findViewById<EditText>(R.id.et_user_email)
        val etUsername = findViewById<EditText>(R.id.et_user_username)
        val etCity = findViewById<EditText>(R.id.et_user_city)
        val etPassword = findViewById<EditText>(R.id.et_user_password)
        val etPwdConfirm = findViewById<EditText>(R.id.et_user_password_confirm)

        findViewById<Button>(R.id.btn_submit_register).setOnClickListener {
            val email = etEmail.text.toString()
            val username = etUsername.text.toString()
            val city = etCity.text.toString()
            val password = etPassword.text.toString()
            val confirm = etPwdConfirm.text.toString()

            if (email.isNotBlank() && username.isNotBlank() && city.isNotBlank() && password.isNotBlank()) {
                if (validatePassword(password, confirm)) {
                    register(RegisterInfo(email, password, username, city, ""), loginCallback = {
                        with(it) {
                            /*applicationContext.getSharedPreferences(
                                SHAREDPREF_NAME,
                                Context.MODE_PRIVATE
                            )
                                .edit()
                                .putString(SHAREDPREF_SESSION, convertDtoToJsonStr(this))
                                .apply()*/


                            startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                            finish()
                        }
                    }, errorCallback = { error ->
                        myToast(error.toString())
                    })

                } else {
                    myToast("password and confirm are not equals")
                }
            } else {
                myToast("fields can't be blank")
            }
        }
    }

    private fun validatePassword(password: String, confirm: String): Boolean {
        return password == confirm
    }
}
