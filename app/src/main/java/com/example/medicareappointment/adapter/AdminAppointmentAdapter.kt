package com.example.medicareappointment.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.medicareappointment.R
import com.example.medicareappointment.model.Appointment

class AdminAppointmentAdapter(
    private val appointmentList: ArrayList<Appointment>,
    private val onStatusChange: (Appointment, String) -> Unit
) : RecyclerView.Adapter<AdminAppointmentAdapter.AdminAppointmentViewHolder>() {


    class AdminAppointmentViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        val txtPatientName: TextView =
            itemView.findViewById(R.id.txtAdminPatientName)

        val txtDoctorName: TextView =
            itemView.findViewById(R.id.txtAdminDoctorName)

        val txtDate: TextView =
            itemView.findViewById(R.id.txtAdminDate)

        val txtTime: TextView =
            itemView.findViewById(R.id.txtAdminTime)

        val txtStatus: TextView =
            itemView.findViewById(R.id.txtAdminStatus)


        val btnConfirm: Button =
            itemView.findViewById(R.id.btnConfirmAppointment)

        val btnComplete: Button =
            itemView.findViewById(R.id.btnCompleteAppointment)

        val btnCancel: Button =
            itemView.findViewById(R.id.btnCancelAppointment)
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdminAppointmentViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.item_admin_appointment,
                parent,
                false
            )

        return AdminAppointmentViewHolder(view)
    }


    override fun onBindViewHolder(
        holder: AdminAppointmentViewHolder,
        position: Int
    ) {

        val appointment = appointmentList[position]


        holder.txtPatientName.text =
            appointment.patientName


        holder.txtDoctorName.text =
            "Doctor : ${appointment.doctorName}"


        holder.txtDate.text =
            "Date : ${appointment.appointmentDate}"


        holder.txtTime.text =
            "Time : ${appointment.appointmentTime}"


        holder.txtStatus.text =
            appointment.appointmentStatus


        updateStatusBackground(
            holder,
            appointment.appointmentStatus
        )


        holder.btnConfirm.setOnClickListener {

            onStatusChange(
                appointment,
                "Confirmed"
            )

        }


        holder.btnComplete.setOnClickListener {

            onStatusChange(
                appointment,
                "Completed"
            )

        }


        holder.btnCancel.setOnClickListener {

            onStatusChange(
                appointment,
                "Cancelled"
            )

        }

    }


    private fun updateStatusBackground(
        holder: AdminAppointmentViewHolder,
        status: String
    ) {

        when(status) {

            "Pending" -> {

                holder.txtStatus.setBackgroundResource(
                    R.drawable.bg_status_pending
                )

            }


            "Confirmed" -> {

                holder.txtStatus.setBackgroundResource(
                    R.drawable.bg_status_confirmed
                )

            }


            "Completed" -> {

                holder.txtStatus.setBackgroundResource(
                    R.drawable.bg_status_completed
                )

            }


            "Cancelled" -> {

                holder.txtStatus.setBackgroundResource(
                    R.drawable.bg_status_cancelled
                )

            }

        }

    }


    override fun getItemCount(): Int {

        return appointmentList.size

    }


    fun updateList(
        newList: ArrayList<Appointment>
    ) {

        appointmentList.clear()

        appointmentList.addAll(newList)

        notifyDataSetChanged()

    }
}