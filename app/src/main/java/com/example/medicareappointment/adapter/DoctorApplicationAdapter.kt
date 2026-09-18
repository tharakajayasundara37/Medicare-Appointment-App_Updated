package com.example.medicareappointment.adapter


import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.medicareappointment.databinding.ItemDoctorApplicationBinding
import com.example.medicareappointment.model.Doctor
import com.example.medicareappointment.activities.DocumentViewerActivity


class DoctorApplicationAdapter(

    private val doctorList: ArrayList<Doctor>,

    private val onStatusChange: (Int, String) -> Unit

) : RecyclerView.Adapter<DoctorApplicationAdapter.ViewHolder>() {


    inner class ViewHolder(

        val binding: ItemDoctorApplicationBinding

    ) : RecyclerView.ViewHolder(binding.root)



    override fun onCreateViewHolder(

        parent: ViewGroup,

        viewType: Int

    ): ViewHolder {


        val binding =

            ItemDoctorApplicationBinding.inflate(

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


        val doctor = doctorList[position]



        holder.binding.txtDoctorApplicationName.text =

            doctor.name



        holder.binding.txtDoctorApplicationSpecialization.text =

            doctor.specialization



        holder.binding.txtDoctorApplicationPhone.text =

            "Phone : ${doctor.phone}"



        holder.binding.txtDoctorApplicationQualification.text =

            "Qualification : ${doctor.qualification}"



        holder.binding.txtDoctorApplicationExperience.text =

            "Experience : ${doctor.experience} Years"



        holder.binding.txtDoctorApplicationWorkingTime.text =

            "${doctor.workingDays} | ${doctor.startTime} - ${doctor.endTime}"



        holder.binding.txtDoctorApplicationStatus.text =

            doctor.status





        // View Document Button

        if (doctor.document.isNotEmpty()) {


            holder.binding.btnViewDocument.visibility =

                View.VISIBLE



            holder.binding.btnViewDocument.setOnClickListener {


                val intent = Intent(

                    holder.itemView.context,

                    DocumentViewerActivity::class.java

                )


                intent.putExtra(

                    "document",

                    doctor.document

                )


                holder.itemView.context.startActivity(intent)


                holder.itemView.context.startActivity(intent)


            }


        } else {


            holder.binding.btnViewDocument.visibility =

                View.GONE


        }






        // Approve Reject Buttons

        if (

            doctor.status.equals(

                "Pending",

                ignoreCase = true

            )

        ) {


            holder.binding.layoutDoctorActions.visibility =

                View.VISIBLE



            holder.binding.btnApproveDoctor.visibility =

                View.VISIBLE



            holder.binding.btnRejectDoctor.visibility =

                View.VISIBLE




            holder.binding.btnApproveDoctor.setOnClickListener {


                onStatusChange(

                    doctor.id,

                    "Approved"

                )


            }





            holder.binding.btnRejectDoctor.setOnClickListener {


                onStatusChange(

                    doctor.id,

                    "Rejected"

                )


            }




        } else {



            holder.binding.layoutDoctorActions.visibility =

                View.GONE



            holder.binding.btnApproveDoctor.setOnClickListener(null)


            holder.binding.btnRejectDoctor.setOnClickListener(null)


        }


    }





    override fun getItemCount(): Int {


        return doctorList.size


    }


}