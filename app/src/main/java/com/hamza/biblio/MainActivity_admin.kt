package com.hamza.biblio

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import kotlinx.android.synthetic.main.activity_details_livre.view.*
import kotlinx.android.synthetic.main.activity_main_admin.*

class MainActivity_admin : AppCompatActivity() {
    lateinit var btn_liste : ImageButton
    lateinit var ajout_livre : ImageButton
    lateinit var logout : ImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_admin)


        var database = FirebaseDatabase.getInstance()
        var myRef = database.getReference("Livre")
        btn_liste = findViewById(R.id.livre_liste)
        btn_liste.setOnClickListener({
            startActivity(Intent(this, details_livre::class.java))
        })

        ajout_livre = findViewById(R.id.cmd_liste)
        ajout_livre.setOnClickListener({
            startActivity(Intent(this, AjoutProduit::class.java))
        })

        logout = findViewById(R.id.logout)
        logout.setOnClickListener {
                FirebaseAuth.getInstance().signOut()
                val intent= Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
        }



    }

}