package com.example.medicareappointment.activities


import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.medicareappointment.database.DatabaseHelper
import com.example.medicareappointment.databinding.ActivityAddAppointmentBinding
import java.util.Calendar
import java.util.Locale


class AddAppointmentActivity : AppCompatActivity() {


    private lateinit var binding: ActivityAddAppointmentBinding

    private lateinit var dbHelper: DatabaseHelper



    private var isEditMode = false

    private var appointmentId = -1



    private var patientId = -1

    private var patientName = ""

    private var patientPhone = ""



    private var selectedDoctorId = -1

    private var selectedDoctorName = ""



    private var doctorStartTime = ""

    private var doctorEndTime = ""

    private var doctorDuration = 30



    private var selectedTime = ""



    private val doctorList =
        ArrayList<com.example.medicareappointment.model.Doctor>()





    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)


        binding =
            ActivityAddAppointmentBinding.inflate(layoutInflater)


        setContentView(binding.root)



        dbHelper =
            DatabaseHelper(this)




        getIntentData()


        loadPatientDetails()


        loadDoctors()


        setupTimeSlotListener()





        binding.etAppointmentDate.apply {

            isFocusable = false

            isClickable = true

        }





        binding.etAppointmentDate.setOnClickListener {

            showDatePicker()

        }





        binding.btnSaveAppointment.setOnClickListener {


            saveOrUpdateAppointment()


        }


    }







    private fun getIntentData(){


        patientId =
            intent.getIntExtra(

                "PATIENT_ID",

                -1

            )



        selectedDoctorId =
            intent.getIntExtra(

                "DOCTOR_ID",

                -1

            )



        selectedDoctorName =
            intent.getStringExtra(

                "DOCTOR_NAME"

            ) ?: ""




        appointmentId =
            intent.getIntExtra(

                "appointment_id",

                -1

            )




        if(appointmentId != -1){


            isEditMode = true


            binding.btnSaveAppointment.text =

                "Update Appointment"


        }


    }







    private fun loadPatientDetails(){


        if(patientId == -1){

            return

        }



        val patient =

            dbHelper.getPatientById(

                patientId

            )



        if(patient != null){


            patientName =

                patient.name



            patientPhone =

                patient.phone




            binding.etPatientName.setText(

                patientName

            )



            binding.etPatientPhone.setText(

                patientPhone

            )


        }


    }
    private fun loadDoctors(){


        val doctors =

            dbHelper.getAllDoctors()



        doctorList.clear()


        doctorList.addAll(

            doctors

        )



        if(doctorList.isEmpty()){


            Toast.makeText(

                this,

                "No doctors available",

                Toast.LENGTH_SHORT

            ).show()


            return

        }





        val doctorNames =

            ArrayList<String>()



        doctorList.forEach {


            doctorNames.add(

                it.name

            )

        }





        val adapter =

            ArrayAdapter(

                this,

                android.R.layout.simple_spinner_dropdown_item,

                doctorNames

            )




        binding.spDoctor.adapter =

            adapter






        if(selectedDoctorName.isNotEmpty()){


            val position =

                doctorList.indexOfFirst {


                    it.name == selectedDoctorName


                }



            if(position >= 0){


                binding.spDoctor.setSelection(

                    position

                )


            }


        }






        binding.spDoctor.setOnItemSelectedListener(

            object :

                android.widget.AdapterView.OnItemSelectedListener {



                override fun onNothingSelected(

                    parent: android.widget.AdapterView<*>?

                ) {

                }





                override fun onItemSelected(

                    parent: android.widget.AdapterView<*>?,

                    view: android.view.View?,

                    position: Int,

                    id: Long

                ) {



                    if(position < doctorList.size){



                        val doctor =

                            doctorList[position]




                        selectedDoctorId =

                            doctor.id




                        selectedDoctorName =

                            doctor.name




                        doctorStartTime =

                            doctor.startTime




                        doctorEndTime =

                            doctor.endTime




                        doctorDuration =

                            doctor.appointmentDuration

                        generateTimeSlots()


                    }


                }


            }

        )


    }








    private fun setupTimeSlotListener(){



        binding.spTimeSlot.setOnItemSelectedListener(

            object :

                android.widget.AdapterView.OnItemSelectedListener {



                override fun onNothingSelected(

                    parent: android.widget.AdapterView<*>?

                ) {

                }





                override fun onItemSelected(

                    parent: android.widget.AdapterView<*>?,

                    view: android.view.View?,

                    position: Int,

                    id: Long

                ) {



                    selectedTime =

                        parent?.getItemAtPosition(position)

                            .toString()



                }


            }

        )


    }








    private fun showDatePicker(){



        val calendar =

            Calendar.getInstance()



        DatePickerDialog(

            this,

            { _, year, month, day ->



                val date =

                    String.format(

                        Locale.getDefault(),

                        "%02d/%02d/%04d",

                        day,

                        month + 1,

                        year

                    )




                binding.etAppointmentDate.setText(

                    date

                )



            },

            calendar.get(Calendar.YEAR),

            calendar.get(Calendar.MONTH),

            calendar.get(Calendar.DAY_OF_MONTH)


        ).show()



    }
    private fun generateTimeSlots(){


        val slots = ArrayList<String>()

        Toast.makeText(
            this,
            "Start=$doctorStartTime End=$doctorEndTime Duration=$doctorDuration",
            Toast.LENGTH_LONG
        ).show()

        if(

            doctorStartTime.isEmpty() ||

            doctorEndTime.isEmpty()

        ){

            return

        }




        var current =

            convertTimeToMinutes(

                doctorStartTime

            )




        val end =

            convertTimeToMinutes(

                doctorEndTime

            )





        while(current < end){



            val hour =

                current / 60



            val minute =

                current % 60





            val amPm =

                if(hour >= 12)

                    "PM"

                else

                    "AM"





            val hour12 =

                when{


                    hour == 0 -> 12


                    hour > 12 -> hour - 12


                    else -> hour


                }





            slots.add(

                String.format(

                    Locale.getDefault(),

                    "%02d:%02d %s",

                    hour12,

                    minute,

                    amPm

                )

            )





            current += doctorDuration


        }






        binding.spTimeSlot.adapter =

            ArrayAdapter(

                this,

                android.R.layout.simple_spinner_dropdown_item,

                slots

            )


    }








    private fun convertTimeToMinutes(

        time:String

    ):Int{


        try{


            val cleanTime =

                time.trim()



            // 24 hour format (10:00 / 21:00)

            if(!cleanTime.contains(" ")){


                val parts =

                    cleanTime.split(":")


                val hour =

                    parts[0].toInt()


                val minute =

                    parts[1].toInt()



                return (hour * 60) + minute

            }





            // 12 hour format (10:00 AM)

            val parts =

                cleanTime.split(" ")



            val hm =

                parts[0].split(":")



            var hour =

                hm[0].toInt()



            val minute =

                hm[1].toInt()



            val amPm =

                parts[1]



            if(
                amPm.equals(
                    "PM",
                    true
                )
                &&
                hour != 12
            ){

                hour += 12

            }



            if(
                amPm.equals(
                    "AM",
                    true
                )
                &&
                hour == 12
            ){

                hour = 0

            }



            return (hour * 60) + minute


        }
        catch(e:Exception){


            return 0


        }


    }





    private fun saveOrUpdateAppointment(){



        val date =

            binding.etAppointmentDate.text

                .toString()

                .trim()




        val time =

            selectedTime






        if(patientId == -1){



            Toast.makeText(

                this,

                "Patient login required",

                Toast.LENGTH_SHORT

            ).show()



            return


        }






        if(selectedDoctorId == -1){



            Toast.makeText(

                this,

                "Please select doctor",

                Toast.LENGTH_SHORT

            ).show()



            return


        }







        if(

            date.isEmpty() ||

            time.isEmpty()

        ){



            Toast.makeText(

                this,

                "Select date and time",

                Toast.LENGTH_SHORT

            ).show()



            return


        }








        if(

            !dbHelper.isAppointmentAvailable(

                selectedDoctorId,

                date,

                time

            )

        ){



            Toast.makeText(

                this,

                "This doctor already has an appointment at this time",

                Toast.LENGTH_LONG

            ).show()



            return


        }







        if(isEditMode){



            val result =

                dbHelper.updateAppointment(

                    appointmentId,

                    patientId,

                    selectedDoctorId,

                    selectedDoctorName,

                    date,

                    time

                )




            if(result > 0){



                Toast.makeText(

                    this,

                    "Appointment updated",

                    Toast.LENGTH_SHORT

                ).show()



                finish()


            }





        }
        else{



            val result =

                dbHelper.insertAppointment(

                    patientId,

                    selectedDoctorId,

                    selectedDoctorName,

                    date,

                    time

                )





            if(result > 0){



                Toast.makeText(

                    this,

                    "Appointment booked successfully",

                    Toast.LENGTH_SHORT

                ).show()



                finish()


            }



        }


    }

}
