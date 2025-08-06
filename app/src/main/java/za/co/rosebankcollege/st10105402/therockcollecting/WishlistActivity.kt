package za.co.rosebankcollege.st10105402.therockcollecting

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class WishlistActivity : AppCompatActivity() {

    private lateinit var stoneEditText: EditText
    private lateinit var addButton: Button
    private lateinit var btnBack: Button
    private lateinit var wishlistRecyclerView: RecyclerView

    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    private val wishlistItems = mutableListOf<WishlistItem>()
    private lateinit var wishlistAdapter: WishlistAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wishlist)

        stoneEditText = findViewById(R.id.stoneEditText)
        addButton = findViewById(R.id.addButton)
        btnBack = findViewById(R.id.btnBack)
        wishlistRecyclerView = findViewById(R.id.wishlistRecyclerView)

        wishlistAdapter = WishlistAdapter(wishlistItems)
        wishlistRecyclerView.adapter = wishlistAdapter
        wishlistRecyclerView.layoutManager = LinearLayoutManager(this)

        addButton.setOnClickListener {
            val itemName = stoneEditText.text.toString().trim()

            if (itemName.isNotEmpty()) {
                saveItemToFirestore(itemName)
            } else {
                Toast.makeText(this, "Item name cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }

        btnBack.setOnClickListener {
            // Create an intent to launch the dashboard activity
            val intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
        }

        // Load wishlist items from Firestore
        loadWishlistItems()
    }

    private fun saveItemToFirestore(itemName: String) {
        val item = hashMapOf(
            "stoneName" to itemName
        )

        firestore.collection("wishlist")
            .add(item)
            .addOnSuccessListener { documentReference ->
                // Item added successfully
                stoneEditText.text.clear()
                Toast.makeText(this, "Item added to wishlist", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                // Handle error while adding item to Firestore
                Toast.makeText(this, "Failed to add item to wishlist", Toast.LENGTH_SHORT).show()
            }
    }

    private fun loadWishlistItems() {
        firestore.collection("wishlist")
            .get()
            .addOnSuccessListener { documents ->
                wishlistItems.clear()

                for (document in documents) {
                    val stoneName = document.getString("stoneName")
                    if (stoneName != null) {
                        wishlistItems.add(WishlistItem(stoneName))
                    }
                }

                wishlistAdapter.notifyDataSetChanged()
            }
            .addOnFailureListener { e ->
                // Handle error while fetching wishlist items from Firestore
                Toast.makeText(this, "Failed to load wishlist items", Toast.LENGTH_SHORT).show()
            }
    }
}
