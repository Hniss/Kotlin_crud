package com.hamza.biblio

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import com.google.firebase.database.*

class livre_ls : AppCompatActivity() {

    lateinit var nom_l : TextView
    lateinit var desc_l : TextView
    lateinit var prix_l : TextView
    lateinit var modifier : Button
    lateinit var supprimer : Button
    lateinit var livre_id : String
    lateinit var retour : ImageButton

    lateinit var database: FirebaseDatabase
    lateinit var myRef: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_livre_ls)
        nom_l = findViewById(R.id.nom_l)
        desc_l = findViewById(R.id.desc_l)
        prix_l = findViewById(R.id.prix_p)
        modifier=findViewById(R.id.modifier2)
        supprimer=findViewById(R.id.supprimer2)
        database= FirebaseDatabase.getInstance()
        myRef=database.getReference("Livre")
        retour = findViewById(R.id.retour1)

        val livre_name : String
        val intent=intent
        livre_name = intent.getStringExtra("nom_p").toString()
        myRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for(sp in snapshot.children)
                {
                    val row : Map<String, Object> = sp.getValue() as HashMap<String, Object>
                    if(row.get("nom_p").toString().equals(livre_name))
                    {
                        livre_id = sp.key as String
                        nom_l.setText(row.get("nom_p").toString())
                        desc_l.setText(row.get("desc_p").toString())
                        prix_l.setText(row.get("prix_p").toString())
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })

        supprimer.setOnClickListener {
            myRef.child(livre_id).removeValue()
            val intent = Intent(applicationContext, details_livre::class.java)
            startActivity(intent)
            finish()
        }

        modifier.setOnClickListener {
            val intent = Intent(applicationContext, update_livre::class.java)
            intent.putExtra("livre_id",livre_id.toString())
            startActivity(intent)
        }

        retour.setOnClickListener({
            val intent= Intent(this, MainActivity_admin::class.java)
            startActivity(intent)
            finish()
        })

    }
}