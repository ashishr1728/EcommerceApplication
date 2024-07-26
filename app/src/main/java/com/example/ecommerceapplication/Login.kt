package com.example.ecommerceapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class Login : AppCompatActivity() {
    private lateinit var email : EditText
    private lateinit var password : EditText
    private lateinit var login : Button
    private lateinit var signUp : Button
    private lateinit var auth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        email = findViewById(R.id.email)
        password = findViewById(R.id.password)
        login = findViewById(R.id.login)
        signUp = findViewById(R.id.signup)
        auth = Firebase.auth

        login.setOnClickListener {
            val edt_email = email.text.toString()
            val edt_pass = password.text.toString()
            login(edt_email,edt_pass)
        }

        signUp.setOnClickListener {
            val edt_email = email.text.toString()
            val edt_pass = password.text.toString()
            signUp(edt_email,edt_pass)
        }
    }

    private fun login(email : String, password : String){
        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    startActivity(
                        Intent(this, Uploadproduct::class.java)
                    )
                    Toast.makeText(this, "Successfully Login", Toast.LENGTH_SHORT).show()
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this, "Some Error Occured", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun signUp(email:String, password:String){
        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Toast.makeText(this, "Successfully SignUp", Toast.LENGTH_SHORT).show()
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this, "Some Error Occured", Toast.LENGTH_SHORT).show()
                }
            }
    }
}