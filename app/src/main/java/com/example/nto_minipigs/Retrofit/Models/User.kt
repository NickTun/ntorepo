package com.example.nto_minipigs.Retrofit.Models

data class User(
    val id: Int,
    val lastVisit: String,
    val login: String,
    val password: String,
    val name: String,
    val photo: String,
    val position: String,
    val entries: ArrayList<Entry>
)