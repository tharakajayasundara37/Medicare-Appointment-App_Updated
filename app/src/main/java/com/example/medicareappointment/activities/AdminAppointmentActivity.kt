package com.example.medicareappointment.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.medicareappointment.adapter.AdminAppointmentAdapter
import com.example.medicareappointment.database.DatabaseHelper
import com.example.medicareappointment.databinding.ActivityAdminAppointmentBinding
import com.example.medicareappointment.model.Appointment

class AdminAppointmentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAdminAppointmentBinding
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var adapter: AdminAppointmentAdapter

    private val appointmentList = ArrayList<Appointment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAdminAppointmentBinding.inflate(layoutInflater)

        setContentView(binding.root)

        dbHelper = DatabaseHelper(this)

        setupRecyclerView()

        loadAppointments()
    }


    private fun setupRecyclerView() {

        adapter = AdminAppointmentAdapter(
            appointmentList
        ) { appointment, status ->

            updateAppointmentStatus(
                appointment,
                status
            )

        }


        binding.recyclerAdminAppointments.layoutManager =
            LinearLayoutManager(this)


        binding.recyclerAdminAppointments.adapter =
            adapter
    }


    private fun loadAppointments() {

        appointmentList.clear()

        appointmentList.addAll(
            dbHelper.getAllAppointments()
        )

        if (::adapter.isInitialized) {

            adapter.notifyDataSetChanged()

        }
    }


    private fun updateAppointmentStatus(
        appointment: Appointment,
        status: String
    ) {

        val result = dbHelper.updateAppointmentStatus(
            appointment.id,
            status
        )


        if (result > 0) {

            Toast.makeText(
                this,
                "Appointment $status",
                Toast.LENGTH_SHORT
            ).show()


            loadAppointments()

        } else {

            Toast.makeText(
                this,
                "Status update failed",
                Toast.LENGTH_SHORT
            ).show()

        }

    }


    override fun onResume() {

        super.onResume()

        if (::adapter.isInitialized) {

            loadAppointments()

        }

    }
}