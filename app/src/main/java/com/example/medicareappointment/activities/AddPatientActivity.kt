package com.example.medicareappointment.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.medicareappointment.database.DatabaseHelper
import com.example.medicareappointment.databinding.ActivityAddPatientBinding

class AddPatientActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddPatientBinding
    private lateinit var dbHelper: DatabaseHelper

    private var isEditMode = false
    private var patientId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddPatientBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = DatabaseHelper(this)

        getIntentData()

        binding.btnSavePatient.setOnClickListener {
            saveOrUpdatePatient()
        }
    }

    private fun getIntentData() {
        patientId = intent.getIntExtra("patient_id", -1)

        if (patientId != -1) {
            isEditMode = true

            binding.etName.setText(intent.getStringExtra("patient_name"))
            binding.etAge.setText(intent.getIntExtra("patient_age", 0).toString())
            binding.etPhone.setText(intent.getStringExtra("patient_phone"))
            binding.etAddress.setText(intent.getStringExtra("patient_address"))

            binding.btnSavePatient.text = "Update Patient"
        }
    }

    private fun saveOrUpdatePatient() {
        val name = binding.etName.text.toString().trim()
        val ageText = binding.etAge.text.toString().trim()
        val phone = binding.etPhone.text.toString().trim()
        val address = binding.etAddress.text.toString().trim()

        if (name.isEmpty() || ageText.isEmpty() || phone.isEmpty() || address.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        if (phone.length != 10) {
            Toast.makeText(this, "Phone number must be 10 digits", Toast.LENGTH_SHORT).show()
            return
        }

        val age = ageText.toInt()

        if (isEditMode) {
            val result = dbHelper.updatePatient(patientId, name, age, phone, address)

            if (result > 0) {
                Toast.makeText(this, "Patient Updated Successfully", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Update Failed", Toast.LENGTH_SHORT).show()
            }
        } else {
            val result = dbHelper.insertPatient(name, age, phone, address)

            if (result > 0) {
                Toast.makeText(this, "Patient Registered Successfully", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Failed to Register Patient", Toast.LENGTH_SHORT).show()
            }
        }
    }
}