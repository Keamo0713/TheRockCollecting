package za.co.rosebankcollege.st10105402.therockcollecting

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import za.co.rosebankcollege.st10105402.therockcollecting.Item
import za.co.rosebankcollege.st10105402.therockcollecting.ItemAdapter
import za.co.rosebankcollege.st10105402.therockcollecting.R

class ViewByCategoryActivity : AppCompatActivity() {

    private lateinit var spinnerCategory: Spinner
    private lateinit var recyclerView: RecyclerView
    private lateinit var btnBack: Button

    private lateinit var category: String

    private lateinit var firestore: FirebaseFirestore
    private lateinit var itemAdapter: ItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_by_category)

        spinnerCategory = findViewById(R.id.spinnerCategory)
        recyclerView = findViewById(R.id.recyclerView)
        btnBack = findViewById(R.id.btnBack)

        firestore = FirebaseFirestore.getInstance()
        itemAdapter = ItemAdapter()

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = itemAdapter

        val categories = arrayOf("Poor", "Good", "Excellent")

        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCategory.adapter = spinnerAdapter

        spinnerCategory.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                category = categories[position]
                loadItemsByCategory(category)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })

        btnBack.setOnClickListener {
            finish()
        }
    }

    private fun loadItemsByCategory(category: String) {
        firestore.collection("items")
            .whereEqualTo("category", category)
            .orderBy("itemName", Query.Direction.ASCENDING)
            .get()
            .addOnSuccessListener { querySnapshot ->
                val items = querySnapshot.toObjects(Item::class.java)
                itemAdapter.setItems(items)
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error loading items: $e", Toast.LENGTH_SHORT).show()
            }
    }
}
