package com.example.atitlanresorts.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "full_name")
    val fullName: String,

    @ColumnInfo(name = "gmail")
    val gmail: String,

    @ColumnInfo(name = "password")
    val password: String,

    @ColumnInfo(name = "phone")
    val phone: String
)




