package com.example.medicareappointment.activities


import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.medicareappointment.adapter.DoctorAppointmentAdapter
import com.example.medicareappointment.database.DatabaseHelper
import com.example.medicareappointment.databinding.ActivityDoctorAppointmentBinding



class DoctorAppointmentActivity : AppCompatActivity() {



    private lateinit var binding: ActivityDoctorAppointmentBinding


    private lateinit var dbHelper: DatabaseHelper


    private lateinit var adapter: DoctorAppointmentAdapter



    private var doctorId = -1






    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)



        binding =

            ActivityDoctorAppointmentBinding.inflate(

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







        if(doctorId == -1){


            Toast.makeText(

                this,

                "Doctor ID not found",

                Toast.LENGTH_SHORT

            ).show()



            finish()


            return


        }







        setupRecyclerView()


        loadAppointments()



    }









    private fun setupRecyclerView(){



        binding.recyclerDoctorAppointments.layoutManager =

            LinearLayoutManager(this)



    }









    private fun loadAppointments(){



        val appointments =

            dbHelper.getDoctorAppointments(

                doctorId

            )






        adapter =

            DoctorAppointmentAdapter(

                appointments

            ){ id, status ->



                updateAppointmentStatus(

                    id,

                    status

                )



            }





        binding.recyclerDoctorAppointments.adapter =

            adapter



    }









    private fun updateAppointmentStatus(

        appointmentId: Int,

        status: String

    ){



        val result =

            dbHelper.updateAppointmentStatus(

                appointmentId,

                status

            )






        if(result){



            Toast.makeText(

                this,

                "Appointment $status",

                Toast.LENGTH_SHORT

            ).show()



            loadAppointments()



        }else{



            Toast.makeText(

                this,

                "Status update failed",

                Toast.LENGTH_SHORT

            ).show()



        }



    }








    override fun onResume(){



        super.onResume()



        if(::adapter.isInitialized){



            loadAppointments()



        }



    }



}