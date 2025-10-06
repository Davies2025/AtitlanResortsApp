package com.example.atitlanresorts.data.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete
import com.example.atitlanresorts.data.entities.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM users WHERE gmail = :gmail AND password = :password LIMIT 1")
    suspend fun getUserByGmailAndPassword(gmail: String, password: String): User?

    @Update
    suspend fun updateUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)

    @Query("SELECT * FROM users")
    suspend fun getAllUsers(): List<User>

    @Query("SELECT * FROM users WHERE gmail = :gmail LIMIT 1")
    suspend fun getUserByGmail(gmail: String): User?

    //Para perfil
    @Query("SELECT * FROM users WHERE id = :userId")
    fun getUserById(userId: Int): Flow<User?>
}
























//ELABORADO POR EL MEDALLISTA

/*@Dao
interface UserDao {


    @Insert
    suspend fun insertUser(user: User)


    @Query("SELECT * FROM users WHERE gmail = :gmail LIMIT 1")
    suspend fun getUserByGmail(gmail: String): User?


    @Update
    suspend fun updateUser(user: User)


    @Delete
    suspend fun deleteUser(user: User)


    @Query("SELECT * FROM users")
    suspend fun getAllUsers(): List<User>
}
*/