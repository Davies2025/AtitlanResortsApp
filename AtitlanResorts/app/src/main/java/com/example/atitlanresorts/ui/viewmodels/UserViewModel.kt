package com.example.atitlanresorts.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.atitlanresorts.data.AppDatabase
import com.example.atitlanresorts.data.entities.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


enum class RegisterResult { SUCCESS, EMAIL_ALREADY_EXISTS }

class UserViewModel(application: Application) : AndroidViewModel(application) {

    private val userDao = AppDatabase.getDatabase(application).userDao()

    private val _loginResult = MutableStateFlow<User?>(null)
    val loginResult: StateFlow<User?> = _loginResult.asStateFlow()


    fun registerUser(user: User, onComplete: (RegisterResult) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {


            val existingUser = userDao.getUserByGmail(user.gmail)

            val result = if (existingUser != null) {

                RegisterResult.EMAIL_ALREADY_EXISTS
            } else {

                userDao.insertUser(user)
                RegisterResult.SUCCESS
            }


            withContext(Dispatchers.Main) {
                onComplete(result)
            }
        }
    }

    fun login(gmail: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val user = userDao.getUserByGmailAndPassword(gmail, password)
            _loginResult.value = user
        }
    }

    fun clearLoginResult() {
        _loginResult.value = null
    }
}

