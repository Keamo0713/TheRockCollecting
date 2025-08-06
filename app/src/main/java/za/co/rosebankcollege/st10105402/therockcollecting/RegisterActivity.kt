package za.co.rosebankcollege.st10105402.therockcollecting

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException


class RegisterActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()

        val registerButton = findViewById<Button>(R.id.registerButton)
        val loginButton = findViewById<Button>(R.id.LoginButton)
        registerButton.setOnClickListener {
            val emailEditText = findViewById<EditText>(R.id.emailEditText)
            val passwordEditText = findViewById<EditText>(R.id.passwordEditText)

            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            registerUser(email, password)
        }
        loginButton.setOnClickListener {
            // Handle the sign-in button click event
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)

            // Finish the RegisterActivity if needed
            finish()
        }
    }





    private fun registerUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Registration successful, user is signed in
                    val user = auth.currentUser
                    // Handle the registered user as needed
                } else {
                    // Registration failed, handle the error
                    val exception = task.exception
                    if (exception is FirebaseAuthException) {
                        val errorCode = exception.errorCode
                        val errorMessage = exception.message
                        // Handle specific error codes or display a generic error message
                    } else if (exception is FirebaseAuthInvalidCredentialsException) {
                        // Handle invalid email or password format error
                    } else {
                        // Handle other registration errors
                        auth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(this) { task ->
                                if (task.isSuccessful) {
                                    // Registration successful, user is signed in
                                    val user = auth.currentUser
                                    // Handle the registered user as needed
                                } else {
                                    // Registration failed, handle the error
                                    val exception = task.exception
                                    if (exception is FirebaseAuthException) {
                                        val errorCode = exception.errorCode
                                        val errorMessage = exception.message
                                        when (errorCode) {
                                            "ERROR_INVALID_EMAIL" -> {
                                                // Handle invalid email format error
                                                // Display an error message to the user
                                                Toast.makeText(
                                                    this,
                                                    "Invalid email format",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                            "ERROR_WEAK_PASSWORD" -> {
                                                // Handle weak password error
                                                // Display an error message to the user
                                                Toast.makeText(
                                                    this,
                                                    "Weak password",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                            // Add more error cases as needed
                                            else -> {
                                                // Handle other FirebaseAuthException errors
                                                // Display a generic error message to the user
                                                Toast.makeText(
                                                    this,
                                                    "Registration failed",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                        }
                                    } else if (exception is FirebaseAuthInvalidCredentialsException) {
                                        // Handle invalid email or password format error
                                        // Display an error message to the user
                                        Toast.makeText(
                                            this,
                                            "Invalid email or password format",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    } else {
                                        // Handle other registration errors
                                        // Display a generic error message to the user
                                        Toast.makeText(
                                            this,
                                            "Registration failed",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }

                                }
                            }
                    }
                }
            }
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)

        // Finish the RegisterActivity if needed
        finish()
    }
}