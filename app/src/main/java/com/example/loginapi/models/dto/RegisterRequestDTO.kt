package com.example.loginapi.models.dto

data class RegisterRequestDTO (
    var name: String,
    var email: String,
    var password: String,
    var phone: String
)