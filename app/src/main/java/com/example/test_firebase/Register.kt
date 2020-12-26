package com.example.test_firebase

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class Register : AppCompatActivity() {
    var auth: FirebaseAuth? = null


    private lateinit var  email :EditText
    private lateinit var pass : EditText
    private lateinit var register: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_register)

        email = findViewById(R.id.Email) as EditText
        pass = findViewById(R.id.password) as EditText
       register = findViewById(R.id.registerBtn) as Button
        auth = FirebaseAuth.getInstance()
    register.setOnClickListener {
            var   memail = email.getText().toString().trim();
            var password = pass.getText().toString().trim();


            if(TextUtils.isEmpty(memail)){
                email.error = "Email is Required.";
                return@setOnClickListener;
            }

            if(TextUtils.isEmpty(password)){

                pass.error = "Password is Required.";
                return@setOnClickListener;
            }

            if(password.length < 6){
                pass.error = "Password Must be >= 6 Characters";
                return@setOnClickListener;
            }

           // progressBar.visibility = View.VISIBLE;

            login()
        }

    }

    @SuppressLint("WrongViewCast")
    private fun  login() {

        email = findViewById(R.id.Email) as EditText
        pass = findViewById(R.id.password) as EditText

        auth?.createUserWithEmailAndPassword(email.text.toString(),pass.text.toString())
            ?.addOnCompleteListener(this){
                Toast.makeText(this,email.text.toString(), Toast.LENGTH_LONG).show()
                if (it.isSuccessful) {
                    Toast.makeText(this,"Successful", Toast.LENGTH_LONG).show()

                } else {
                    Toast.makeText(this,"Error ${it.exception?.message}", Toast.LENGTH_LONG).show()

                }

            }


    }
}