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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHealthServiceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnBack.setOnClickListener { finish() }
        render(intent.getStringExtra(EXTRA_SERVICE) ?: DOCTORS)
    }

    private fun render(service: String) {
        val content = when (service) {
            LABS -> ServiceContent("LAB & HOME CARE", "Book laboratory tests", "Request routine tests or a trained professional for home sample collection.", "Popular tests", "Full Blood Count  •  Fasting Blood Sugar\nLipid Profile  •  Liver Function\nHome collection available in selected areas", "1. Select a test or upload a prescription\n2. Add patient and collection details\n3. A partner lab confirms the request", "Start lab request")
            MEDICINE -> ServiceContent("PHARMACY", "Order prescription medicine", "Upload a valid prescription and request delivery from a partner pharmacy near you.", "Safe prescription fulfilment", "Prescription review by a pharmacy\nDelivery address confirmation\nOrder updates and secure hand-off", "1. Upload your prescription\n2. Confirm patient and delivery address\n3. Pharmacy confirms availability and total", "Upload prescription")
            TELEHEALTH -> ServiceContent("VIDEO CARE", "Consult from anywhere", "Connect remotely with a doctor for suitable non-emergency health concerns.", "Remote care options", "General medicine  •  Paediatrics\nDermatology  •  Mental wellbeing\nAudio or video consultation", "1. Choose a speciality\n2. Select an available time\n3. Join from your appointment screen", "Book video consultation")
            PACKAGES -> ServiceContent("PREVENTIVE CARE", "Health check-up plans", "Explore preventive screening packages designed for different life stages.", "Recommended packages", "Essential wellness screening\nHeart health profile\nDiabetes screening\nSenior wellness assessment", "1. Compare included tests\n2. Choose a hospital and date\n3. Receive preparation instructions", "Explore packages")
            EMERGENCY -> ServiceContent("EMERGENCY", "Emergency assistance", "For a serious or life-threatening emergency, contact an emergency service immediately.", "1990 Suwa Seriya", "Sri Lanka's pre-hospital emergency ambulance service. Do not use regular appointment booking for emergencies.", "1. Move to a safe location\n2. Call 1990 and describe the emergency\n3. Follow the operator's instructions", "Call 1990 now")
            else -> ServiceContent("DOCTOR CHANNELLING", "Find the right doctor", "Search by doctor, speciality or hospital and create an appointment in a few steps.", "Available specialities", "General Physician  •  Cardiologist\nPaediatrician  •  Dermatologist\nENT Surgeon  •  Gynaecologist", "1. Search or select a speciality\n2. Choose a doctor and available time\n3. Add patient details and confirm", "Create appointment")
        }
        binding.txtEyebrow.text = content.eyebrow
        binding.txtTitle.text = content.title
        binding.txtDescription.text = content.description
        binding.txtCardTitle.text = content.cardTitle
        binding.txtCardBody.text = content.cardBody
        binding.txtSteps.text = content.steps
        binding.btnPrimaryAction.text = content.action
        binding.etSearch.visibility = if (service == DOCTORS) View.VISIBLE else View.GONE
        binding.txtDisclaimer.visibility = if (service == EMERGENCY) View.GONE else View.VISIBLE
        binding.btnPrimaryAction.setOnClickListener {
            when (service) {
                DOCTORS, TELEHEALTH -> startActivity(Intent(this, AddAppointmentActivity::class.java))
                EMERGENCY -> startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:1990")))
                else -> Toast.makeText(this, "Request flow is ready for partner API integration", Toast.LENGTH_LONG).show()
            }
        }
    }

    data class ServiceContent(val eyebrow: String, val title: String, val description: String, val cardTitle: String, val cardBody: String, val steps: String, val action: String)

    companion object {
        const val EXTRA_SERVICE = "service"
        const val DOCTORS = "doctors"; const val LABS = "labs"; const val MEDICINE = "medicine"
        const val TELEHEALTH = "telehealth"; const val PACKAGES = "packages"; const val EMERGENCY = "emergency"
    }
}
