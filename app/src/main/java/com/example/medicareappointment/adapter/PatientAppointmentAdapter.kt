package com.example.medicareappointment.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.medicareappointment.databinding.ItemPatientAppointmentBinding
import com.example.medicareappointment.model.Appointment



class PatientAppointmentAdapter(

    private var appointmentList: ArrayList<Appointment>

) : RecyclerView.Adapter<PatientAppointmentAdapter.ViewHolder>() {



    class ViewHolder(

        val binding: ItemPatientAppointmentBinding

    ) : RecyclerView.ViewHolder(binding.root)




    override fun onCreateViewHolder(

        parent: ViewGroup,

        viewType: Int

    ): ViewHolder {


        val binding =
            ItemPatientAppointmentBinding.inflate(

                LayoutInflater.from(parent.context),

                parent,

                false

            )


        return ViewHolder(binding)

    }





    override fun onBindViewHolder(

        holder: ViewHolder,

        position: Int

    ) {


        val appointment =
            appointmentList[position]



        holder.binding.txtDoctorName.text =
            "Doctor : ${appointment.doctorName}"



        holder.binding.txtAppointmentDate.text =
            "Date : ${appointment.appointmentDate}"



        holder.binding.txtAppointmentTime.text =
            "Time : ${appointment.appointmentTime}"



        holder.binding.txtAppointmentStatus.text =
            "Status : ${appointment.appointmentStatus}"


    }





    override fun getItemCount(): Int {

        return appointmentList.size

    }




    fun updateList(

        newList: ArrayList<Appointment>

    ){

        appointmentList = newList

        notifyDataSetChanged()

    }


}