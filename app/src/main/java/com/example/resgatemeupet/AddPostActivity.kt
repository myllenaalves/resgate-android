
package com.example.resgatemeupet

import Endpoint
import NetworkUtils
import Pets
import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*



class AddPostActivity: AppCompatActivity() {
    val GALLERY_REQUEST_CODE = 100;
    var imagePath = "";
    var loadingMessage: TextView? = null;
    var button: Button? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_post)
        button = findViewById<Button>(R.id.button4)
        val imageButton = findViewById<ImageButton>(R.id.imageButton)
        loadingMessage = findViewById<TextView>(R.id.loadingImage)
        loadingMessage?.setText("");
        this.button?.isEnabled = false;

        this.button?.setOnClickListener() {
            val userInfo = Pets(
                id = null,
                title = findViewById<TextView>(R.id.titlePost).text.toString(),
                description = findViewById<TextView>(R.id.descriptionPost).text.toString(),
                number = findViewById<TextView>(R.id.numberPost).text.toString(),
                image = imagePath
            )
            addPost(userInfo) {
                if (it?.id != null) {
                    val intent = Intent(this, FeedActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(baseContext, "Error registering new user", Toast.LENGTH_SHORT).show()
                }
            }
        }
        imageButton.setOnClickListener() {
            selectImageFromGallery()
        }
    }

    private fun selectImageFromGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(
            Intent.createChooser(
                intent,
                "Please select..."
            ),
            GALLERY_REQUEST_CODE
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

        if (requestCode == GALLERY_REQUEST_CODE
            && resultCode == Activity.RESULT_OK
            && data != null
            && data.data != null
        ) {

            // Get the Uri of data
            val file_uri = data?.data
            file_uri?.let { uploadImageToFirebase(it) }
        }
    }

    private fun uploadImageToFirebase(fileUri: Uri) {
        if (fileUri != null) {
            val fileName = UUID.randomUUID().toString() +".jpg"

            val database = FirebaseDatabase.getInstance()
            val refStorage = FirebaseStorage.getInstance().reference.child("images/$fileName")

            this.button?.isEnabled = false;
            loadingMessage?.setText("Aguarde, imagem sendo carregada!");


            refStorage.putFile(fileUri)
                .addOnSuccessListener(
                    OnSuccessListener<UploadTask.TaskSnapshot> { taskSnapshot ->
                        taskSnapshot.storage.downloadUrl.addOnSuccessListener {
                            val imageUrl = it.toString()
                            imagePath = imageUrl
                            loadingMessage?.setText("Imagem carregada!");
                            this.button?.isEnabled = true;
                        }
                    })

                ?.addOnFailureListener(OnFailureListener { e ->
                    print(e.message)
                })
        }
    }

    private fun addPost(userData: Pets, onResult: (Pets?) -> Unit) {
        val retrofit = NetworkUtils.ServiceBuilder.buildService(Endpoint::class.java)
        retrofit.postPets(userData).enqueue(
            object : Callback<Pets> {
                override fun onFailure(call: Call<Pets>, t: Throwable) {
                    onResult(null)
                }

                override fun onResponse(call: Call<Pets>, response: Response<Pets>) {
                    val addedUser = response.body()
                    onResult(addedUser)
                }
            }
        )
    }


}







