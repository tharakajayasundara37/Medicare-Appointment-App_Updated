package com.example.medicareappointment.activities

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.medicareappointment.adapter.AppointmentAdapter
import com.example.medicareappointment.database.DatabaseHelper
import com.example.medicareappointment.databinding.ActivityAppointmentListBinding
import com.example.medicareappointment.model.Appointment

class AppointmentListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAppointmentListBinding
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAppointmentListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = DatabaseHelper(this)
        binding.recyclerAppointments.layoutManager = LinearLayoutManager(this)

        binding.btnAddAppointment.setOnClickListener {
            startActivity(Intent(this, AddAppointmentActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        loadAppointments()
    }

    private fun loadAppointments() {
        val appointmentList = dbHelper.getAllAppointments()

        val adapter = AppointmentAdapter(
            appointmentList,
            onDeleteClick = { appointment ->
                showCancelDialog(appointment)
            },
            onUpdateClick = { appointment ->
                val intent = Intent(this, AddAppointmentActivity::class.java)
                intent.putExtra("appointment_id", appointment.id)
                intent.putExtra("patient_name", appointment.patientName)
                intent.putExtra("patient_phone", appointment.patientPhone)
                intent.putExtra("doctor_name", appointment.doctorName)
                intent.putExtra("appointment_date", appointment.appointmentDate)
                intent.putExtra("appointment_time", appointment.appointmentTime)
                startActivity(intent)
            }
        )

        binding.recyclerAppointments.adapter = adapter
    }

    private fun showCancelDialog(appointment: Appointment) {
        AlertDialog.Builder(this)
            .setTitle("Cancel Appointment")
            .setMessage("Are you sure you want to cancel this appointment?")
            .setPositiveButton("Cancel Appointment") { _, _ ->
                dbHelper.deleteAppointment(appointment.id)
                Toast.makeText(this, "Appointment Cancelled", Toast.LENGTH_SHORT).show()
                loadAppointments()
            }
            .setNegativeButton("No", null)
            .show()
    }
}