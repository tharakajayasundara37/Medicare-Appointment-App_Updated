package com.example.medicareappointment.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.medicareappointment.R
import com.example.medicareappointment.model.Appointment

class AppointmentAdapter(
    private val appointmentList: ArrayList<Appointment>,
    private val onDeleteClick: (Appointment) -> Unit,
    private val onUpdateClick: (Appointment) -> Unit
) : RecyclerView.Adapter<AppointmentAdapter.AppointmentViewHolder>() {

    class AppointmentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val txtPatientName: TextView = itemView.findViewById(R.id.txtPatientName)
        val txtDoctorName: TextView = itemView.findViewById(R.id.txtDoctorName)
        val txtDate: TextView = itemView.findViewById(R.id.txtDate)
        val txtTime: TextView = itemView.findViewById(R.id.txtTime)

        val btnUpdate: Button = itemView.findViewById(R.id.btnUpdateAppointment)
        val btnDelete: Button = itemView.findViewById(R.id.btnCancelAppointment)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppointmentViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_appointment, parent, false)

        return AppointmentViewHolder(view)
    }

    override fun onBindViewHolder(holder: AppointmentViewHolder, position: Int) {

        val appointment = appointmentList[position]

        holder.txtPatientName.text = appointment.patientName
        holder.txtDoctorName.text = "Doctor : ${appointment.doctorName}"
        holder.txtDate.text = "Date : ${appointment.appointmentDate}"
        holder.txtTime.text = "Time : ${appointment.appointmentTime}"

        holder.btnUpdate.setOnClickListener {
            onUpdateClick(appointment)
        }

        holder.btnDelete.setOnClickListener {
            onDeleteClick(appointment)
        }
    }

    override fun getItemCount(): Int {
        return appointmentList.size
    }
}