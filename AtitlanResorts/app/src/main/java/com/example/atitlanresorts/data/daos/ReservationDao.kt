package com.example.atitlanresorts.data.daos

import androidx.room.*
import com.example.atitlanresorts.data.entities.Reservation
import kotlinx.coroutines.flow.Flow

@Dao
interface ReservationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReservation(reservation: Reservation): Long


    @Query("SELECT * FROM reservations")
    suspend fun getAllReservations(): List<Reservation>

    @Update
    suspend fun updateReservation(reservation: Reservation)

    @Delete
    suspend fun deleteReservation(reservation: Reservation)

    @Query("SELECT * FROM reservations WHERE tipoHabitacion = :tipoHabitacion")
    suspend fun getReservationsByTipoHabitacion(tipoHabitacion: String): List<Reservation>

    @Query("UPDATE reservations SET tarjetaPago = :tarjetaPago WHERE id = :reservationId")
    suspend fun updatePayment(reservationId: Int, tarjetaPago: String)

    @Query("SELECT * FROM reservations WHERE id = :id")
    suspend fun getReservationByIdImmediate(id: Int): Reservation?

    @Query("SELECT * FROM reservations WHERE id = :id")
    fun getReservationById(id: Int): Flow<Reservation?>


    @Query("SELECT COUNT(id) FROM reservations WHERE idUsuario = :idUsuario")
    fun countReservationsByUserId(idUsuario: Int): Flow<Int>

}
