package za.co.rosebankcollege.st10105402.therockcollecting

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException

class LoginActivity : AppCompatActivity() {
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var registerButton: Button
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Initialize Firebase Authentication
        auth = FirebaseAuth.getInstance()

        // Initialize views
        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        loginButton = findViewById(R.id.loginButton)
        registerButton = findViewById(R.id.registerButton)

        loginButton.setOnClickListener {
            // Perform login operation
            loginUser()
        }

        registerButton.setOnClickListener {
            // Navigate to RegisterActivity when register button is clicked
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loginUser() {
        val email = emailEditText.text.toString()
        val password = passwordEditText.text.toString()

        if (email.isNotEmpty() && password.isNotEmpty()) {
            // Sign in the user with the provided email and password
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Login successful, navigate to the main activity
                        val intent = Intent(this, DashboardActivity::class.java)
                        startActivity(intent)
                        finish() // Finish the LoginActivity to prevent returning to it using the back button
                    } else {
                        // Handle login errors
                        try {
                            throw task.exception!!
                        } catch (e: FirebaseAuthInvalidUserException) {
                            // User not found
                            Toast.makeText(this, "Invalid email address", Toast.LENGTH_SHORT).show()
                        } catch (e: FirebaseAuthInvalidCredentialsException) {
                            // Invalid password
                            Toast.makeText(this, "Invalid password", Toast.LENGTH_SHORT).show()
                        } catch (e: Exception) {
                            // Other login errors
                            Toast.makeText(this, "Login failed. Please try again.", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
        } else {
            Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show()
        }
    }
}
