package com.example.medicareappointment.activities


import android.net.Uri
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.medicareappointment.R


class DocumentViewerActivity : AppCompatActivity() {


    private lateinit var imgDocument: ImageView

    private lateinit var webDocument: WebView



    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)


        setContentView(R.layout.activity_document_viewer)



        imgDocument =
            findViewById(R.id.imgDocument)


        webDocument =
            findViewById(R.id.webDocument)



        val documentUri =

            intent.getStringExtra("document")



        if(documentUri != null){

            openDocument(documentUri)

        }


    }





    private fun openDocument(uri: String) {


        val fileUri = Uri.parse(uri)


        val mimeType = contentResolver.getType(fileUri)



        if (

            mimeType == "image/jpeg" ||

            mimeType == "image/png"

        ) {


            imgDocument.visibility =
                View.VISIBLE


            webDocument.visibility =
                View.GONE



            imgDocument.setImageURI(fileUri)



        } else if (

            mimeType == "application/pdf"

        ) {


            imgDocument.visibility =
                View.GONE


            webDocument.visibility =
                View.VISIBLE



            webDocument.settings.javaScriptEnabled = true



            webDocument.loadUrl(

                "https://docs.google.com/gview?embedded=true&url=$uri"

            )


        } else {


            Toast.makeText(

                this,

                "Unsupported document format : $mimeType",

                Toast.LENGTH_SHORT

            ).show()


        }

    }


}