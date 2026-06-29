package com.example.medicareappointment.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.medicareappointment.database.DatabaseHelper
import com.example.medicareappointment.databinding.ActivityDashboardBinding

class DashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = DatabaseHelper(this)

        binding.btnPatients.setOnClickListener {
            startActivity(Intent(this, PatientListActivity::class.java))
        }

        binding.btnAppointments.setOnClickListener {
            startActivity(Intent(this, AppointmentListActivity::class.java))
        }

        binding.btnViewAllPatients.setOnClickListener {
            startActivity(Intent(this, PatientListActivity::class.java))
        }

        binding.btnViewAllAppointments.setOnClickListener {
            startActivity(Intent(this, AppointmentListActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        loadDashboardData()
    }

    private fun loadDashboardData() {
        binding.txtPatientCount.text = dbHelper.getPatientCount().toString()
        binding.txtAppointmentCount.text = dbHelper.getAppointmentCount().toString()

        val patients = dbHelper.getRecentPatients()

        binding.txtPatientName1.text = patients.getOrNull(0)?.name ?: "-"
        binding.txtPatientPhone1.text = patients.getOrNull(0)?.phone ?: "-"

        binding.txtPatientName2.text = patients.getOrNull(1)?.name ?: "-"
        binding.txtPatientPhone2.text = patients.getOrNull(1)?.phone ?: "-"

        val appointments = dbHelper.getRecentAppointments()

        binding.txtAppDate1.text = appointments.getOrNull(0)?.appointmentDate ?: "-"
        binding.txtAppPatient1.text = appointments.getOrNull(0)?.patientName ?: "-"

        binding.txtAppDate2.text = appointments.getOrNull(1)?.appointmentDate ?: "-"
        binding.txtAppPatient2.text = appointments.getOrNull(1)?.patientName ?: "-"
    }
}