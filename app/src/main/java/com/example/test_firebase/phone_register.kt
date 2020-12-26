package com.example.test_firebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import java.util.concurrent.TimeUnit
 val TAG="fatma"
class phone_register : AppCompatActivity() {
    var auth: FirebaseAuth? = null


    private lateinit var  lemail : EditText
    private lateinit var lpass : EditText
    private lateinit var login: Button
    lateinit var  storedVerificationId:String
    lateinit var  resendToken:PhoneAuthProvider.ForceResendingToken

  lateinit var  callbacks : PhoneAuthProvider.OnVerificationStateChangedCallbacks

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phone_register)

        lemail = findViewById(R.id.phone) as EditText
        lpass = findViewById(R.id.password) as EditText
        login = findViewById(R.id.loginBtn) as Button

        auth = FirebaseAuth.getInstance()

        login.setOnClickListener {
            var   memail = lemail.getText().toString().trim();
            var password = lpass.getText().toString().trim();


            if(TextUtils.isEmpty(memail)){
                lemail.error = "phone is Required.";
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

        var phoneNumber=lemail.text.toString()


        val options = auth?.let {
            PhoneAuthOptions.newBuilder(it)
                .setPhoneNumber(phoneNumber)       // Phone number to verify
                .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                .setActivity(this)                 // Activity (for callback binding)
                .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
                .build()
        }
        if (options != null) {
            PhoneAuthProvider.verifyPhoneNumber(options)
        }

        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {

                Log.d(TAG, "onVerificationCompleted:$credential")

                signInWithPhoneAuthCredential(credential)
            }

            override fun onVerificationFailed(p0: FirebaseException) {
                TODO("Not yet implemented")
                Log.w(TAG, "onVerificationFailed", p0)

                if (p0 is FirebaseAuthInvalidCredentialsException) {

                } else if (p0 is FirebaseTooManyRequestsException) {

                }

            }



            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.d(TAG, "onCodeSent:$verificationId")

                // Save verification ID and resending token so we can use them later
                storedVerificationId = verificationId
                resendToken = token

                // ...
            }
        }
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth?.signInWithCredential(credential)
            ?.addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")

                    val user = task.result?.user
                    // ...
                } else {
                    // Sign in failed, display a message and update the UI
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                    }
                }
            }
    }

}