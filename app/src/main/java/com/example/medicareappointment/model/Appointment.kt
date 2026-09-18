package com.example.medicareappointment.model

data class Appointment(
    val id: Int,
    val patientName: String,
    val patientPhone: String,
    val doctorName: String,
    val appointmentDate: String,
    val appointmentTime: String,
    val appointmentStatus: String = "Pending"
)