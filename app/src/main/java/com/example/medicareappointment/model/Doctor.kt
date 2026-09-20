package com.example.medicareappointment.model


data class Doctor(

    val id: Int = 0,


    val name: String,


    val specialization: String,


    val phone: String,


    val email: String,


    val password: String = "",


    val qualification: String = "",


    val registrationNumber: String = "",


    val experience: String = "",


    // Qualification document path
    val document: String = "",


    // Doctor profile image path
    val profileImage: String = "",


    val workingDays: String = "",


    val startTime: String = "",


    val endTime: String = "",



    // Appointment slot duration in minutes
    // Example: 15, 30, 60
    val appointmentDuration: Int = 30,



    // Admin approval status
    // Pending / Approved / Rejected
    val status: String = "Pending"

)