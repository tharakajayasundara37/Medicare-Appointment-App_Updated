package com.example.medicareappointment.activities


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.medicareappointment.adapter.AdminRecentAppointmentAdapter
import com.example.medicareappointment.database.DatabaseHelper
import com.example.medicareappointment.databinding.ActivityAdminDashboardBinding
import com.example.medicareappointment.model.Appointment


class AdminDashboardActivity : AppCompatActivity() {


    private lateinit var binding: ActivityAdminDashboardBinding


    private lateinit var dbHelper: DatabaseHelper


    private lateinit var recentAppointmentAdapter: AdminRecentAppointmentAdapter


    private val recentAppointmentList =
        ArrayList<Appointment>()



    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)


        binding =
            ActivityAdminDashboardBinding.inflate(layoutInflater)


        setContentView(binding.root)



        dbHelper =
            DatabaseHelper(this)



        // Load admin profile image
        loadAdminProfileImage()



        setupRecentAppointments()


        loadDashboardData()



        binding.btnManageAppointments.setOnClickListener {


            startActivity(

                Intent(

                    this,

                    AdminAppointmentActivity::class.java

                )

            )

        }



        binding.imgAdminProfileIcon.setOnClickListener {


            startActivity(

                Intent(

                    this,

                    AdminProfileActivity::class.java

                )

            )

        }



        binding.btnManageDoctors.setOnClickListener {


            startActivity(

                Intent(

                    this,

                    DoctorManagementActivity::class.java

                )

            )

        }



        binding.btnManagePatients.setOnClickListener {


            startActivity(

                Intent(

                    this,

                    PatientListActivity::class.java

                )

            )

        }


    }




    private fun loadAdminProfileImage() {


        val admin = dbHelper.getAdminProfile()



        if (

            admin != null &&

            admin.profileImage.isNotEmpty()

        ) {


            try {


                binding.imgAdminProfileIcon.setImageURI(

                    Uri.parse(admin.profileImage)

                )


            } catch (e: Exception) {


                e.printStackTrace()


            }


        }


    }





    private fun setupRecentAppointments() {


        recentAppointmentAdapter =

            AdminRecentAppointmentAdapter(

                recentAppointmentList

            )



        binding.recyclerRecentAppointments.layoutManager =

            LinearLayoutManager(this)



        binding.recyclerRecentAppointments.adapter =

            recentAppointmentAdapter


    }





    private fun loadDashboardData() {



        binding.txtTotalAppointments.text =

            dbHelper.getAppointmentCount()
                .toString()



        binding.txtTotalPatients.text =

            dbHelper.getPatientCount()
                .toString()



        binding.txtTotalDoctors.text =

            dbHelper.getDoctorCount()
                .toString()



        binding.txtPendingCount.text =

            "Pending : ${
                dbHelper.getAppointmentStatusCount(
                    "Pending"
                )
            }"



        binding.txtConfirmedCount.text =

            "Confirmed : ${
                dbHelper.getAppointmentStatusCount(
                    "Confirmed"
                )
            }"



        binding.txtCompletedCount.text =

            "Completed : ${
                dbHelper.getAppointmentStatusCount(
                    "Completed"
                )
            }"



        binding.txtCancelledCount.text =

            "Cancelled : ${
                dbHelper.getAppointmentStatusCount(
                    "Cancelled"
                )
            }"



        loadRecentAppointments()


    }





    private fun loadRecentAppointments() {


        recentAppointmentList.clear()


        recentAppointmentList.addAll(

            dbHelper.getRecentAppointments()

        )



        if(::recentAppointmentAdapter.isInitialized) {


            recentAppointmentAdapter.notifyDataSetChanged()


        }


    }





    override fun onResume() {


        super.onResume()



        if(::dbHelper.isInitialized) {


            loadDashboardData()


            // Refresh profile image after returning from profile page
            loadAdminProfileImage()


        }


    }


}