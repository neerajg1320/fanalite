package com.fanalite.rulesapp.retrofitFanalite.models

data class LoginUserRequest(
    val email:String,
    val password:String,
    val strategy:String
)