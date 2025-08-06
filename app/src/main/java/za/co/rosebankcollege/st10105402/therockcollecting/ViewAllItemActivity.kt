
package za.co.rosebankcollege.st10105402.therockcollecting

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import za.co.rosebankcollege.st10105402.therockcollecting.AddItemActivity
import za.co.rosebankcollege.st10105402.therockcollecting.Item
import za.co.rosebankcollege.st10105402.therockcollecting.ItemAdapter
import za.co.rosebankcollege.st10105402.therockcollecting.R

class ViewAllItemActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ItemAdapter
    private lateinit var backButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Create a new instance of the adapter
        adapter = ItemAdapter()

        // Set the adapter to the RecyclerView
        recyclerView.adapter = adapter

        // Call a method to retrieve the list of items from Firestore and update the adapter
        fetchItemsFromFirestore()

        val addItemButton: Button = findViewById(R.id.btnAddItem)
        addItemButton.setOnClickListener {
            // Start the "Add Item" activity
            val intent = Intent(this, AddItemActivity::class.java)
            startActivity(intent)

        }
        backButton = findViewById(R.id.btnBack)
        backButton.setOnClickListener {
            // Go back to the dashboard activity
            onBackPressed()
        }
    }


    private fun fetchItemsFromFirestore() {
        val db = FirebaseFirestore.getInstance()
        val itemsCollection = db.collection("items")

        itemsCollection.get()
            .addOnSuccessListener { querySnapshot ->
                val itemList = mutableListOf<Item>()
                for (document in querySnapshot.documents) {
                    val item = document.toObject(Item::class.java)
                    if (item != null) {
                        itemList.add(item)
                    }
                }
                adapter.setItems(itemList)
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Failed to fetch items: $exception", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onBackPressed() {
        // Override the back button behavior to go back to the dashboard activity
        val intent = Intent(this, DashboardActivity::class.java)
        startActivity(intent)
        finish()
    }
}