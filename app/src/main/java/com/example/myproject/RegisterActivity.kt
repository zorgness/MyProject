package com.example.myproject

import SHAREDPREF_NAME
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.myproject.databinding.ActivityLoginBinding
import com.example.myproject.databinding.ActivityRegisterBinding
import com.example.myproject.dataclass.RegisterInfo
import myToast
import register

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnSubmitRegister.setOnClickListener {
            val email =  binding.etUserEmail.text.toString()
            val username = binding.etUserUsername.text.toString()
            val city = binding.etUserCity.text.toString()
            val password = binding.etUserPassword.text.toString()
            val confirm = binding.etUserPasswordConfirm.text.toString()

            if (email.isNotBlank() && username.isNotBlank() && city.isNotBlank() && password.isNotBlank()) {
                if (validatePassword(password, confirm)) {
                    register(RegisterInfo(email, password, username, city, ""), loginCallback = {
                        with(it) {

                            //NEED A SOLUTION TO LOG AFTER REGISTRATION
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
