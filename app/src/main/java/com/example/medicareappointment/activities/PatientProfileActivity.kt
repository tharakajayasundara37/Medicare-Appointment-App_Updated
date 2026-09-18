package com.example.medicareappointment.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.medicareappointment.databinding.ActivityPatientProfileBinding


class PatientProfileActivity : AppCompatActivity() {


    private lateinit var binding: ActivityPatientProfileBinding


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)


        binding =
            ActivityPatientProfileBinding.inflate(layoutInflater)


        setContentView(binding.root)

    }

}