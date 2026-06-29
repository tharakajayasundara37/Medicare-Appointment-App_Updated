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

        val recentPatients = dbHelper.getRecentPatients()
        binding.txtRecentPatients.text =
            if (recentPatients.isEmpty()) {
                "No recent patients"
            } else {
                recentPatients.joinToString("\n\n") {
                    "👤 ${it.name}\n📞 ${it.phone}"
                }
            }

        val recentAppointments = dbHelper.getRecentAppointments()
        binding.txtRecentAppointments.text =
            if (recentAppointments.isEmpty()) {
                "No recent appointments"
            } else {
                recentAppointments.joinToString("\n\n") {
                    "📅 ${it.appointmentDate}\n👤 ${it.patientName}\n🩺 ${it.doctorName}"
                }
            }
    }
}