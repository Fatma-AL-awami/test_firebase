package com.example.test_firebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth


class Login : AppCompatActivity() {
    var auth: FirebaseAuth? = null


    private lateinit var  lemail :EditText
    private lateinit var lpass : EditText
    private lateinit var login: Button

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        lemail = findViewById(R.id.Email) as EditText
        lpass = findViewById(R.id.password) as EditText
        login = findViewById(R.id.loginBtn) as Button

        auth = FirebaseAuth.getInstance()

        login.setOnClickListener {
            var   memail = lemail.getText().toString().trim();
            var password = lpass.getText().toString().trim();


            if(TextUtils.isEmpty(memail)){
                lemail.error = "Email is Required.";
                return@setOnClickListener;
            }

            if(TextUtils.isEmpty(password)){

                lpass.error = "Password is Required.";
                return@setOnClickListener;
            }

            if(password.length < 6){
                lpass.error = "Password Must be >= 6 Characters";
                return@setOnClickListener;
            }

           // progressBar.visibility = View.VISIBLE;

            login()
        }



    }

    private fun  login() {
        lemail = findViewById(R.id.Email) as EditText
        lpass = findViewById(R.id.password) as EditText

       auth?.signInWithEmailAndPassword(lemail.text.toString(),lpass.text.toString())
       // auth?.createUserWithEmailAndPassword(lemail.text.toString(),lpass.text.toString())
            ?.addOnCompleteListener(this){
                if (it.isSuccessful) {
                    Toast.makeText(this,"Successful", Toast.LENGTH_LONG).show()

                } else {
                    Toast.makeText(this,"Error ${it.exception?.message}", Toast.LENGTH_LONG).show()

                }

            }


    }
}