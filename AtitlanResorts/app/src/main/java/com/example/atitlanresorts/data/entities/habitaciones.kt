package com.example.atitlanresorts.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "habitaciones")
data class Habitacion(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val tipo: String,
    val capacidad: Int,
    val cantidadDisponible: Int
)