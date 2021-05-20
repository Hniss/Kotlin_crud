package com.hamza.biblio

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.*

class update_livre : AppCompatActivity() {

    lateinit var name : EditText
    lateinit var description : EditText
    lateinit var prix : EditText
    lateinit var updater : Button
    lateinit var livre_id : String

    lateinit var database : FirebaseDatabase
    lateinit var myRef : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_livre)
        val intent=intent

        name = findViewById(R.id.nom_l_u)
        description = findViewById(R.id.desc_l_u)
        prix = findViewById(R.id.prix_l_u)
        updater = findViewById(R.id.updater)
        livre_id = intent.getStringExtra("livre_id") as String

        Toast.makeText(applicationContext, livre_id, Toast.LENGTH_SHORT).show()
        database = FirebaseDatabase.getInstance()
        myRef = database.getReference("Livre")

        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for(sp in snapshot.children)
                {
                    val row : Map<String, Object> = sp.getValue() as HashMap<String, Object>
                    if(sp.key.equals(livre_id))
                    {
                        name.setText(row.get("nom_p").toString())
                        description.setText(row.get("desc_p").toString())
                        prix.setText(row.get("prix_p").toString())
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        updater.setOnClickListener {
            var m = Livre(name.text.toString(),
                description.text.toString(),
                prix.text.toString().toFloat()
            )

            myRef.child(livre_id).setValue(m)
            Toast.makeText(applicationContext, "Update Done !", Toast.LENGTH_SHORT).show()
            val intent = Intent(applicationContext, details_livre::class.java)
            startActivity(intent)
            finish()

        }

    }
}