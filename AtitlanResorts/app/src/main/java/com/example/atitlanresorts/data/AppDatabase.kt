package com.example.atitlanresorts.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.atitlanresorts.data.daos.UserDao
import com.example.atitlanresorts.data.daos.ReservationDao
import com.example.atitlanresorts.data.daos.HabitacionDao
import com.example.atitlanresorts.data.entities.User
import com.example.atitlanresorts.data.entities.Reservation
import com.example.atitlanresorts.data.entities.Habitacion
import com.example.atitlanresorts.data.entities.Converters

@Database(
    entities = [User::class, Reservation::class, Habitacion::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun reservationDao(): ReservationDao
    abstract fun habitacionDao(): HabitacionDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "atitlan_resorts_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}

