package com.example.medicareappointment.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.medicareappointment.databinding.ItemDoctorAppointmentBinding
import com.example.medicareappointment.model.Appointment



class DoctorAppointmentAdapter(

    private val appointmentList: ArrayList<Appointment>,

    private val onStatusChange: (Int, String) -> Unit

) : RecyclerView.Adapter<DoctorAppointmentAdapter.ViewHolder>() {



    class ViewHolder(

        val binding: ItemDoctorAppointmentBinding

    ) : RecyclerView.ViewHolder(binding.root)







    override fun onCreateViewHolder(

        parent: ViewGroup,

        viewType: Int

    ): ViewHolder {


        val binding =

            ItemDoctorAppointmentBinding.inflate(

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


        val appointment = appointmentList[position]



        val status =

            appointment.appointmentStatus.lowercase()






        holder.binding.txtPatientName.text =

            appointment.patientName






        holder.binding.txtPatientPhone.text =

            "Phone : ${appointment.patientPhone}"






        holder.binding.txtAppointmentDate.text =

            "Date : ${appointment.appointmentDate}"






        holder.binding.txtAppointmentTime.text =

            "Time : ${appointment.appointmentTime}"






        holder.binding.txtAppointmentStatus.text =

            "Status : ${appointment.appointmentStatus}"








        when(status){



            "completed",

            "cancelled" -> {



                holder.binding.btnConfirm.visibility =

                    View.GONE



                holder.binding.btnComplete.visibility =

                    View.GONE



                holder.binding.btnCancel.visibility =

                    View.GONE



            }





            "confirmed" -> {



                holder.binding.btnConfirm.visibility =

                    View.GONE



                holder.binding.btnComplete.visibility =

                    View.VISIBLE



                holder.binding.btnCancel.visibility =

                    View.VISIBLE



            }





            else -> {



                holder.binding.btnConfirm.visibility =

                    View.VISIBLE



                holder.binding.btnComplete.visibility =

                    View.VISIBLE



                holder.binding.btnCancel.visibility =

                    View.VISIBLE



            }



        }









        holder.binding.btnConfirm.setOnClickListener {



            onStatusChange(

                appointment.id,

                "Confirmed"

            )



        }







        holder.binding.btnComplete.setOnClickListener {



            onStatusChange(

                appointment.id,

                "Completed"

            )



        }







        holder.binding.btnCancel.setOnClickListener {



            onStatusChange(

                appointment.id,

                "Cancelled"

            )



        }



    }








    override fun getItemCount(): Int {


        return appointmentList.size


    }



}