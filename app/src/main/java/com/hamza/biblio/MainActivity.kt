package com.hamza.biblio

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.row_livres_client.view.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    lateinit var database: FirebaseDatabase
    lateinit var myRef: DatabaseReference
    lateinit var Livres:ArrayList<Livre>
    lateinit var logoutBtn : ImageButton
    lateinit var home_btn : ImageButton
   // lateinit var liste_livre_u: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        database= FirebaseDatabase.getInstance()
        myRef=database.getReference("Livre")
        Livres= ArrayList<Livre>()
        logoutBtn = findViewById(R.id.logout)
        home_btn = findViewById(R.id.home_client)

     //   liste_livre_u = findViewById(R.id.listes_livres_u)

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

                //var adapter= ArrayAdapter<Livre>(applicationContext, android.R.layout.simple_list_item_1,Livres)

                listes_livres_u.adapter=ad
            }

        })

        logoutBtn.setOnClickListener({
            FirebaseAuth.getInstance().signOut()
            val intent= Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()

        })

        home_btn.setOnClickListener({
            val intent= Intent(this, MainActivity::class.java)
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
            var Mylayout = LayoutInflater.from(mContext).inflate(R.layout.row_livres_client, null)
            //Toast.makeText(mContext, Mylayout.toString(), Toast.LENGTH_SHORT).show()
            //Mylayout.logo_item.setImageResource(android.R.drawable.ic_menu_delete)
            Mylayout.nom_item.setText(prds[position].nom_p)
            Mylayout.prix_item.setText(prds[position].prix_p.toString())
            Mylayout.desc_item.setText(prds[position].desc_p)

            return  Mylayout
        }
    }

}