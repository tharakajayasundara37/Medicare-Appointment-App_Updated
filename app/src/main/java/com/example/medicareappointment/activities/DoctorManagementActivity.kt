package com.example.medicareappointment.activities


import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.medicareappointment.adapter.DoctorApplicationAdapter
import com.example.medicareappointment.database.DatabaseHelper
import com.example.medicareappointment.databinding.ActivityDoctorManagementBinding


class DoctorManagementActivity : AppCompatActivity() {


    private lateinit var binding: ActivityDoctorManagementBinding

    private lateinit var databaseHelper: DatabaseHelper

    private lateinit var adapter: DoctorApplicationAdapter


    private var currentStatus = "Pending"



    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)


        binding =
            ActivityDoctorManagementBinding.inflate(layoutInflater)


        setContentView(binding.root)



        databaseHelper =
            DatabaseHelper(this)



        setupRecyclerView()

        setupFilterButtons()

        loadDoctorCounts()

        loadDoctors(currentStatus)

    }





    private fun setupRecyclerView() {


        binding.recyclerDoctorManagement.layoutManager =

            LinearLayoutManager(this)


    }





    private fun setupFilterButtons() {


        binding.btnPendingDoctors.setOnClickListener {


            currentStatus = "Pending"

            loadDoctors(currentStatus)

        }




        binding.btnApprovedDoctors.setOnClickListener {


            currentStatus = "Approved"

            loadDoctors(currentStatus)

        }




        binding.btnRejectedDoctors.setOnClickListener {


            currentStatus = "Rejected"

            loadDoctors(currentStatus)

        }


    }





    private fun loadDoctorCounts() {


        binding.txtPendingDoctorCount.text =

            databaseHelper
                .getDoctorStatusCount("Pending")
                .toString()



        binding.txtApprovedDoctorCount.text =

            databaseHelper
                .getDoctorStatusCount("Approved")
                .toString()



        binding.txtRejectedDoctorCount.text =

            databaseHelper
                .getDoctorStatusCount("Rejected")
                .toString()


    }





    private fun loadDoctors(status: String) {


        val doctors =

            databaseHelper.getDoctorsByStatus(status)



        Log.d(

            "DOCTOR_LIST",

            "$status doctors = ${doctors.size}"

        )



        adapter =

            DoctorApplicationAdapter(

                doctors

            ) { id, newStatus ->



                databaseHelper.updateDoctorStatus(

                    id,

                    newStatus

                )



                loadDoctorCounts()

                loadDoctors(currentStatus)


            }



        binding.recyclerDoctorManagement.adapter =

            adapter


    }





    override fun onResume() {


        super.onResume()



        if (::databaseHelper.isInitialized) {


            loadDoctorCounts()

            loadDoctors(currentStatus)

        }


    }


}