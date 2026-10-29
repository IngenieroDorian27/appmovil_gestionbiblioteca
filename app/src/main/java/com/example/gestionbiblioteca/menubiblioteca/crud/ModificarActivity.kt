package com.example.gestionbiblioteca.menubiblioteca.crud

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gestionbiblioteca.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ModificarActivity : AppCompatActivity() {

    //Declaración de variables
    private lateinit var database: DatabaseReference
    private lateinit var libroRecyclerView: RecyclerView
    private lateinit var libroList: ArrayList<DataLibro>
    private lateinit var libroAdapter: LibroAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_modificar)


        libroRecyclerView = findViewById(R.id.notesRecyclerView)
        libroRecyclerView.layoutManager = LinearLayoutManager(this)
        libroRecyclerView.setHasFixedSize(true)

        libroList = arrayListOf()
        libroAdapter = LibroAdapter(libroList, isEditable = true)
        libroRecyclerView.adapter = libroAdapter

        // Referencia a "DatosLibros" en Firebase
        database = FirebaseDatabase.getInstance().getReference("DatosLibros")

        //Configuración del evento
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                libroList.clear()
                if (snapshot.exists()) {
                    for (libroSnapshot in snapshot.children) {
                        val libro = libroSnapshot.getValue(DataLibro::class.java)
                        libroList.add(libro!!)
                    }
                    libroAdapter.notifyDataSetChanged()
                }
            }


            // Manejador de errores al cargar datos desde Firebase
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@ModificarActivity, "Error al cargar los datos: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })

    }
}