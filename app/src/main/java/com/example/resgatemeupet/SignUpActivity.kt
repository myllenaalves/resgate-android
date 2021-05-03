package com.example.resgatemeupet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.w3c.dom.Text

class SignUpActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = Firebase.auth
        val button = findViewById<Button>(R.id.button)
        val email = findViewById<TextView>(R.id.editTextTextEmailAddress)
        val password = findViewById<TextView>(R.id.editTextTextPassword)
        val passwordConfirmation = findViewById<TextView>(R.id.editTextTextPassword2)

        button.setOnClickListener() {
            Toast.makeText(this, email.text.toString(), Toast.LENGTH_SHORT).show()
            if(password.text.toString() == passwordConfirmation.text.toString()) {
                auth.createUserWithEmailAndPassword(email.text.toString(), password.text.toString())
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("TAG", "createUserWithEmail:success")
                                val user = auth.currentUser
                                Toast.makeText(this, "Clicked 1", Toast.LENGTH_SHORT).show()
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("TAG", "createUserWithEmail:failure", task.exception)
                                Toast.makeText(this, "Clicked 2", Toast.LENGTH_SHORT).show()
                            }
                        }
            }
        }
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if(currentUser != null){
            reload();
        }
    }

    private fun reload() {
        TODO("Not yet implemented")
    }




}