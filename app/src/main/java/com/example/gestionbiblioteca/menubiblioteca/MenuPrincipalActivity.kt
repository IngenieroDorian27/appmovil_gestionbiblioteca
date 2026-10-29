package com.example.gestionbiblioteca.menubiblioteca

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.gestionbiblioteca.R
import com.example.gestionbiblioteca.menubiblioteca.crud.AgregarLibroActivity
import com.example.gestionbiblioteca.menubiblioteca.crud.ModificarActivity
import com.example.gestionbiblioteca.menubiblioteca.crud.VisualizarActivity

class MenuPrincipalActivity : AppCompatActivity() {

    //Declaración de variables
    private lateinit var cvAgregarLibro: CardView
    private lateinit var cvEditarLibro: CardView
    private lateinit var cvRevisarLibro: CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_menu_principal)

        //Inicialización de variables
        cvAgregarLibro = findViewById(R.id.cvAgregarLibro)
        cvEditarLibro = findViewById(R.id.cvEditarLibro)
        cvRevisarLibro = findViewById(R.id.cvRevisarLibro)
        cvEditarLibro = findViewById(R.id.cvEditarLibro)

        //Configuración del evento
        cvAgregarLibro.setOnClickListener {
            naveharToAgregar()
        }

        //Configuración del evento
        cvRevisarLibro.setOnClickListener {
            naveharToRevisar()
        }

        //Configuración del evento
        cvEditarLibro.setOnClickListener(){
            naveharToEditar()
        }

    }

    //Navegación entre pantallas
    private fun naveharToEditar() {
        val intent = Intent(this, ModificarActivity::class.java)
        startActivity(intent)
    }

    private fun naveharToRevisar() {
        val intent = Intent(this, VisualizarActivity::class.java)
        startActivity(intent)
    }

    private fun naveharToAgregar() {
        val intent = Intent(this, AgregarLibroActivity::class.java)
        startActivity(intent)
    }
}