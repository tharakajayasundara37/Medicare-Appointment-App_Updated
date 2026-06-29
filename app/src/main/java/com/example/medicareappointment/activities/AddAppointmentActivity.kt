package com.example.medicareappointment.activities

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.medicareappointment.database.DatabaseHelper
import com.example.medicareappointment.databinding.ActivityAddAppointmentBinding
import java.util.Calendar

class AddAppointmentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddAppointmentBinding
    private lateinit var dbHelper: DatabaseHelper

    private var isEditMode = false
    private var appointmentId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddAppointmentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = DatabaseHelper(this)

        binding.etAppointmentDate.isFocusable = false
        binding.etAppointmentTime.isFocusable = false

        getIntentData()

        binding.etAppointmentDate.setOnClickListener {
            showDatePicker()
        }

        binding.etAppointmentTime.setOnClickListener {
            showTimePicker()
        }

        binding.btnSaveAppointment.setOnClickListener {
            saveOrUpdateAppointment()
        }
    }

    private fun getIntentData() {
        appointmentId = intent.getIntExtra("appointment_id", -1)

        if (appointmentId != -1) {
            isEditMode = true

            binding.etPatientName.setText(intent.getStringExtra("patient_name"))
            binding.etPatientPhone.setText(intent.getStringExtra("patient_phone"))
            binding.etDoctorName.setText(intent.getStringExtra("doctor_name"))
            binding.etAppointmentDate.setText(intent.getStringExtra("appointment_date"))
            binding.etAppointmentTime.setText(intent.getStringExtra("appointment_time"))

            binding.btnSaveAppointment.text = "Update Appointment"
        }
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()

        DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                val selectedDate = String.format("%02d/%02d/%04d", dayOfMonth, month + 1, year)
                binding.etAppointmentDate.setText(selectedDate)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun showTimePicker() {
        val calendar = Calendar.getInstance()

        TimePickerDialog(
            this,
            { _, hourOfDay, minute ->
                val amPm = if (hourOfDay >= 12) "PM" else "AM"
                val hour12 = when {
                    hourOfDay == 0 -> 12
                    hourOfDay > 12 -> hourOfDay - 12
                    else -> hourOfDay
                }

                val selectedTime = String.format("%02d:%02d %s", hour12, minute, amPm)
                binding.etAppointmentTime.setText(selectedTime)
            },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            false
        ).show()
    }

    private fun saveOrUpdateAppointment() {
        val patientName = binding.etPatientName.text.toString().trim()
        val patientPhone = binding.etPatientPhone.text.toString().trim()
        val doctorName = binding.etDoctorName.text.toString().trim()
        val date = binding.etAppointmentDate.text.toString().trim()
        val time = binding.etAppointmentTime.text.toString().trim()

        if (
            patientName.isEmpty() ||
            patientPhone.isEmpty() ||
            doctorName.isEmpty() ||
            date.isEmpty() ||
            time.isEmpty()
        ) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        if (patientPhone.length != 10) {
            Toast.makeText(this, "Phone number must be 10 digits", Toast.LENGTH_SHORT).show()
            return
        }

        if (isEditMode) {
            val result = dbHelper.updateAppointment(
                appointmentId,
                patientName,
                patientPhone,
                doctorName,
                date,
                time
            )

            if (result > 0) {
                Toast.makeText(this, "Appointment Updated Successfully", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Update Failed", Toast.LENGTH_SHORT).show()
            }
        } else {
            val result = dbHelper.insertAppointment(
                patientName,
                patientPhone,
                doctorName,
                date,
                time
            )

            if (result > 0) {
                Toast.makeText(this, "Appointment Created Successfully", Toast.LENGTH_SHORT).show()
                sendConfirmationSMS(patientPhone, patientName, doctorName, date, time)
                finish()
            } else {
                Toast.makeText(this, "Failed to Create Appointment", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun sendConfirmationSMS(
        phone: String,
        patient: String,
        doctor: String,
        date: String,
        time: String
    ) {
        val message = """
            Appointment Confirmed
            
            Patient : $patient
            Doctor : $doctor
            Date : $date
            Time : $time
            
            Thank you for choosing MediCare Clinic.
        """.trimIndent()

        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("smsto:$phone")
            putExtra("sms_body", message)
        }

        startActivity(intent)
    }
}