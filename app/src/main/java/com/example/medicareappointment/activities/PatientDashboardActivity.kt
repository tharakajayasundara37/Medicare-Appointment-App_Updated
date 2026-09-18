package com.example.medicareappointment.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.medicareappointment.R
import com.example.medicareappointment.database.DatabaseHelper
import com.example.medicareappointment.databinding.ActivityPatientDashboardBinding


class PatientDashboardActivity : AppCompatActivity() {


    private lateinit var binding: ActivityPatientDashboardBinding

    private lateinit var databaseHelper: DatabaseHelper


    private var patientId: Int = -1



    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)


        binding =
            ActivityPatientDashboardBinding.inflate(layoutInflater)


        setContentView(binding.root)



        databaseHelper =
            DatabaseHelper(this)



        patientId =
            intent.getIntExtra(
                "PATIENT_ID",
                -1
            )



        if(patientId == -1){


            Toast.makeText(

                this,

                "Patient session expired",

                Toast.LENGTH_SHORT

            ).show()


            openLogin()

            return

        }



        setupButtons()


        loadDashboardData()

    }





    private fun loadDashboardData(){


        val patient =
            databaseHelper.getPatientById(
                patientId
            )



        if(patient != null){



            binding.txtWelcomeName.text =
                "Welcome, ${patient.name}"



            binding.txtPatientCode.text =
                patient.patientCode



            if(patient.profileImage.isNotEmpty()){


                try {


                    binding.imgPatientProfile.setImageURI(

                        Uri.parse(
                            patient.profileImage
                        )

                    )


                }catch(e:Exception){


                    binding.imgPatientProfile.setImageResource(
                        R.drawable.ic_profile
                    )

                }


            }else{


                binding.imgPatientProfile.setImageResource(
                    R.drawable.ic_profile
                )

            }



        }




        // Appointment statistics


        binding.txtAppointmentCount.text =

            databaseHelper.getPatientAppointmentCount(
                patientId
            ).toString()



        binding.txtUpcomingCount.text =

            databaseHelper.getUpcomingAppointmentCount(
                patientId
            ).toString()


    }





    private fun setupButtons(){



        // Book Appointment

        binding.btnBookAppointment.setOnClickListener {


            val intent = Intent(

                this,

                AddAppointmentActivity::class.java

            )


            intent.putExtra(

                "PATIENT_ID",

                patientId

            )


            startActivity(intent)


        }




        // My Appointments

        binding.btnMyAppointments.setOnClickListener {


            val intent = Intent(

                this,

                PatientAppointmentActivity::class.java

            )


            intent.putExtra(

                "PATIENT_ID",

                patientId

            )


            startActivity(intent)


        }




// Health Services

        binding.btnHealthServices.setOnClickListener {


            val intent = Intent(

                this,

                DashboardActivity::class.java

            )


            intent.putExtra(

                "PATIENT_ID",

                patientId

            )


            startActivity(intent)


        }




        // Profile

        binding.btnMyProfile.setOnClickListener {


            val intent = Intent(

                this,

                PatientProfileActivity::class.java

            )


            intent.putExtra(

                "PATIENT_ID",

                patientId

            )


            startActivity(intent)


        }




        binding.imgPatientProfile.setOnClickListener {


            val intent = Intent(

                this,

                PatientProfileActivity::class.java

            )


            intent.putExtra(

                "PATIENT_ID",

                patientId

            )


            startActivity(intent)


        }




        // Logout

        binding.btnLogout.setOnClickListener {


            openLogin()

        }


    }





    private fun openLogin(){


        val intent = Intent(

            this,

            LoginActivity::class.java

        )


        intent.flags =

            Intent.FLAG_ACTIVITY_NEW_TASK or
                    Intent.FLAG_ACTIVITY_CLEAR_TASK



        startActivity(intent)


        finish()


    }





    override fun onResume(){


        super.onResume()



        if(

            ::databaseHelper.isInitialized &&
            patientId != -1

        ){

            loadDashboardData()

        }


    }


}