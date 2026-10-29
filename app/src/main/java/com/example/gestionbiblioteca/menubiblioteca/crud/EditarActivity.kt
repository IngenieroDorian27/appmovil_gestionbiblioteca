package com.example.gestionbiblioteca.menubiblioteca.crud

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.gestionbiblioteca.R
import com.google.firebase.database.FirebaseDatabase

class EditarActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_editar)

        //Inicialización de variables
        val libroId = intent.getStringExtra("LIBRO_ID")
        val libroNombre = intent.getStringExtra("LIBRO_NOMBRE")
        val libroAutor = intent.getStringExtra("LIBRO_AUTOR")
        val libroCategoria = intent.getStringExtra("LIBRO_CATEGORIA")
        val libroFecha = intent.getStringExtra("LIBRO_FECHA")
        val libroIsbn = intent.getStringExtra("LIBRO_ISBN")
        val btnEditarLibro = findViewById<Button>(R.id.btnEditarLibro)

        //Configuración de los datos para mostrar
        findViewById<EditText>(R.id.etEditarNombre).setText(libroNombre)
        findViewById<EditText>(R.id.etEditarAutor).setText(libroAutor)
        findViewById<EditText>(R.id.etEditarCategoria).setText(libroCategoria)
        findViewById<EditText>(R.id.etEditarFecha).setText(libroFecha)
        findViewById<EditText>(R.id.etEditarIsbn).setText(libroIsbn)

        //Configuración del evento
        btnEditarLibro.setOnClickListener {
            val nuevoNombre = findViewById<EditText>(R.id.etEditarNombre).text.toString()
            val nuevoAutor = findViewById<EditText>(R.id.etEditarAutor).text.toString()
            val nuevaCategoria = findViewById<EditText>(R.id.etEditarCategoria).text.toString()
            val nuevaFecha = findViewById<EditText>(R.id.etEditarFecha).text.toString()
            val nuevoIsbn = findViewById<EditText>(R.id.etEditarIsbn).text.toString()

            actualizarLibro(libroId, nuevoNombre, nuevoAutor, nuevaCategoria, nuevaFecha, nuevoIsbn)
        }

    }

    //Función para actualizar libro
    private fun actualizarLibro(
        libroId: String?,
        nuevoNombre: String,
        nuevoAutor: String,
        nuevaCategoria: String,
        nuevaFecha: String,
        nuevoIsbn: String
    ) {
        val database = FirebaseDatabase.getInstance().getReference("DatosLibros")

        val libro =
            DataLibro(libroId, nuevoNombre, nuevoAutor, nuevaCategoria, nuevaFecha, nuevoIsbn)
        libroId?.let {
            database.child(it).setValue(libro).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Libro actualizado exitosamente", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Toast.makeText(this, "Error al actualizar", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}