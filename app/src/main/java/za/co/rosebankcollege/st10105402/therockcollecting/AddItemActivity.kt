package za.co.rosebankcollege.st10105402.therockcollecting
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*

class AddItemActivity : AppCompatActivity() {

    private val CAMERA_REQUEST_CODE = 101
    private val CAMERA_PERMISSION_REQUEST_CODE = 100

    private lateinit var itemNameEditText: EditText
    private lateinit var descriptionEditText: EditText
    private lateinit var purchaseDateEditText: EditText
    private lateinit var priceEditText: EditText
    private lateinit var categoryEditText: EditText
    private lateinit var captureImageButton: Button
    private lateinit var itemImageView: ImageView
    private lateinit var saveButton: Button



    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)


        itemNameEditText = findViewById(R.id.itemNameEditText)
        descriptionEditText = findViewById(R.id.descriptionEditText)
        purchaseDateEditText = findViewById(R.id.purchaseDateEditText)
        priceEditText = findViewById(R.id.priceEditText)
        categoryEditText = findViewById(R.id.categoryEditText)
        captureImageButton = findViewById(R.id.captureImageButton)
        itemImageView = findViewById(R.id.itemImageView)
        saveButton = findViewById(R.id.saveButton)




        captureImageButton.setOnClickListener {
            captureImage()
        }

        saveButton.setOnClickListener {
            saveItem()


        }

    }


    private fun captureImage() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                CAMERA_PERMISSION_REQUEST_CODE
            )
        } else {
            startCameraActivity()
        }
    }

    private fun startCameraActivity() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                captureImage()
            } else {
                Toast.makeText(this, "Camera permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
            val image = data?.extras?.get("data") as? Bitmap
            if (image != null) {
                itemImageView.setImageBitmap(image)
                val timestamp =
                    SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
                val imageFileName = "IMG_$timestamp.jpg"
                val imageFileRef = FirebaseStorage.getInstance().reference.child(imageFileName)
                val baos = ByteArrayOutputStream()
                image.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                val imageData = baos.toByteArray()
                val uploadTask = imageFileRef.putBytes(imageData)
                uploadTask.continueWithTask { task ->
                    if (!task.isSuccessful) {
                        task.exception?.let {
                            throw it
                        }
                    }
                    imageFileRef.downloadUrl
                }.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val downloadUri = task.result
                        imageUri = downloadUri
                    }
                }
            }
        }
    }

    private fun saveItem() {
        val itemName = itemNameEditText.text.toString().trim()
        val description = descriptionEditText.text.toString().trim()
        val purchaseDate = purchaseDateEditText.text.toString().trim()
        val priceText = priceEditText.text.toString().trim()
        val category = categoryEditText.text.toString().trim()


        if (itemName.isNotEmpty() && description.isNotEmpty() && purchaseDate.isNotEmpty() && priceText.isNotEmpty()) {
            val price = priceText.toDouble() // Convert price to Double if needed

            val db = FirebaseFirestore.getInstance()
            val item = hashMapOf(
                "itemName" to itemName,
                "description" to description,
                "purchaseDate" to purchaseDate,
                "price" to price,
                "category" to category,
                "imageUri" to imageUri.toString()
            )

            db.collection("items")
                .add(item)
                .addOnSuccessListener { documentReference ->
                    Toast.makeText(this, "Item added successfully", Toast.LENGTH_SHORT).show()
                    finish()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Error adding item: $e", Toast.LENGTH_SHORT).show()
                }
        } else {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
        }
    }
}