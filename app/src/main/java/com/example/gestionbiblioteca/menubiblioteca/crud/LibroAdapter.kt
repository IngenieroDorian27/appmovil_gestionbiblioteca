package com.example.gestionbiblioteca.menubiblioteca.crud

import android.app.AlertDialog
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.gestionbiblioteca.R
import com.google.firebase.database.FirebaseDatabase

class LibroAdapter(
    private val libroList: List<DataLibro>,
    private val isEditable: Boolean = false
) : RecyclerView.Adapter<LibroAdapter.LibroViewHolder>() {

    //Clase para el ViewHolder
    class LibroViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tituloNombre: TextView = itemView.findViewById(R.id.tvTituloNombre)
        val autorAutor: TextView = itemView.findViewById(R.id.tvTituloAutor)
        val updateButton: ImageView? = itemView.findViewById(R.id.updateButton)
        val deleteButton: ImageView? = itemView.findViewById(R.id.deleteButton)
        val btnver: ImageView? = itemView.findViewById(R.id.btnver)
    }

    //Función para crear el ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LibroViewHolder {
        val itemView = if (isEditable) {
            LayoutInflater.from(parent.context).inflate(R.layout.modificar_item, parent, false)
        } else {
            LayoutInflater.from(parent.context).inflate(R.layout.libros_item, parent, false)
        }
        return LibroViewHolder(itemView)
    }

    //Función para actualizar el ViewHolder
    override fun onBindViewHolder(holder: LibroViewHolder, position: Int) {
        val currentItem = libroList[position]
        holder.tituloNombre.text = currentItem.nombreLibro ?: "Sin nombre"
        holder.autorAutor.text = currentItem.autorLibro ?: "Sin autor"


        //Configuración del evento
        holder.btnver?.setOnClickListener {
            val intent = Intent(holder.itemView.context, DialogActivity::class.java).apply {
                putExtra("LIBRO_ID", currentItem.libroId)
                putExtra("LIBRO_NOMBRE", currentItem.nombreLibro)
                putExtra("LIBRO_AUTOR", currentItem.autorLibro)
                putExtra("LIBRO_CATEGORIA", currentItem.categoriaLibro)
                putExtra("LIBRO_FECHA", currentItem.fechaLibro)
                putExtra("LIBRO_ISBN", currentItem.isbnLibro)
            }
            holder.itemView.context.startActivity(intent)
        }

        if (isEditable) {
            holder.updateButton?.visibility = View.VISIBLE
            holder.deleteButton?.visibility = View.VISIBLE

            //Configuración del evento
            holder.updateButton?.setOnClickListener {
                val intent = Intent(holder.itemView.context, EditarActivity::class.java).apply {
                    putExtra("LIBRO_ID", currentItem.libroId)
                    putExtra("LIBRO_NOMBRE", currentItem.nombreLibro)
                    putExtra("LIBRO_AUTOR", currentItem.autorLibro)
                    putExtra("LIBRO_CATEGORIA", currentItem.categoriaLibro)
                    putExtra("LIBRO_FECHA", currentItem.fechaLibro)
                    putExtra("LIBRO_ISBN", currentItem.isbnLibro)
                }
                holder.itemView.context.startActivity(intent)
            }

            //Configuración del evento
            holder.deleteButton?.setOnClickListener {

                val alertDialog = AlertDialog.Builder(holder.itemView.context)
                    .setTitle("Confirmación")
                    .setMessage("¿Estás seguro de que quieres eliminar este libro?")
                    .setPositiveButton("Sí") { dialog, _ ->
                        val ideliminar = currentItem.libroId ?: ""
                        eliminarLibro(holder.itemView.context, ideliminar)
                        dialog.dismiss()
                    }
                    .setNegativeButton("No") { dialog, _ ->
                        dialog.dismiss()
                    }.create()
                alertDialog.show()
            }
        } else {
            holder.updateButton?.visibility = View.GONE
            holder.deleteButton?.visibility = View.GONE
        }
    }

    //Función para eliminar libro
    private fun eliminarLibro(context: android.content.Context, libroId: String) {
        val database = FirebaseDatabase.getInstance().getReference("DatosLibros")
        database.child(libroId).removeValue().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(context, "Libro eliminado exitosamente", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Error eliminar", Toast.LENGTH_SHORT).show()
            }
        }
    }


    //Función para obtener el tamaño de la lista
    override fun getItemCount() = libroList.size
}