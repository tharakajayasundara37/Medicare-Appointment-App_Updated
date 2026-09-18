package com.example.medicareappointment.activities


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.medicareappointment.adapter.PatientAppointmentAdapter
import com.example.medicareappointment.database.DatabaseHelper
import com.example.medicareappointment.databinding.ActivityPatientAppointmentBinding


class PatientAppointmentActivity : AppCompatActivity() {


    private lateinit var binding: ActivityPatientAppointmentBinding

    private lateinit var dbHelper: DatabaseHelper

    private lateinit var adapter: PatientAppointmentAdapter


    private var patientId = -1



    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)


        binding =
            ActivityPatientAppointmentBinding.inflate(layoutInflater)


        setContentView(binding.root)



        dbHelper =
            DatabaseHelper(this)



        patientId =
            intent.getIntExtra(
                "PATIENT_ID",
                -1
            )



        setupRecyclerView()


        loadAppointments()

    }





    private fun setupRecyclerView(){


        adapter =
            PatientAppointmentAdapter(
                ArrayList()
            )



        binding.recyclerAppointments.layoutManager =
            LinearLayoutManager(this)



        binding.recyclerAppointments.adapter =
            adapter


    }





    private fun loadAppointments(){


        val list =
            dbHelper.getPatientAppointments(
                patientId
            )



        adapter.updateList(list)



        if(list.isEmpty()){

            binding.txtEmpty.text =
                "No appointments found"

        }
        else{

            binding.txtEmpty.text =
                ""

        }

    }


}