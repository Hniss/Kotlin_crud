package com.hamza.biblio

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AjoutProduit : AppCompatActivity() {
    lateinit var nom_p : EditText
    lateinit var desc_p : EditText
    lateinit var prix_p : EditText
    lateinit var insert_p : Button
    lateinit var home_aj : ImageButton

    lateinit var database : FirebaseDatabase
    lateinit var myRef : DatabaseReference

    //val x = findViewById<TextInputEditText>(R.id.(id de champs))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ajout_produit)

        nom_p = findViewById(R.id.nom_p)
        desc_p = findViewById(R.id.desc_p)
        prix_p = findViewById(R.id.prix_p)
        insert_p = findViewById(R.id.ajouter)
        home_aj = findViewById(R.id.home_aj)

        database = FirebaseDatabase.getInstance()
        myRef = database.getReference("Livre")
        insert_p.setOnClickListener {
            var L = Livre(nom_p.text.toString(), desc_p.text.toString(),prix_p.text.toString().toFloat())
            var id = myRef.push().key as String
            myRef.child(id).setValue(L).addOnSuccessListener {
                Toast.makeText(applicationContext, "Bien enregistré", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(applicationContext, "Erreur", Toast.LENGTH_SHORT).show()
            }
        }

        home_aj.setOnClickListener({
            val intent= Intent(this, MainActivity_admin::class.java)
            startActivity(intent)
            finish()
        })


    }
}