package com.hamza.biblio

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.LinearLayoutCompat
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_details_livre.*
import kotlinx.android.synthetic.main.activity_details_livre.view.*
import kotlinx.android.synthetic.main.row_livres.view.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class details_livre : AppCompatActivity() {

    lateinit var database: FirebaseDatabase
    lateinit var myRef: DatabaseReference
    lateinit var Livres:ArrayList<Livre>
    lateinit var home_row : ImageButton
    //lateinit var update : Button
   // lateinit var drop : Button
    lateinit var livre_id : String
    //lateinit var liste_livres : ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_livre)

        //liste_livres = findViewById(R.id.listes_livres)

        database= FirebaseDatabase.getInstance()
        myRef=database.getReference("Livre")
        Livres= ArrayList<Livre>()
       home_row = findViewById(R.id.retour)
        //update = findViewById(R.id.modifier)
        //val layout= LinearLayoutCompat.inflate(this,R.layout.activity_details_livre,null) as LinearLayout
        //drop= findViewById(R.id.supprimer)
        //drop.setText("test")
        myRef.addValueEventListener(object: ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                for (snapshot in snapshot.children) {
                    val row: Map<String, Objects> = snapshot.getValue() as HashMap<String, Objects>
                    val L=Livre(
                        row.get("nom_p").toString(),
                        row.get("desc_p").toString(),
                        row.get("prix_p").toString().toFloat())
                        Livres.add(L)
                }
                val ad = customeAdapter(applicationContext, Livres)

                //var adapter=ArrayAdapter<Livre>(applicationContext, R.layout.row_livres,Livres)

                listes_livres.adapter=ad
            }

        })

        listes_livres.setOnItemClickListener { adapterView, view, position, l ->
            val intent=Intent(applicationContext,livre_ls::class.java)
            intent.putExtra("nom_p", Livres[position].nom_p)
            startActivity(intent)
        }
        home_row.setOnClickListener({
            val intent= Intent(this, MainActivity_admin::class.java)
            startActivity(intent)
            finish()
        })

    }
    class customeAdapter(
        private val mContext : Context,
        private val prds : List<Livre>
    ): ArrayAdapter<Livre>(mContext,0 ,prds){

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            //super.getView(position, convertView, parent)
            //return convertView!! // return le vide
            var Mylayout = LayoutInflater.from(mContext).inflate(R.layout.row_livres, null)
            //Toast.makeText(mContext, Mylayout.toString(), Toast.LENGTH_SHORT).show()
            Mylayout.logo_item.setImageResource(R.drawable.ajout_livre)
            Mylayout.nom_item.setText(prds[position].nom_p)
            Mylayout.prix_item.setText(prds[position].prix_p.toString())
            Mylayout.desc_item.setText(prds[position].desc_p)

            return  Mylayout
        }
    }
}