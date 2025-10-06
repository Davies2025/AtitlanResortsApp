package com.example.atitlanresorts.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "reservations")
data class Reservation(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val idUsuario: Int,
    val nombreEncargado: String,
    val tipoHabitacion: String,
    val cantidadHuespedes: Int,
    val cantidadHabitaciones: Int,
    val fechaLlegada: Date,
    val fechaSalida: Date,
    val totalPagar: Double,
    var tarjetaPago: String? = null
)


