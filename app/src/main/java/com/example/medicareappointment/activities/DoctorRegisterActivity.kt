package com.example.medicareappointment.activities


import android.app.TimePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.medicareappointment.R
import com.example.medicareappointment.database.DatabaseHelper
import java.util.Calendar


class DoctorRegisterActivity : AppCompatActivity() {


    private lateinit var databaseHelper: DatabaseHelper



    private lateinit var doctorName: EditText
    private lateinit var specialization: EditText
    private lateinit var phone: EditText
    private lateinit var email: EditText
    private lateinit var password: EditText


    private lateinit var spinnerQualification: Spinner
    private lateinit var registrationNumber: EditText
    private lateinit var experience: EditText

    private var appointmentDuration = 30
    private lateinit var spinnerWorkingDays: Spinner

    private lateinit var spinnerAppointmentDuration: Spinner

    private lateinit var startTime: EditText
    private lateinit var endTime: EditText


    private lateinit var btnUploadDocument: Button
    private lateinit var txtSelectedDocument: TextView

    private lateinit var btnUploadProfileImage: Button

    private lateinit var imgDoctorProfile: ImageView


    private lateinit var registerButton: Button

    private var selectedProfileImage = ""
    private var selectedDocument = ""



    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)


        setContentView(R.layout.activity_doctor_register)



        databaseHelper =
            DatabaseHelper(this)


        initializeViews()


        setupDropdowns()


        setupDurationSpinner()


        setupTimePicker()


        setupDocumentUpload()


        setupProfileImageUpload()


        registerButton.setOnClickListener {


            registerDoctor()


        }


    }

    private fun initializeViews() {


        doctorName =
            findViewById(R.id.etDoctorName)


        specialization =
            findViewById(R.id.etSpecialization)


        phone =
            findViewById(R.id.etDoctorPhone)


        email =
            findViewById(R.id.etDoctorEmail)


        password =
            findViewById(R.id.etDoctorPassword)



        spinnerQualification =
            findViewById(R.id.spinnerQualification)



        registrationNumber =
            findViewById(R.id.etRegistrationNumber)



        experience =
            findViewById(R.id.etExperience)



        spinnerWorkingDays =
            findViewById(R.id.spinnerWorkingDays)

        spinnerAppointmentDuration =
            findViewById(R.id.spAppointmentDuration)

        startTime =
            findViewById(R.id.etStartTime)



        endTime =
            findViewById(R.id.etEndTime)



        btnUploadDocument =
            findViewById(R.id.btnUploadDocument)



        txtSelectedDocument =
            findViewById(R.id.txtSelectedDocument)


        btnUploadProfileImage =

            findViewById(R.id.btnUploadProfileImage)



        imgDoctorProfile =

            findViewById(R.id.imgDoctorProfile)

        registerButton =
            findViewById(R.id.btnSubmitDoctor)


    }





    private fun setupDropdowns() {


        val qualifications = arrayOf(

            "MBBS",

            "MBBS, MD",

            "MBBS, MS",

            "MBBS, MRCP",

            "Other"

        )


        spinnerQualification.adapter =

            ArrayAdapter(

                this,

                android.R.layout.simple_spinner_dropdown_item,

                qualifications

            )





        val workingDays = arrayOf(

            "Monday - Friday",

            "Monday - Saturday",

            "Monday, Wednesday, Friday",

            "Weekend"

        )


        spinnerWorkingDays.adapter =

            ArrayAdapter(

                this,

                android.R.layout.simple_spinner_dropdown_item,

                workingDays

            )


    }

    private fun setupDurationSpinner(){


        val durations = arrayOf(

            "15",

            "30",

            "45",

            "60"

        )


        spinnerAppointmentDuration.adapter =

            ArrayAdapter(

                this,

                android.R.layout.simple_spinner_dropdown_item,

                durations

            )



        spinnerAppointmentDuration.setOnItemSelectedListener(

            object :
                android.widget.AdapterView.OnItemSelectedListener {


                override fun onNothingSelected(
                    parent: android.widget.AdapterView<*>?
                ) {

                }



                override fun onItemSelected(

                    parent: android.widget.AdapterView<*>,

                    view: android.view.View?,

                    position: Int,

                    id: Long

                ){


                    appointmentDuration =

                        durations[position].toInt()


                }


            }

        )


    }



    private fun setupTimePicker() {


        startTime.setOnClickListener {


            showTimePicker(startTime)


        }



        endTime.setOnClickListener {


            showTimePicker(endTime)


        }


    }





    private fun showTimePicker(editText: EditText) {


        val calendar =
            Calendar.getInstance()



        TimePickerDialog(

            this,

            { _, hour, minute ->


                editText.setText(

                    String.format(

                        "%02d:%02d",

                        hour,

                        minute

                    )

                )


            },


            calendar.get(Calendar.HOUR_OF_DAY),

            calendar.get(Calendar.MINUTE),

            true


        ).show()


    }





    private fun setupDocumentUpload() {


        btnUploadDocument.setOnClickListener {


            val intent = Intent(

                Intent.ACTION_OPEN_DOCUMENT

            )


            intent.type = "*/*"


            intent.putExtra(

                Intent.EXTRA_MIME_TYPES,

                arrayOf(

                    "application/pdf",

                    "image/jpeg",

                    "image/png"

                )

            )


            intent.addCategory(

                Intent.CATEGORY_OPENABLE

            )


            intent.addFlags(

                Intent.FLAG_GRANT_READ_URI_PERMISSION

            )


            startActivityForResult(

                intent,

                100

            )


        }


    }
    private fun setupProfileImageUpload() {


        btnUploadProfileImage.setOnClickListener {


            val intent = Intent(

                Intent.ACTION_OPEN_DOCUMENT

            )


            intent.type = "image/*"


            intent.addCategory(

                Intent.CATEGORY_OPENABLE

            )


            startActivityForResult(

                intent,

                200

            )


        }


    }

    override fun onActivityResult(

        requestCode: Int,

        resultCode: Int,

        data: Intent?

    ) {


        super.onActivityResult(

            requestCode,

            resultCode,

            data

        )


        if(resultCode == RESULT_OK && data != null){


            val uri = data.data

            if(uri != null){


                try {


                    contentResolver.takePersistableUriPermission(

                        uri,

                        Intent.FLAG_GRANT_READ_URI_PERMISSION

                    )


                }catch(e:Exception){


                    e.printStackTrace()


                }




                // Qualification Document

                if(requestCode == 100){


                    selectedDocument =

                        uri.toString()



                    txtSelectedDocument.text =

                        getFileName(uri)



                }





                // Doctor Profile Image

                if(requestCode == 200){


                    selectedProfileImage =

                        uri.toString()



                    try {


                        val bitmap =

                            android.graphics.BitmapFactory.decodeStream(

                                contentResolver.openInputStream(uri)

                            )



                        imgDoctorProfile.setImageBitmap(bitmap)



                    } catch(e: Exception){


                        e.printStackTrace()



                        Toast.makeText(

                            this,

                            "Image loading failed",

                            Toast.LENGTH_SHORT

                        ).show()


                    }



                }


            }


        }


    }


    private fun getFileName(uri: Uri): String {


        var fileName = "Document"



        val cursor = contentResolver.query(

            uri,

            null,

            null,

            null,

            null

        )



        cursor?.use {


            if (it.moveToFirst()) {


                val index =

                    it.getColumnIndex(

                        OpenableColumns.DISPLAY_NAME

                    )



                if (index >= 0) {


                    fileName =

                        it.getString(index)


                }


            }


        }



        return fileName


    }





    private fun registerDoctor() {


        val name =

            doctorName.text.toString().trim()



        val spec =

            specialization.text.toString().trim()



        val phoneNumber =

            phone.text.toString().trim()



        val emailAddress =

            email.text.toString().trim()



        val passwordValue =

            password.text.toString().trim()



        val qualificationValue =

            spinnerQualification.selectedItem.toString()



        val registrationValue =

            registrationNumber.text.toString().trim()



        val experienceValue =

            experience.text.toString().trim()



        val daysValue =

            spinnerWorkingDays.selectedItem.toString()



        val startValue =

            startTime.text.toString().trim()



        val endValue =

            endTime.text.toString().trim()





        if (

            name.isEmpty() ||

            spec.isEmpty() ||

            phoneNumber.isEmpty() ||

            emailAddress.isEmpty() ||

            passwordValue.isEmpty() ||

            selectedDocument.isEmpty() ||

            selectedProfileImage.isEmpty()
        ) {


            Toast.makeText(

                this,

                "Please fill required fields and upload document",

                Toast.LENGTH_SHORT

            ).show()


            return


        }

        val result =
            databaseHelper.insertDoctorApplication(

                name,

                spec,

                phoneNumber,

                emailAddress,

                passwordValue,

                qualificationValue,

                registrationValue,

                experienceValue,

                selectedDocument,

                selectedProfileImage,

                daysValue,

                startValue,

                endValue,

                appointmentDuration

            )




        if (result > 0) {


            Toast.makeText(

                this,

                "Application submitted. Waiting for admin approval",

                Toast.LENGTH_LONG

            ).show()



            clearFields()



        } else {


            Toast.makeText(

                this,

                "Registration Failed",

                Toast.LENGTH_SHORT

            ).show()

        }

    }

    private fun clearFields() {


        doctorName.text.clear()

        specialization.text.clear()

        phone.text.clear()

        email.text.clear()

        password.text.clear()

        registrationNumber.text.clear()

        experience.text.clear()

        startTime.text.clear()

        endTime.text.clear()

        spinnerQualification.setSelection(0)

        spinnerWorkingDays.setSelection(0)

        txtSelectedDocument.text =

            "No document selected"

        selectedDocument = ""

        selectedProfileImage = ""
    }

}