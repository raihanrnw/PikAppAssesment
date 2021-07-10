package com.example.pikapptest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        auth = FirebaseAuth.getInstance()

        btn_signup.setOnClickListener {
            signupuser()
        }
    }

    private fun signupuser() {

        btn_signup.setOnClickListener {
            if (email.text.toString().isEmpty()) {
                email.error = "Pls Input Email"
                email.requestFocus()
                return@setOnClickListener
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(email.text.toString()).matches()) {
                email.error = "Pls Input Valid Email"
                email.requestFocus()
                return@setOnClickListener
            }

            if (password.text.toString().isEmpty()) {
                password.error = "Pls Input Password"
                password.requestFocus()
                return@setOnClickListener
            }

            auth.createUserWithEmailAndPassword(email.text.toString(), password.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        startActivity(Intent(this, LoginActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(baseContext, "Sign up failed", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}