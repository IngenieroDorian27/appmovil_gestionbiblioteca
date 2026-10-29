package com.example.gestionbiblioteca.menubiblioteca.crud

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import java.util.Locale

class VisualizarActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var libroRecyclerView: RecyclerView
    private lateinit var libroList: ArrayList<DataLibro>
    private lateinit var libroAdapter: LibroAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_visualizar)

        // Configuración del RecyclerView para mostrar la lista de libros
        libroRecyclerView = findViewById(R.id.notesRecyclerView)
        libroRecyclerView.layoutManager = LinearLayoutManager(this)
        libroRecyclerView.setHasFixedSize(true)

        libroList = arrayListOf()
        libroAdapter = LibroAdapter(libroList)
        libroRecyclerView.adapter = libroAdapter

        database = FirebaseDatabase.getInstance().getReference("DatosLibros")

        // Configuración del evento para cargar la lista
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
                Toast.makeText(this@VisualizarActivity, "Error al cargar los datos: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })


        // Configura el EditText para filtrar la lista de libros en tiempo real
        val etBuscarLibro = findViewById<android.widget.EditText>(R.id.etBuscarLibro)
        etBuscarLibro.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                filterList(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

    }

    //Función para filtrar la lista de libros
    private fun filterList(query: String) {
        val filteredList = ArrayList<DataLibro>()
        for (libro in libroList) {
            if (libro.nombreLibro?.lowercase(Locale.ROOT)?.contains(query.lowercase(Locale.ROOT)) == true) {
                filteredList.add(libro)
            }
        }
        libroAdapter = LibroAdapter(filteredList)
        libroRecyclerView.adapter = libroAdapter

    }
}