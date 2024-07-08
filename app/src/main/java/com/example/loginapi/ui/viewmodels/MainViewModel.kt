// MainViewModel.kt
package com.example.loginapi.ui.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.loginapi.repositories.PreferencesRepository
import com.example.loginapi.repositories.UserRepository

class MainViewModel : ViewModel() {
    private val _errorMessage: MutableLiveData<String> by lazy {
        MutableLiveData<String>("")
    }
    val errorMessage: LiveData<String> get() = _errorMessage

    private val _loginSuccess: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }
    val loginSuccess: LiveData<Boolean> get() = _loginSuccess

    private val _registerSuccess: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }
    val registerSuccess: LiveData<Boolean> get() = _registerSuccess

    fun login(email: String, password: String, context: Context) {
        UserRepository.doLogin(email,
            password,
            success = {
                if(it == null) {
                    _errorMessage.value = "Usuario o contraseña incorrectos"
                    return@doLogin
                }
                _errorMessage.value = ""
                Log.d("MainViewModel", "Token: ${it.access_token}")
                val token: String = it.access_token!!
                PreferencesRepository.saveToken(token, context)
                _loginSuccess.value = true
            }, failure = {
                it.printStackTrace()
                _errorMessage.value = "Error al iniciar sesión"
            })
    }

    fun register(name: String, email: String, password: String, phone: String) {
        UserRepository.doRegister(name, email, password, phone,
            success = {
                if (it?.success == true) {
                    _registerSuccess.value = true
                    _errorMessage.value = ""
                } else {
                    _registerSuccess.value = false
                    _errorMessage.value = it?.message ?: "Error en el registro"
                }
            }, failure = {
                /*it.printStackTrace()*/
                _registerSuccess.value = false
                _errorMessage.value = "Error en el registro"
            })
    }
}
