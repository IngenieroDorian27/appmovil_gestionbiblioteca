package com.example.gestionbiblioteca.menubiblioteca.crud


//Modelo de datos para Firebase
data class DataLibro(
    val libroId: String? = "",
    val nombreLibro: String? = "",
    val autorLibro: String? = "",
    val categoriaLibro: String? = "",
    val fechaLibro: String? = "",
    val isbnLibro: String? = ""
)
