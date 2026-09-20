package com.example.medicareappointment.activities


import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.medicareappointment.database.DatabaseHelper
import com.example.medicareappointment.databinding.ActivityDoctorDashboardBinding



class DoctorDashboardActivity : AppCompatActivity() {



    private lateinit var binding: ActivityDoctorDashboardBinding


    private lateinit var dbHelper: DatabaseHelper



    private var doctorId = -1







    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)



        binding =

            ActivityDoctorDashboardBinding.inflate(

                layoutInflater

            )


        setContentView(binding.root)






        dbHelper =

            DatabaseHelper(this)






        doctorId =

            intent.getIntExtra(

                "DOCTOR_ID",

                -1

            )





        loadDoctorData()


        loadAppointmentData()







        // ================= PROFILE =================



        binding.btnDoctorProfile.setOnClickListener {



            startActivity(

                Intent(

                    this,

                    DoctorProfileActivity::class.java

                ).apply {



                    putExtra(

                        "DOCTOR_ID",

                        doctorId

                    )



                }

            )


        }







        // ================= APPOINTMENTS =================



        binding.btnDoctorAppointments.setOnClickListener {



            startActivity(

                Intent(

                    this,

                    DoctorAppointmentActivity::class.java

                ).apply {



                    putExtra(

                        "DOCTOR_ID",

                        doctorId

                    )


                }

            )


        }








        // ================= PROFILE IMAGE CLICK =================


        binding.imgDoctorDashboardProfile.setOnClickListener {



            startActivity(

                Intent(

                    this,

                    DoctorProfileActivity::class.java

                ).apply {



                    putExtra(

                        "DOCTOR_ID",

                        doctorId

                    )


                }

            )


        }








        // ================= LOGOUT =================



        binding.btnDoctorLogout.setOnClickListener {



            val intent = Intent(

                this,

                LoginActivity::class.java

            )



            intent.flags =

                Intent.FLAG_ACTIVITY_NEW_TASK or

                        Intent.FLAG_ACTIVITY_CLEAR_TASK



            startActivity(intent)



        }



    }









    private fun loadDoctorData(){



        if(doctorId == -1){

            return

        }






        val doctor =

            dbHelper.getDoctorById(

                doctorId

            )







        if(doctor != null){





            binding.txtDoctorName.text =

                doctor.name





            binding.txtDoctorSpecialization.text =

                doctor.specialization





            binding.txtWorkingTime.text =


                "${doctor.workingDays} | ${doctor.startTime} - ${doctor.endTime}"







            binding.txtDuration.text =


                "Appointment Duration : ${doctor.appointmentDuration} Minutes"







            // Load Profile Image


            if(

                doctor.profileImage.isNotEmpty()

            ){



                try {



                    val uri =

                        Uri.parse(

                            doctor.profileImage

                        )




                    val inputStream =

                        contentResolver.openInputStream(

                            uri

                        )




                    val bitmap =

                        BitmapFactory.decodeStream(

                            inputStream

                        )




                    binding.imgDoctorDashboardProfile.setImageBitmap(

                        bitmap

                    )




                    inputStream?.close()



                }catch(e:Exception){



                    e.printStackTrace()



                }



            }



        }



    }









    private fun loadAppointmentData(){



        if(doctorId == -1){

            return

        }





        binding.txtTodayCount.text =


            dbHelper.getDoctorAppointmentCount(

                doctorId

            ).toString()






        binding.txtUpcomingCount.text =


            dbHelper.getDoctorUpcomingAppointmentCount(

                doctorId

            ).toString()





    }



}