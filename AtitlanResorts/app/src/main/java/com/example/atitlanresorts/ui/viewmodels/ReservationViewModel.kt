package com.example.atitlanresorts.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.atitlanresorts.data.AppDatabase
import com.example.atitlanresorts.data.entities.Habitacion
import com.example.atitlanresorts.data.entities.Reservation
import kotlinx.coroutines.withContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOn
import com.example.atitlanresorts.data.entities.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ReservationViewModel(application: Application) : AndroidViewModel(application) {

    private val reservationDao = AppDatabase.getDatabase(application).reservationDao()
    private val habitacionDao = AppDatabase.getDatabase(application).habitacionDao()
    private val userDao = AppDatabase.getDatabase(application).userDao()

    private val _reservations = MutableStateFlow<List<Reservation>>(emptyList())
    val reservations: StateFlow<List<Reservation>> = _reservations

    private val _habitaciones = MutableStateFlow<List<Habitacion>>(emptyList())
    val habitaciones: StateFlow<List<Habitacion>> = _habitaciones

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val habitacionesDB = habitacionDao.getAllHabitaciones()
            if (habitacionesDB.isEmpty()) {
                val iniciales = listOf(
                    Habitacion(tipo = "Colibrí", cantidadDisponible = 20, capacidad = 2),
                    Habitacion(tipo = "Luna de Miel", cantidadDisponible = 10, capacidad = 2)
                )
                iniciales.forEach { habitacionDao.insertHabitacion(it) }
                _habitaciones.value = iniciales
            } else {
                _habitaciones.value = habitacionesDB
            }

            _reservations.value = reservationDao.getAllReservations()
        }
    }

    fun getReservationById(id: Int) = reservationDao.getReservationById(id).flowOn(Dispatchers.IO)


    fun addReservation(reservation: Reservation, onComplete: (Int) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {

            val newReservationId = reservationDao.insertReservation(reservation).toInt()

            _reservations.value = reservationDao.getAllReservations()

            withContext(Dispatchers.Main) {
                onComplete(newReservationId)
            }
        }
    }

    fun getAllReservations() {
        viewModelScope.launch(Dispatchers.IO) {
            _reservations.value = reservationDao.getAllReservations()
        }
    }

    fun getHabitacionesDisponibles() {
        viewModelScope.launch(Dispatchers.IO) {
            _habitaciones.value = habitacionDao.getAllHabitaciones()
        }
    }

    fun calcularTotal(tipoHabitacion: String, cantidad: Int): Double {
        return when (tipoHabitacion) {
            "Colibrí" -> 100.0 * cantidad
            "Luna de Miel" -> 250.0 * cantidad
            else -> 0.0
        }
    }

    fun confirmarPago(idReserva: Int, tarjetaPago: String, onComplete: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val reservation = reservationDao.getReservationByIdImmediate(idReserva)

            if (reservation == null) {
                withContext(Dispatchers.Main) {

                }
                return@launch
            }

            reservationDao.updatePayment(reservation.id, tarjetaPago)

            val habitacion = habitacionDao.getHabitacionesByTipo(reservation.tipoHabitacion).firstOrNull()
            if (habitacion != null) {
                val nuevaCantidad = habitacion.cantidadDisponible - reservation.cantidadHabitaciones
                if (nuevaCantidad >= 0) {
                    habitacionDao.updateHabitacion(habitacion.copy(cantidadDisponible = nuevaCantidad))
                }
            }

            _habitaciones.value = habitacionDao.getAllHabitaciones()
            _reservations.value = reservationDao.getAllReservations()

            withContext(Dispatchers.Main) {
                onComplete()
            }
        }
    }

    fun getUserProfile(id: Int): Flow<User?> = userDao.getUserById(id).flowOn(Dispatchers.IO)

    fun countReservationsByUserId(idUsuario: Int): Flow<Int> = reservationDao.countReservationsByUserId(idUsuario).flowOn(Dispatchers.IO)


}



