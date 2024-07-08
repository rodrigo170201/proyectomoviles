// UserRepository.kt
package com.example.loginapi.repositories

import android.util.Log
import com.example.loginapi.api.APIProyecto
import com.example.loginapi.models.dto.LoginRequestDTO
import com.example.loginapi.models.dto.LoginResponseDTO
import com.example.loginapi.models.dto.RegisterRequestDTO
import com.example.loginapi.models.dto.RegisterResponseDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object UserRepository {
    fun doLogin(
        email: String,
        password: String,
        success: (LoginResponseDTO?) -> Unit,
        failure: (Throwable) -> Unit
    ) {
        val retrofit = RetrofitRepository.getRetrofitInstance()

        val service: APIProyecto =
            retrofit.create(APIProyecto::class.java)
        service.login(LoginRequestDTO(email, password))
            .enqueue(object : Callback<LoginResponseDTO> {
                override fun onResponse(
                    res: Call<LoginResponseDTO>,
                    response: Response<LoginResponseDTO>
                ) {
                    if (response.code() == 401) {
                        success(null)
                        return
                    }
                    val loginResponse = response.body()
                    success(loginResponse)
                }

                override fun onFailure(res: Call<LoginResponseDTO>, t: Throwable) {
                    failure(t)
                }
            })
    }
    fun doRegister(
        name: String,
        email: String,
        password: String,
        phone: String,
        success: (RegisterResponseDTO?) -> Unit,
        failure: (String) -> Unit
    ) {
        val retrofit = RetrofitRepository.getRetrofitInstance()

        val service: APIProyecto = retrofit.create(APIProyecto::class.java)

        val request = RegisterRequestDTO(name, email, password, phone)
        Log.d("UserRepository", "Sending register request: $request")

        service.register(request).enqueue(object : Callback<RegisterResponseDTO> {
            override fun onResponse(
                res: Call<RegisterResponseDTO>,
                response: Response<RegisterResponseDTO>
            ) {
                Log.d("UserRepository", "Response code: ${response.code()}")
                Log.d("UserRepository", "Response body: ${response.body()}")

                when (response.code()) {
                    200 -> {
                        val registerResponse = response.body()
                        success(registerResponse)
                        Log.d("UserRepository", "Register response: $registerResponse")
                    }
                    409 -> {
                        failure("El correo electrónico ya está en uso.")
                        Log.e("UserRepository", "Register failed with code 409: El correo electrónico ya está en uso.")
                    }
                    else -> {
                        failure("Error en el registro")
                        Log.e("UserRepository", "Register failed with code ${response.code()}")
                    }
                }
            }

            override fun onFailure(res: Call<RegisterResponseDTO>, t: Throwable) {
                failure(t.message ?: "Error de conexión")
                Log.e("UserRepository", "Register request failed", t)
            }
        })
    }
}
