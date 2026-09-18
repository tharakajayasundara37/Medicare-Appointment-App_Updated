package com.example.medicareappointment.activities


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.medicareappointment.adapter.DoctorAdapter
import com.example.medicareappointment.database.DatabaseHelper
import com.example.medicareappointment.databinding.ActivityDoctorListBinding



class DoctorListActivity : AppCompatActivity() {


    private lateinit var binding: ActivityDoctorListBinding

    private lateinit var dbHelper: DatabaseHelper


    private var patientId = -1





    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)


        binding =
            ActivityDoctorListBinding.inflate(layoutInflater)


        setContentView(binding.root)



        dbHelper =
            DatabaseHelper(this)



        patientId =
            intent.getIntExtra(

                "PATIENT_ID",

                -1

            )



        loadDoctors()


    }









    private fun loadDoctors() {


        val doctors =

            dbHelper.getAllDoctors()





        val adapter = DoctorAdapter(

            doctors

        ) { doctor ->





            val intent = Intent(

                this,

                AddAppointmentActivity::class.java

            )





            // Send patient ID

            if(patientId != -1){


                intent.putExtra(

                    "PATIENT_ID",

                    patientId

                )


            }







            // Send doctor details


            intent.putExtra(

                "DOCTOR_ID",

                doctor.id

            )



            intent.putExtra(

                "DOCTOR_NAME",

                doctor.name

            )





            startActivity(intent)



        }

        binding.recyclerDoctors.layoutManager =

            LinearLayoutManager(this)

        binding.recyclerDoctors.adapter = adapter

    }
}