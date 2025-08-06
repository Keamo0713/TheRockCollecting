package za.co.rosebankcollege.st10105402.therockcollecting

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import za.co.rosebankcollege.st10105402.therockcollecting.R

class TargetAmountActivity : AppCompatActivity() {

    private lateinit var editPoor: EditText
    private lateinit var editGood: EditText
    private lateinit var editExcellent: EditText
    private lateinit var btnSave: Button
    private lateinit var textProgress: TextView
    private lateinit var progressBarPoor: ProgressBar
    private lateinit var progressBarGood: ProgressBar
    private lateinit var progressBarExcellent: ProgressBar
    private lateinit var progressBarTotal: ProgressBar
    private lateinit var textTotalProgress: TextView

    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_target)

        editPoor = findViewById(R.id.editPoor)
        editGood = findViewById(R.id.editGood)
        editExcellent = findViewById(R.id.editExcellent)
        btnSave = findViewById(R.id.btnSave)
        textProgress = findViewById(R.id.textProgress)
        progressBarPoor = findViewById(R.id.progressBarPoor)
        progressBarGood = findViewById(R.id.progressBarGood)
        progressBarExcellent = findViewById(R.id.progressBarExcellent)
        progressBarTotal = findViewById(R.id.progressBarTotal)
        textTotalProgress = findViewById(R.id.textTotalProgress)

        btnSave.setOnClickListener {
            val targetPoor = editPoor.text.toString().toInt()
            val targetGood = editGood.text.toString().toInt()
            val targetExcellent = editExcellent.text.toString().toInt()

            saveTargetsToFirestore(targetPoor, targetGood, targetExcellent)
        }

        fetchTargetsFromFirestore()
    }

    private fun saveTargetsToFirestore(targetPoor: Int, targetGood: Int, targetExcellent: Int) {
        val targets = hashMapOf(
            "targetPoor" to targetPoor,
            "targetGood" to targetGood,
            "targetExcellent" to targetExcellent
        )

        firestore.collection("userTargets")
            .document("15y40q0OO5eEff1o090O")
            .set(targets)
            .addOnSuccessListener {
                // Targets saved successfully
                fetchCountValuesFromFirestore(targetPoor, targetGood, targetExcellent)
            }
            .addOnFailureListener { e ->
                // Handle error while saving targets to Firestore
                e.printStackTrace()
            }
    }

    private fun fetchTargetsFromFirestore() {
        firestore.collection("userTargets")
            .document("15y40q0OO5eEff1o090O")
            .get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    val targetPoor = document.getLong("targetPoor")?.toInt() ?: 0
                    val targetGood = document.getLong("targetGood")?.toInt() ?: 0
                    val targetExcellent = document.getLong("targetExcellent")?.toInt() ?: 0

                    editPoor.setText(targetPoor.toString())
                    editGood.setText(targetGood.toString())
                    editExcellent.setText(targetExcellent.toString())

                    fetchCountValuesFromFirestore(targetPoor, targetGood, targetExcellent)
                }
            }
            .addOnFailureListener { e ->
                // Handle error while fetching targets from Firestore
                e.printStackTrace()
            }
    }

    private fun fetchCountValuesFromFirestore(targetPoor: Int, targetGood: Int, targetExcellent: Int) {
        val documentRef = firestore.collection("Targets").document("Jf9urtotTfqaaejr1BxL")

        documentRef.get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    val countPoor = documentSnapshot.getLong("countPoor")?.toInt() ?: 0
                    val countGood = documentSnapshot.getLong("countGood")?.toInt() ?: 0
                    val countExcellent = documentSnapshot.getLong("countExcellent")?.toInt() ?: 0

                    calculateProgress(targetPoor, targetGood, targetExcellent, countPoor, countGood, countExcellent)
                } else {
                    // Document doesn't exist
                    // Handle the case accordingly
                    progressBarPoor.progress = 0
                    progressBarGood.progress = 0
                    progressBarExcellent.progress = 0
                    progressBarTotal.progress = 0

                    textProgress.text = "Progress: 0% - 0% - 0%"
                    textTotalProgress.text = "0%"

                    // Display a message or perform any other action for a non-existing document
                    Toast.makeText(this, "Target document doesn't exist", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { e ->
                // Handle error while retrieving the document
                progressBarPoor.progress = 0
                progressBarGood.progress = 0
                progressBarExcellent.progress = 0
                progressBarTotal.progress = 0

                textProgress.text = "Progress: 0% - 0% - 0%"
                textTotalProgress.text = "0%"

                // Display a message or perform any other action for the failure to retrieve the document
                Toast.makeText(this, "Failed to retrieve target document", Toast.LENGTH_SHORT).show()
            }
    }

    private fun calculateProgress(
        targetPoor: Int,
        targetGood: Int,
        targetExcellent: Int,
        countPoor: Int,
        countGood: Int,
        countExcellent: Int
    ) {
        val progressPoor = (countPoor.toDouble() / targetPoor.toDouble()) * 100
        val progressGood = (countGood.toDouble() / targetGood.toDouble()) * 100
        val progressExcellent = (countExcellent.toDouble() / targetExcellent.toDouble()) * 100

        progressBarPoor.progress = progressPoor.toInt()
        progressBarGood.progress = progressGood.toInt()
        progressBarExcellent.progress = progressExcellent.toInt()

        val totalProgress = (progressPoor + progressGood + progressExcellent) / 3
        progressBarTotal.progress = totalProgress.toInt()

        textProgress.text = "Progress: ${String.format("%.0f", progressPoor)}% - ${
            String.format(
                "%.0f",
                progressGood
            )
        }% - ${String.format("%.0f", progressExcellent)}%"
        textTotalProgress.text = "${String.format("%.0f", totalProgress)}%"
    }
}
