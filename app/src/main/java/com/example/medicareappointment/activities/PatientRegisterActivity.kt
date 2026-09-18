package com.example.medicareappointment.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.medicareappointment.database.DatabaseHelper
import com.example.medicareappointment.databinding.ActivityPatientRegisterBinding

class PatientRegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPatientRegisterBinding
    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding =
            ActivityPatientRegisterBinding.inflate(layoutInflater)

        setContentView(binding.root)

        databaseHelper =
            DatabaseHelper(this)

        binding.btnRegisterPatient.setOnClickListener {
            registerPatient()
        }
    }

    private fun registerPatient() {

        val name =
            binding.etPatientName.text
                .toString()
                .trim()

        val ageText =
            binding.etPatientAge.text
                .toString()
                .trim()

        val phone =
            binding.etPatientPhone.text
                .toString()
                .trim()

        val address =
            binding.etPatientAddress.text
                .toString()
                .trim()

        val email =
            binding.etPatientEmail.text
                .toString()
                .trim()

        val password =
            binding.etPatientPassword.text
                .toString()
                .trim()


        // ================= VALIDATION =================

        if (
            name.isEmpty() ||
            ageText.isEmpty() ||
            phone.isEmpty() ||
            address.isEmpty() ||
            email.isEmpty() ||
            password.isEmpty()
        ) {

            Toast.makeText(
                this,
                "Please fill all fields",
                Toast.LENGTH_SHORT
            ).show()

            return
        }


        val age = ageText.toIntOrNull()

        if (age == null || age <= 0 || age > 120) {

            Toast.makeText(
                this,
                "Please enter a valid age",
                Toast.LENGTH_SHORT
            ).show()

            return
        }


        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

            Toast.makeText(
                this,
                "Please enter a valid email address",
                Toast.LENGTH_SHORT
            ).show()

            return
        }


        if (password.length < 6) {

            Toast.makeText(
                this,
                "Password must contain at least 6 characters",
                Toast.LENGTH_SHORT
            ).show()

            return
        }


        // ================= REGISTER PATIENT =================

        val result = databaseHelper.insertPatient(
            name,
            age,
            phone,
            address,
            email,
            password
        )


        // ================= SUCCESS =================

        if (result > 0) {

            Toast.makeText(
                this,
                "Registration Successful. Please login.",
                Toast.LENGTH_LONG
            ).show()


            val intent =
                Intent(
                    this,
                    LoginActivity::class.java
                )


            // Login screen එකේ Patient role select කරන්න
            intent.putExtra(
                "SELECT_ROLE",
                "Patient"
            )


            // Registered email auto fill කරන්න
            intent.putExtra(
                "EMAIL",
                email
            )


            // Previous Register screens remove කරලා Login එකට යන්න
            intent.flags =
                Intent.FLAG_ACTIVITY_CLEAR_TOP


            startActivity(intent)

            finish()


        } else {

            // Duplicate email වගේ database issue එකක් උනොත්
            Toast.makeText(
                this,
                "Registration failed. Email may already be registered.",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}