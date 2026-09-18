package com.example.medicareappointment.activities


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.medicareappointment.databinding.ActivityHealthServiceBinding



class HealthServiceActivity : AppCompatActivity() {


    private lateinit var binding: ActivityHealthServiceBinding


    private var patientId = -1





    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)



        binding =
            ActivityHealthServiceBinding.inflate(layoutInflater)


        setContentView(binding.root)



        patientId =
            intent.getIntExtra(

                "PATIENT_ID",

                -1

            )



        binding.btnBack.setOnClickListener {

            finish()

        }



        render(

            intent.getStringExtra(EXTRA_SERVICE)
                ?: DOCTORS

        )


    }








    private fun render(service:String){


        val content = when(service){


            LABS -> ServiceContent(

                "LAB & HOME CARE",

                "Book laboratory tests",

                "Request routine tests or a trained professional for home sample collection.",

                "Popular tests",

                "Full Blood Count • Fasting Blood Sugar\nLipid Profile • Liver Function\nHome collection available in selected areas",

                "1. Select a test\n2. Add patient details\n3. Confirm request",

                "Start lab request"

            )





            MEDICINE -> ServiceContent(

                "PHARMACY",

                "Order prescription medicine",

                "Upload a valid prescription and request delivery.",

                "Safe prescription fulfilment",

                "Prescription review\nDelivery confirmation\nOrder updates",

                "1. Upload prescription\n2. Confirm address\n3. Pharmacy confirms",

                "Upload prescription"

            )






            TELEHEALTH -> ServiceContent(

                "VIDEO CARE",

                "Consult from anywhere",

                "Connect remotely with a doctor.",

                "Remote care options",

                "General medicine • Paediatrics\nDermatology • Mental wellbeing\nAudio or video consultation",

                "1. Choose speciality\n2. Select time\n3. Join consultation",

                "Book video consultation"

            )






            PACKAGES -> ServiceContent(

                "PREVENTIVE CARE",

                "Health check-up plans",

                "Explore preventive screening packages.",

                "Recommended packages",

                "Wellness screening\nHeart health\nDiabetes screening",

                "1. Compare plans\n2. Select package\n3. Confirm",

                "Explore packages"

            )






            EMERGENCY -> ServiceContent(

                "EMERGENCY",

                "Emergency assistance",

                "For serious emergencies contact emergency service.",

                "1990 Suwa Seriya",

                "Sri Lanka ambulance emergency service",

                "1. Stay safe\n2. Call 1990\n3. Follow instructions",

                "Call 1990 now"

            )






            else -> ServiceContent(

                "DOCTOR CHANNELLING",

                "Find the right doctor",

                "Search doctors and create appointments.",

                "Available specialities",

                "General Physician\nCardiologist\nPaediatrician",

                "1. Search doctor\n2. Select time\n3. Confirm",

                "Create appointment"

            )


        }






        binding.txtEyebrow.text =
            content.eyebrow


        binding.txtTitle.text =
            content.title


        binding.txtDescription.text =
            content.description


        binding.txtCardTitle.text =
            content.cardTitle


        binding.txtCardBody.text =
            content.cardBody


        binding.txtSteps.text =
            content.steps


        binding.btnPrimaryAction.text =
            content.action





        binding.etSearch.visibility =
            if(service == DOCTORS)
                View.VISIBLE
            else
                View.GONE






        binding.btnPrimaryAction.setOnClickListener {



            when(service){



                DOCTORS, TELEHEALTH -> {


                    if(patientId == -1){


                        Toast.makeText(

                            this,

                            "Please login as patient first",

                            Toast.LENGTH_SHORT

                        ).show()


                        return@setOnClickListener

                    }





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






                EMERGENCY -> {


                    startActivity(

                        Intent(

                            Intent.ACTION_DIAL,

                            Uri.parse("tel:1990")

                        )

                    )

                }






                else -> {


                    if(patientId == -1){


                        Toast.makeText(

                            this,

                            "Please login as patient first",

                            Toast.LENGTH_SHORT

                        ).show()


                    }
                    else{


                        Toast.makeText(

                            this,

                            "Feature ready for patient account",

                            Toast.LENGTH_SHORT

                        ).show()


                    }


                }


            }


        }


    }







    data class ServiceContent(

        val eyebrow:String,

        val title:String,

        val description:String,

        val cardTitle:String,

        val cardBody:String,

        val steps:String,

        val action:String

    )





    companion object {


        const val EXTRA_SERVICE = "service"


        const val DOCTORS = "doctors"

        const val LABS = "labs"

        const val MEDICINE = "medicine"

        const val TELEHEALTH = "telehealth"

        const val PACKAGES = "packages"

        const val EMERGENCY = "emergency"


    }


}