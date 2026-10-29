package com.example.gestionbiblioteca.menubiblioteca.crud

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.gestionbiblioteca.R

class DialogActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_dialog)

        //Inicialización de variables
        val libroId = intent.getStringExtra("LIBRO_ID")
        val libroNombre = intent.getStringExtra("LIBRO_NOMBRE")
        val libroAutor = intent.getStringExtra("LIBRO_AUTOR")
        val libroCategoria = intent.getStringExtra("LIBRO_CATEGORIA")
        val libroFecha = intent.getStringExtra("LIBRO_FECHA")
        val libroIsbn = intent.getStringExtra("LIBRO_ISBN")

        //Configuración de los datos para mostrar
        findViewById<TextView>(R.id.tvTituloNombre).text = libroNombre
        findViewById<TextView>(R.id.tvTituloAutor).text = libroAutor
        findViewById<TextView>(R.id.tvTituloCategoria).text = libroCategoria
        findViewById<TextView>(R.id.tvTituloFecha).text = libroFecha
        findViewById<TextView>(R.id.tvTituloIsbn).text = libroIsbn


    }
}