package com.example.medicareappointment.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.medicareappointment.R
import com.example.medicareappointment.model.Patient

class PatientAdapter(
    private val patientList: ArrayList<Patient>,
    private val onDeleteClick: (Patient) -> Unit,
    private val onUpdateClick: (Patient) -> Unit
) : RecyclerView.Adapter<PatientAdapter.PatientViewHolder>() {

    class PatientViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtName: TextView = itemView.findViewById(R.id.txtName)
        val txtPhone: TextView = itemView.findViewById(R.id.txtPhone)
        val txtAge: TextView = itemView.findViewById(R.id.txtAge)
        val txtAddress: TextView = itemView.findViewById(R.id.txtAddress)
        val btnUpdate: Button = itemView.findViewById(R.id.btnUpdate)
        val btnDelete: Button = itemView.findViewById(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PatientViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_patient, parent, false)
        return PatientViewHolder(view)
    }

    override fun onBindViewHolder(holder: PatientViewHolder, position: Int) {
        val patient = patientList[position]

        holder.txtName.text = patient.name
        holder.txtPhone.text = "Phone: ${patient.phone}"
        holder.txtAge.text = "Age: ${patient.age}"
        holder.txtAddress.text = "Address: ${patient.address}"

        holder.btnDelete.setOnClickListener {
            onDeleteClick(patient)
        }

        holder.btnUpdate.setOnClickListener {
            onUpdateClick(patient)
        }
    }

    override fun getItemCount(): Int {
        return patientList.size
    }
}