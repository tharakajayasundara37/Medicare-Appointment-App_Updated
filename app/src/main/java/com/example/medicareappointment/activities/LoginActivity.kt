package com.example.medicareappointment.activities


import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.medicareappointment.database.DatabaseHelper
import com.example.medicareappointment.databinding.ActivityLoginBinding


class LoginActivity : AppCompatActivity() {


    private lateinit var binding: ActivityLoginBinding

    private lateinit var databaseHelper: DatabaseHelper



    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)


        binding =
            ActivityLoginBinding.inflate(layoutInflater)


        setContentView(binding.root)



        databaseHelper =
            DatabaseHelper(this)



        setupRoleSpinner()



        binding.btnLogin.setOnClickListener {


            checkLogin()


        }



        binding.btnRegister.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    RegisterSelectionActivity::class.java
                )
            )
        }

    }


    private fun setupRoleSpinner() {


        val roles = arrayOf(

            "Admin",

            "Doctor",

            "Patient"

        )



        val adapter = ArrayAdapter(

            this,

            android.R.layout.simple_spinner_dropdown_item,

            roles

        )



        binding.spinnerRole.adapter = adapter


    }







    private fun checkLogin() {



        val email =

            binding.etEmail.text

                .toString()

                .trim()



        val password =

            binding.etPassword.text

                .toString()

                .trim()



        val role =

            binding.spinnerRole.selectedItem

                .toString()





        if(

            email.isEmpty() ||

            password.isEmpty()

        ){


            Toast.makeText(

                this,

                "Please enter email and password",

                Toast.LENGTH_SHORT

            ).show()


            return

        }





        when(role){





            // ================= ADMIN =================


            "Admin" -> {


                if(

                    email == "admin@medicare.com" &&

                    password == "123456"

                ){


                    Toast.makeText(

                        this,

                        "Admin Login Successful",

                        Toast.LENGTH_SHORT

                    ).show()



                    startActivity(

                        Intent(

                            this,

                            AdminDashboardActivity::class.java

                        )

                    )



                    finish()



                }else{


                    Toast.makeText(

                        this,

                        "Invalid Admin Login",

                        Toast.LENGTH_SHORT

                    ).show()


                }


            }







            // ================= DOCTOR =================


            "Doctor" -> {



                val doctor =

                    databaseHelper.doctorLogin(

                        email,

                        password

                    )




                if(doctor != null){



                    if(

                        doctor.status.equals(

                            "Approved",

                            ignoreCase = true

                        )

                    ){



                        Toast.makeText(

                            this,

                            "Doctor Login Successful",

                            Toast.LENGTH_SHORT

                        ).show()




                        startActivity(

                            Intent(

                                this,

                                DoctorDashboardActivity::class.java

                            ).apply {


                                putExtra(

                                    "DOCTOR_ID",

                                    doctor.id

                                )


                            }

                        )



                        finish()



                    }else{



                        Toast.makeText(

                            this,

                            "Waiting for admin approval",

                            Toast.LENGTH_LONG

                        ).show()



                    }




                }else{


                    Toast.makeText(

                        this,

                        "Invalid Doctor Login",

                        Toast.LENGTH_SHORT

                    ).show()


                }


            }







            // ================= PATIENT =================


            "Patient" -> {



                val patient =

                    databaseHelper.patientLogin(

                        email,

                        password

                    )




                if(patient != null){



                    Toast.makeText(

                        this,

                        "Patient Login Successful",

                        Toast.LENGTH_SHORT

                    ).show()





                    startActivity(

                        Intent(

                            this,

                            PatientDashboardActivity::class.java

                        ).apply {


                            putExtra(

                                "PATIENT_ID",

                                patient.id

                            )


                        }

                    )



                    finish()



                }else{


                    Toast.makeText(

                        this,

                        "Invalid Patient Login",

                        Toast.LENGTH_SHORT

                    ).show()



                }


            }



        }


    }



}