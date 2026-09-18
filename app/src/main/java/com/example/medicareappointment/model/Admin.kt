package com.example.medicareappointment.model


data class Admin(

    val id: Int = 0,

    val name: String,

    val email: String,

    val phone: String = "",

    val password: String,

    val profileImage: String = "",

    val role: String = "Super Admin",

    val createdDate: String = ""

)