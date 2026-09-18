package com.example.medicareappointment.activities


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.medicareappointment.database.DatabaseHelper
import com.example.medicareappointment.databinding.ActivityDashboardBinding



class DashboardActivity : AppCompatActivity() {


    private lateinit var binding: ActivityDashboardBinding

    private lateinit var dbHelper: DatabaseHelper


    private var patientId = -1





    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)


        binding =
            ActivityDashboardBinding.inflate(layoutInflater)


        setContentView(binding.root)



        dbHelper =
            DatabaseHelper(this)




        patientId =
            intent.getIntExtra(

                "PATIENT_ID",

                -1

            )







        // My Family / Patient Profiles


        binding.btnPatients.setOnClickListener {


            if(patientId != -1){


                startActivity(

                    Intent(

                        this,

                        PatientProfileActivity::class.java

                    ).apply {


                        putExtra(

                            "PATIENT_ID",

                            patientId

                        )


                    }

                )


            }
            else{


                startActivity(

                    Intent(

                        this,

                        PatientListActivity::class.java

                    )

                )


            }


        }








        // Appointment


        binding.btnAppointments.setOnClickListener {


            if(patientId != -1){


                startActivity(

                    Intent(

                        this,

                        AddAppointmentActivity::class.java

                    ).apply {


                        putExtra(

                            "PATIENT_ID",

                            patientId

                        )


                    }

                )


            }
            else{


                startActivity(

                    Intent(

                        this,

                        LoginActivity::class.java

                    )

                )


            }


        }









        // Admin Login


        binding.btnAdminLogin.setOnClickListener {


            startActivity(

                Intent(

                    this,

                    LoginActivity::class.java

                )

            )


        }









        // Find Doctor


        binding.btnFindDoctor.setOnClickListener {



            val intent = Intent(

                this,

                DoctorListActivity::class.java

            )



            if(patientId != -1){


                intent.putExtra(

                    "PATIENT_ID",

                    patientId

                )


            }



            startActivity(intent)



        }









        // Lab Tests


        binding.btnLabTests.setOnClickListener {


            openService(

                HealthServiceActivity.LABS

            )


        }









        // Medicine


        binding.btnMedicine.setOnClickListener {


            openService(

                HealthServiceActivity.MEDICINE

            )


        }









        // Video Care


        binding.btnTelehealth.setOnClickListener {


            openService(

                HealthServiceActivity.TELEHEALTH

            )


        }









        // Health Packages


        binding.btnHealthPackages.setOnClickListener {


            openService(

                HealthServiceActivity.PACKAGES

            )


        }









        // Emergency


        binding.btnEmergency.setOnClickListener {


            openService(

                HealthServiceActivity.EMERGENCY

            )


        }









        // View all patients


        binding.btnViewAllPatients.setOnClickListener {


            startActivity(

                Intent(

                    this,

                    PatientListActivity::class.java

                )

            )


        }









        // View all appointments


        binding.btnViewAllAppointments.setOnClickListener {


            if(patientId != -1){


                startActivity(

                    Intent(

                        this,

                        PatientAppointmentActivity::class.java

                    ).apply {


                        putExtra(

                            "PATIENT_ID",

                            patientId

                        )


                    }

                )


            }
            else{


                startActivity(

                    Intent(

                        this,

                        AppointmentListActivity::class.java

                    )

                )


            }


        }


    }









    private fun openService(service:String){


        val intent = Intent(

            this,

            HealthServiceActivity::class.java

        )



        intent.putExtra(

            HealthServiceActivity.EXTRA_SERVICE,

            service

        )



        if(patientId != -1){


            intent.putExtra(

                "PATIENT_ID",

                patientId

            )


        }



        startActivity(intent)



    }









    override fun onResume(){


        super.onResume()


        loadDashboardData()


    }









    private fun loadDashboardData(){



        binding.txtPatientCount.text =

            dbHelper.getPatientCount().toString()





        binding.txtAppointmentCount.text =

            dbHelper.getAppointmentCount().toString()






        val patients =

            dbHelper.getRecentPatients()





        binding.txtPatientName1.text =

            patients.getOrNull(0)?.name ?: "-"





        binding.txtPatientPhone1.text =

            patients.getOrNull(0)?.phone ?: "-"





        binding.txtPatientName2.text =

            patients.getOrNull(1)?.name ?: "-"





        binding.txtPatientPhone2.text =

            patients.getOrNull(1)?.phone ?: "-"







        val appointments =

            dbHelper.getRecentAppointments()





        binding.txtAppDate1.text =

            appointments.getOrNull(0)?.appointmentDate ?: "-"





        binding.txtAppPatient1.text =

            appointments.getOrNull(0)?.patientName ?: "-"





        binding.txtAppDate2.text =

            appointments.getOrNull(1)?.appointmentDate ?: "-"

        binding.txtAppPatient2.text =

            appointments.getOrNull(1)?.patientName ?: "-"

    }
}