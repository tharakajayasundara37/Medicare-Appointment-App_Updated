package com.example.medicareappointment.activities


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.medicareappointment.R


class DocumentViewerActivity : AppCompatActivity() {


    private lateinit var imgDocument: ImageView



    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)


        setContentView(R.layout.activity_document_viewer)



        imgDocument =

            findViewById(R.id.imgDocument)





        val document =

            intent.getStringExtra("document")




        if(document.isNullOrEmpty()){


            Toast.makeText(

                this,

                "Document not found",

                Toast.LENGTH_SHORT

            ).show()



            finish()

            return

        }




        openDocument(document)



    }







    private fun openDocument(path:String){


        try {


            val uri = Uri.parse(path)



            val mimeType =

                contentResolver.getType(uri)





            when {



                // IMAGE FILES
                mimeType?.startsWith("image") == true ||

                        path.endsWith(".jpg", true) ||

                        path.endsWith(".jpeg", true) ||

                        path.endsWith(".png", true) -> {



                    imgDocument.visibility =

                        View.VISIBLE



                    imgDocument.setImageURI(uri)



                }







                // PDF FILE
                mimeType == "application/pdf" ||

                        path.endsWith(".pdf", true) -> {



                    openPdf(uri)



                }







                // OTHER FILES
                else -> {


                    openOtherFile(uri)


                }



            }



        }

        catch(e:Exception){


            e.printStackTrace()



            Toast.makeText(

                this,

                "Cannot open document",

                Toast.LENGTH_SHORT

            ).show()



        }



    }









    private fun openPdf(uri: Uri){



        try {



            val intent = Intent(

                Intent.ACTION_VIEW

            )



            intent.setDataAndType(

                uri,

                "application/pdf"

            )



            intent.addFlags(

                Intent.FLAG_GRANT_READ_URI_PERMISSION

            )




            startActivity(

                Intent.createChooser(

                    intent,

                    "Open PDF using"

                )

            )



        }

        catch(e:Exception){



            Toast.makeText(

                this,

                "No PDF viewer available",

                Toast.LENGTH_SHORT

            ).show()



        }



    }









    private fun openOtherFile(uri: Uri){



        try {



            val intent = Intent(

                Intent.ACTION_VIEW

            )



            intent.setDataAndType(

                uri,

                "*/*"

            )



            intent.addFlags(

                Intent.FLAG_GRANT_READ_URI_PERMISSION

            )




            startActivity(

                Intent.createChooser(

                    intent,

                    "Open document using"

                )

            )



        }

        catch(e:Exception){



            Toast.makeText(

                this,

                "Unsupported document format",

                Toast.LENGTH_SHORT

            ).show()



        }



    }



}