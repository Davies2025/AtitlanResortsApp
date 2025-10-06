package com.example.atitlanresorts.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.atitlanresorts.data.daos.UserDao
import com.example.atitlanresorts.data.entities.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel(private val userDao: UserDao) : ViewModel() {

    private val _loginSuccess = MutableStateFlow<Boolean?>(null)
    val loginSuccess: StateFlow<Boolean?> = _loginSuccess

    fun login(gmail: String, password: String) {
        viewModelScope.launch {
            val user = userDao.getUserByGmailAndPassword(gmail, password)
            _loginSuccess.value = user != null
        }
    }
}
