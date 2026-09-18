package com.example.medicareappointment.activities


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.medicareappointment.database.DatabaseHelper
import com.example.medicareappointment.databinding.ActivityAdminProfileBinding


class AdminProfileActivity : AppCompatActivity() {


    private lateinit var binding: ActivityAdminProfileBinding

    private lateinit var databaseHelper: DatabaseHelper


    private var selectedImageUri: Uri? = null



    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)


        binding =
            ActivityAdminProfileBinding.inflate(layoutInflater)


        setContentView(binding.root)



        databaseHelper =
            DatabaseHelper(this)



        loadAdminProfile()



        binding.btnChangeProfileImage.setOnClickListener {


            openGallery()


        }



        binding.btnLogout.setOnClickListener {


            logout()


        }


    }





    private fun loadAdminProfile() {


        val admin = databaseHelper.getAdminProfile()



        if(admin != null) {


            binding.txtAdminName.text =
                admin.name



            binding.txtAdminRole.text =
                admin.role



            binding.txtAdminEmail.text =
                "Email : ${admin.email}"



            binding.txtAdminPhone.text =
                "Phone : ${admin.phone}"



            binding.txtAdminCreated.text =
                "Created Date : ${admin.createdDate}"



            if(admin.profileImage.isNotEmpty()) {


                binding.imgAdminProfile.setImageURI(

                    Uri.parse(
                        admin.profileImage
                    )

                )

            }


        }

    }





    private fun openGallery() {

        val intent = Intent(
            Intent.ACTION_OPEN_DOCUMENT
        )

        intent.type = "image/*"

        intent.addCategory(
            Intent.CATEGORY_OPENABLE
        )

        intent.addFlags(
            Intent.FLAG_GRANT_READ_URI_PERMISSION or
                    Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION
        )

        startActivityForResult(
            intent,
            200
        )

    }



    override fun onActivityResult(

        requestCode: Int,

        resultCode: Int,

        data: Intent?

    ) {


        super.onActivityResult(

            requestCode,

            resultCode,

            data

        )



        if (

            requestCode == 200 &&

            resultCode == RESULT_OK

        ) {


            data?.data?.let { uri ->


                try {

                    contentResolver.takePersistableUriPermission(

                        uri,

                        Intent.FLAG_GRANT_READ_URI_PERMISSION

                    )


                } catch (e: Exception) {

                    e.printStackTrace()

                }


                selectedImageUri = uri


            }



            if (selectedImageUri != null) {


                // Show selected image

                binding.imgAdminProfile.setImageURI(

                    selectedImageUri

                )



                // Save image path in database

                databaseHelper.updateAdminProfileImage(

                    1,

                    selectedImageUri.toString()

                )



                Toast.makeText(

                    this,

                    "Profile image updated",

                    Toast.LENGTH_SHORT

                ).show()


            }


        }


    }




    private fun logout() {


        val intent = Intent(

            this,

            LoginActivity::class.java

        )


        intent.flags =

            Intent.FLAG_ACTIVITY_NEW_TASK or

                    Intent.FLAG_ACTIVITY_CLEAR_TASK



        startActivity(intent)



        finish()


    }


}