package com.hamza.biblio

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {
    val ref = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val name = findViewById<TextInputEditText>(R.id.nom)
        val prenom = findViewById<TextInputEditText>(R.id.prenom)
        val email=findViewById<TextInputEditText>(R.id.login_email)
        val password=findViewById<TextInputEditText>(R.id.login_password)
        val registerBtn=findViewById<Button>(R.id.register_button)
        val navToLog=findViewById<TextView>(R.id.navigate_to_register)

        registerBtn.setOnClickListener {
            if(email.text.toString().trim().isNotEmpty() || password.text.toString().trim().isNotEmpty())
            {
                ref.createUserWithEmailAndPassword(
                    email.text.toString().trim(),
                    password.text.toString().trim()
                )
                Toast.makeText(this,"Compte crée avec succes!", Toast.LENGTH_LONG).show()
                startActivity(Intent(this, LoginActivity::class.java))

            }
            else
            {
                Toast.makeText(this,"Veuillez entrer une valeur", Toast.LENGTH_LONG).show()
            }

        }

        navToLog.setOnClickListener{
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}