package com.example.gestionbiblioteca.menubiblioteca.crud

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.gestionbiblioteca.R
import com.example.gestionbiblioteca.menubiblioteca.MenuPrincipalActivity
import com.google.firebase.database.FirebaseDatabase

class AgregarLibroActivity : AppCompatActivity() {

    //Declaración de variables
    private lateinit var database: FirebaseDatabase
    private lateinit var etNombre: EditText
    private lateinit var etAutor: EditText
    private lateinit var etCategoria: EditText
    private lateinit var etFecha: EditText
    private lateinit var etIsbn: EditText
    private lateinit var btnRegistrarLibro: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_agregar_libro)

        //Inicialización de variables
        database = FirebaseDatabase.getInstance()
        etNombre = findViewById(R.id.etNombre)
        etAutor = findViewById(R.id.etAutor)
        etCategoria = findViewById(R.id.etCategoria)
        etFecha = findViewById(R.id.etFecha)
        etIsbn = findViewById(R.id.etIsbn)
        btnRegistrarLibro = findViewById(R.id.btnRegistrarLibro)

        //Configuración del evento
        btnRegistrarLibro.setOnClickListener {
            registrarLibro()
        }

    }

    //Funcion para registrar libro
    private fun registrarLibro() {

        val nombre = etNombre.text.toString()
        val autor = etAutor.text.toString()
        val categoria = etCategoria.text.toString()
        val fecha = etFecha.text.toString()
        val isbn = etIsbn.text.toString()

        if (nombre.isNotEmpty() && autor.isNotEmpty() && categoria.isNotEmpty() && fecha.isNotEmpty()
            && isbn.isNotEmpty()){
            registroLibro(nombre, autor, categoria, fecha, isbn)
            limpiarCampos()
        } else {
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
        }
    }

    //Función para limpiar campos
    private fun limpiarCampos() {
        etNombre.text.clear()
        etAutor.text.clear()
        etCategoria.text.clear()
        etFecha.text.clear()
        etIsbn.text.clear()
    }

    //Función para registrar libro
    private fun registroLibro(nombre: String, autor: String, categoria: String, fecha: String, isbn: String) {

        val libroId = database.reference.push().key

        val DatosLibros = DataLibro(
            libroId = libroId,
            nombreLibro = nombre,
            autorLibro = autor,
            categoriaLibro = categoria,
            fechaLibro = fecha,
            isbnLibro = isbn
        )
        if (libroId != null) {
            database.reference.child("DatosLibros").child(libroId).setValue(DatosLibros)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Libro registrado", Toast.LENGTH_SHORT).show()
                    }
                    else {
                        Toast.makeText(this, "Error al registrar", Toast.LENGTH_SHORT).show()
                    }
                }
        } else {
            Toast.makeText(this, "Error al registrar", Toast.LENGTH_SHORT).show()
        }

    }

}