package com.example.foodrecipeapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.foodrecipeapp.databinding.ActivityRegisterBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private var db = Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        setupViews()
    }

    private fun setupViews() {
        binding.signInHereText.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        val today = Calendar.getInstance()
        val year = today.get(Calendar.YEAR)
        val month = today.get(Calendar.MONTH) + 1
        val day = today.get(Calendar.DAY_OF_MONTH)
        binding.editTextDate.setText("$day/$month/$year")
        binding.registerButton.setOnClickListener {
            val email = binding.emailAddress.text.toString()
            val password = binding.password.text.toString()
            val name = binding.personName.text.toString()
            val phoneNum = binding.TextPhone.text.toString()
            val dateOfBirth = binding.editTextDate.text.toString()

            val userMap = hashMapOf(
                "Name" to name,
                "Email" to email,
                "password" to password,
                "phone" to phoneNum,
                "DOB" to dateOfBirth
            )

            firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val userID = firebaseAuth.currentUser!!.uid
                        db.collection("Users").document(userID).set(userMap)
                            .addOnSuccessListener {

                                Snackbar.make(binding.root, "User added", Snackbar.LENGTH_LONG)
                                    .show()
                                val intent = Intent(this, MainActivity::class.java)
                                startActivity(intent)
                            }
                            .addOnFailureListener { exception ->

                                Snackbar.make(
                                    binding.root,
                                    "Failed to add user",
                                    Snackbar.LENGTH_LONG
                                ).show()
                                Log.e(
                                    "RegisterActivity",
                                    "Failed to add data to Firestore",
                                    exception
                                )
                            }
                    } else {

                        Toast.makeText(this, task.exception.toString(), Toast.LENGTH_LONG).show()
                        Log.e("RegisterActivity", "Failed to create Firebase user", task.exception)
                    }
                }
        }
    }   }
