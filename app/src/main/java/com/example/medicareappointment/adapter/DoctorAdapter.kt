package com.example.medicareappointment.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.medicareappointment.R
import com.example.medicareappointment.model.Doctor

class DoctorAdapter(
    private val doctorList: ArrayList<Doctor>,
    private val onSelectClick: (Doctor) -> Unit
) : RecyclerView.Adapter<DoctorAdapter.DoctorViewHolder>() {


    class DoctorViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val name: TextView = view.findViewById(R.id.tvDoctorName)

        val specialization: TextView =
            view.findViewById(R.id.tvDoctorSpecialization)

        val phone: TextView =
            view.findViewById(R.id.tvDoctorPhone)

        val selectButton: Button =
            view.findViewById(R.id.btnSelectDoctor)

    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DoctorViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.item_doctor,
                parent,
                false
            )

        return DoctorViewHolder(view)
    }


    override fun onBindViewHolder(
        holder: DoctorViewHolder,
        position: Int
    ) {

        val doctor = doctorList[position]


        holder.name.text = doctor.name

        holder.specialization.text = doctor.specialization

        holder.phone.text = doctor.phone


        holder.selectButton.setOnClickListener {

            onSelectClick(doctor)

        }

    }


    override fun getItemCount(): Int {

        return doctorList.size

    }

}