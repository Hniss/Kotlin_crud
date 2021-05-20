package com.hamza.biblio

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class LoginActivity : AppCompatActivity() {
    lateinit var auth : FirebaseAuth
    var databaseReference : DatabaseReference? = null
    var database : FirebaseDatabase? = null

    lateinit var login : EditText
    lateinit var password : EditText
    lateinit var loginBtn : Button
    lateinit var navToReg : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("Main")

        login = findViewById(R.id.login_email)
        password = findViewById(R.id.login_password)
        loginBtn = findViewById(R.id.register_button)
        navToReg = findViewById(R.id.navigate_to_register)

        login()
    }

    private fun login(){
        loginBtn.setOnClickListener {
            if(TextUtils.isEmpty(login.text.toString()))
            {
                login.setError("Entrez un email svp!")
                return@setOnClickListener
            }
            else if(TextUtils.isEmpty(password.text.toString()))
            {
                password.setError("Entrez un mot de passe svp!")
                return@setOnClickListener
            }
            auth.signInWithEmailAndPassword(login.text.toString(), password.text.toString()).addOnCompleteListener {
                if(it.isSuccessful)
                {
                    if(login.text.toString().equals("admin@gmail.com")){
                        startActivity(Intent(this@LoginActivity, MainActivity_admin::class.java))
                        finish()
                    }else{
                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                        finish()
                    }

                }
                else {
                    Toast.makeText(this@LoginActivity, "Email et/ou mot de passe incorrect(s)", Toast.LENGTH_LONG)

                }
            }

        }

        navToReg.setOnClickListener{
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }


    }
}