package com.example.medicareappointment.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.medicareappointment.R
import com.example.medicareappointment.model.Appointment

class AdminRecentAppointmentAdapter(
    private val appointmentList: ArrayList<Appointment>
) : RecyclerView.Adapter<AdminRecentAppointmentAdapter.ViewHolder>() {


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        val txtPatientName: TextView =
            itemView.findViewById(R.id.txtRecentPatientName)


        val txtDoctorName: TextView =
            itemView.findViewById(R.id.txtRecentDoctorName)


        val txtDate: TextView =
            itemView.findViewById(R.id.txtRecentDate)


        val txtTime: TextView =
            itemView.findViewById(R.id.txtRecentTime)


        val txtStatus: TextView =
            itemView.findViewById(R.id.txtRecentStatus)

    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {


        val view = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.item_recent_appointment,
                parent,
                false
            )


        return ViewHolder(view)

    }



    override fun onBindViewHolder(
        holder: ViewHolder,
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



        when(appointment.appointmentStatus) {


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