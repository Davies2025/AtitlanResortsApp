package com.example.atitlanresorts.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.atitlanresorts.data.AppDatabase
import com.example.atitlanresorts.data.entities.Habitacion
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HabitacionViewModel(application: Application) : AndroidViewModel(application) {

    private val habitacionDao = AppDatabase.getDatabase(application).habitacionDao()

    private val _habitaciones = MutableStateFlow<List<Habitacion>>(emptyList())
    val habitaciones: StateFlow<List<Habitacion>> = _habitaciones

    fun addHabitacion(habitacion: Habitacion, onComplete: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            habitacionDao.insertHabitacion(habitacion)
            onComplete()
        }
    }

    fun getAllHabitaciones() {
        viewModelScope.launch(Dispatchers.IO) {
            _habitaciones.value = habitacionDao.getAllHabitaciones()
        }
    }

    fun updateHabitacion(habitacion: Habitacion, onComplete: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            habitacionDao.updateHabitacion(habitacion)
            onComplete()
        }
    }
}
