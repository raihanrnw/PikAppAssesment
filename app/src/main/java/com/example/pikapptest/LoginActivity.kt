package com.example.pikapptest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.util.Patterns
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()


        tv_signup.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
            finish()
        }

        btn_login.setOnClickListener {
            Login()
        }
    }

    private fun Login() {
        if (email_input.text.toString().isEmpty()) {
            email_input.error = "Pls Input Email"
            email_input.requestFocus()
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email_input.text.toString()).matches()) {
            email_input.error = "Pls Input Valid Email"
            email_input.requestFocus()
            return
        }

        if (password_input.text.toString().isEmpty()) {
            password_input.error = "Pls Input Password"
            password_input.requestFocus()
            return
        }

        auth.signInWithEmailAndPassword(email_input.text.toString(),password_input.text.toString())
            .addOnCompleteListener(this) {
                task ->
                if (task.isSuccessful){
                    val user: FirebaseUser? = auth.currentUser
                    updateUI(user)
                }else{
                    Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }
            }
    }

    public override fun onStart() {
        super.onStart()
        val currUser : FirebaseUser? = auth.currentUser
        updateUI(currUser)
    }

    private fun updateUI(currUser: FirebaseUser?){
    if (currUser != null){
        startActivity(Intent(this, MainActivity::class.java))
        } else{
            Toast.makeText(baseContext, "Login Failed", Toast.LENGTH_SHORT).show()
        }
    }
}