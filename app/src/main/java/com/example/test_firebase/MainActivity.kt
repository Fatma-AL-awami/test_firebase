package com.example.test_firebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth

var auth : FirebaseAuth? = null
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnSignUp = findViewById(R.id.btnSignUp) as Button
        val btnSignIn = findViewById(R.id.btnSignIn) as Button
        val btnSignOut = findViewById(R.id.btnSignOut) as Button

        auth = FirebaseAuth.getInstance()

        btnSignUp.setOnClickListener {
            signUp()
        }

        btnSignIn.setOnClickListener {
            signIn()
        }

        btnSignOut.setOnClickListener {
            signOut()
        }

    }

    private fun signUp() {
        val intent = Intent(this,Register::class.java)
        startActivity(intent)
    }
    private fun signIn() {
        val intent = Intent(this,Login::class.java)
        startActivity(intent)
    }

    private fun signOut() {
        auth!!.signOut()
    }
}