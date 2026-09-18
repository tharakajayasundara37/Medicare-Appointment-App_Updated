package com.example.medicareappointment.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.medicareappointment.databinding.ActivityRegisterSelectionBinding

class RegisterSelectionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterSelectionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding =
            ActivityRegisterSelectionBinding.inflate(layoutInflater)

        setContentView(binding.root)


        // Patient Registration
        binding.btnPatientRegister.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    PatientRegisterActivity::class.java
                )
            )
        }


        // Doctor Application
        binding.btnDoctorRegister.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    DoctorRegisterActivity::class.java
                )
            )
        }


        // Back to Login
        binding.txtBackToLogin.setOnClickListener {
            finish()
        }
    }
}