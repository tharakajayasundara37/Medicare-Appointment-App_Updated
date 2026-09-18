package com.example.medicareappointment.model


data class Patient(

    val id: Int,

    val patientCode: String,

    val name: String,

    val age: Int,

    val phone: String,

    val address: String,

    val email: String,

    val password: String,

    val profileImage: String,

    val createdDate: String

)