package com.example.medicareappointment.activities


import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.medicareappointment.database.DatabaseHelper
import com.example.medicareappointment.databinding.ActivityDoctorProfileBinding


class DoctorProfileActivity : AppCompatActivity() {


    private lateinit var binding: ActivityDoctorProfileBinding

    private lateinit var dbHelper: DatabaseHelper


    private var doctorId = -1



    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)


        binding =

            ActivityDoctorProfileBinding.inflate(

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





        loadProfile()



    }









    private fun loadProfile(){



        val doctor =

            dbHelper.getDoctorById(

                doctorId

            )





        if(doctor != null){





            // Profile Image

            if(doctor.profileImage.isNotEmpty()){


                try {



                    val uri =

                        Uri.parse(

                            doctor.profileImage

                        )



                    val inputStream =

                        contentResolver.openInputStream(uri)



                    val bitmap =

                        BitmapFactory.decodeStream(

                            inputStream

                        )



                    binding.imgDoctorProfile.setImageBitmap(

                        bitmap

                    )



                    inputStream?.close()



                } catch(e: Exception){



                    e.printStackTrace()



                }



            }








            binding.txtProfileName.text =

                doctor.name






            binding.txtProfileSpecialization.text =

                doctor.specialization






            binding.txtProfilePhone.text =

                "Phone : ${doctor.phone}"






            binding.txtProfileEmail.text =

                "Email : ${doctor.email}"






            binding.txtProfileQualification.text =

                "Qualification : ${doctor.qualification}"






            binding.txtProfileRegistration.text =

                "Registration No : ${doctor.registrationNumber}"






            binding.txtProfileExperience.text =

                "Experience : ${doctor.experience} Years"






            binding.txtProfileWorking.text =

                "${doctor.workingDays} | ${doctor.startTime} - ${doctor.endTime}"






            binding.txtProfileDuration.text =

                "Appointment Duration : ${doctor.appointmentDuration} Minutes}"






            binding.txtProfileStatus.text =

                "Status : ${doctor.status}"



        }



    }



}