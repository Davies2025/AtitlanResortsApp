package com.example.atitlanresorts.data.daos

import androidx.room.*
import com.example.atitlanresorts.data.entities.Habitacion

@Dao
interface HabitacionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHabitacion(habitacion: Habitacion)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHabitaciones(habitaciones: List<Habitacion>)

    @Query("SELECT * FROM habitaciones WHERE id = :id")
    suspend fun getHabitacionById(id: Int): Habitacion?

    @Query("SELECT * FROM habitaciones")
    suspend fun getAllHabitaciones(): List<Habitacion>

    @Query("SELECT * FROM habitaciones WHERE tipo = :tipo")
    suspend fun getHabitacionesByTipo(tipo: String): List<Habitacion>

    @Update
    suspend fun updateHabitacion(habitacion: Habitacion)

    @Delete
    suspend fun deleteHabitacion(habitacion: Habitacion)
}












