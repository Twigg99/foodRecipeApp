package com.example.foodrecipeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import com.example.foodrecipeapp.databinding.ActivityForgotPasswordBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class ForgotPasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityForgotPasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.recoverPasswordButton.setOnClickListener{
            val email = binding.emailForRecovery.text.toString().trim()
            if (email.isEmpty()){
                Snackbar.make(binding.root, "Please enter email", Snackbar.LENGTH_LONG).show()
            } else if (!isValidEmail(email)) {
                Snackbar.make(binding.root, "Invalid Email", Snackbar.LENGTH_LONG).show()

            } else {
                FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                    .addOnCompleteListener { task ->

                        if (task.isSuccessful) {
                            Snackbar.make(binding.root, "Email sent", Snackbar.LENGTH_LONG).show()
                        } else {
                            Log.d("ForgotPasswordActivity", "Error with resetting password")

                            Snackbar.make(binding.root, "Error", Snackbar.LENGTH_LONG).show()

                        }
                    }
            }
        }
    }

    private fun isValidEmail(email: String): Boolean {
        val pattern = Patterns.EMAIL_ADDRESS
        return pattern.matcher(email).matches()
    }


}

