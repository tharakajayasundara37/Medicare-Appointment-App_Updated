package com.example.medicareappointment.activities

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.medicareappointment.adapter.PatientAdapter
import com.example.medicareappointment.database.DatabaseHelper
import com.example.medicareappointment.databinding.ActivityPatientListBinding
import com.example.medicareappointment.model.Patient

class PatientListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPatientListBinding
    private lateinit var dbHelper: DatabaseHelper

    private var allPatients = ArrayList<Patient>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPatientListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = DatabaseHelper(this)
        binding.recyclerPatients.layoutManager = LinearLayoutManager(this)

        binding.btnAddPatient.setOnClickListener {
            startActivity(Intent(this, AddPatientActivity::class.java))
        }

        binding.etSearchPatient.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filterPatients(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    override fun onResume() {
        super.onResume()
        loadPatients()
    }

    private fun loadPatients() {
        allPatients = dbHelper.getAllPatients()
        setAdapter(allPatients)
    }

    private fun filterPatients(query: String) {
        val filteredList = ArrayList<Patient>()

        for (patient in allPatients) {
            if (
                patient.name.contains(query, ignoreCase = true) ||
                patient.phone.contains(query, ignoreCase = true)
            ) {
                filteredList.add(patient)
            }
        }

        setAdapter(filteredList)
    }

    private fun setAdapter(patientList: ArrayList<Patient>) {
        val adapter = PatientAdapter(
            patientList,
            onDeleteClick = { patient ->
                showDeleteDialog(patient)
            },
            onUpdateClick = { patient ->
                val intent = Intent(this, AddPatientActivity::class.java)
                intent.putExtra("patient_id", patient.id)
                intent.putExtra("patient_name", patient.name)
                intent.putExtra("patient_age", patient.age)
                intent.putExtra("patient_phone", patient.phone)
                intent.putExtra("patient_address", patient.address)
                startActivity(intent)
            }
        )

        binding.recyclerPatients.adapter = adapter
    }

    private fun showDeleteDialog(patient: Patient) {
        AlertDialog.Builder(this)
            .setTitle("Delete Patient")
            .setMessage("Are you sure you want to delete ${patient.name}?")
            .setPositiveButton("Delete") { _, _ ->
                dbHelper.deletePatient(patient.id)
                Toast.makeText(this, "Patient Deleted", Toast.LENGTH_SHORT).show()
                loadPatients()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}