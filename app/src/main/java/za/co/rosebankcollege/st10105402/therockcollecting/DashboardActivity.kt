package za.co.rosebankcollege.st10105402.therockcollecting



import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class DashboardActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        // Find and set click listeners for the icons
        findViewById<View>(R.id.iconImageView).setOnClickListener(this)
        findViewById<View>(R.id.iconImageView3).setOnClickListener(this)
        findViewById<View>(R.id.iconImageView4).setOnClickListener(this)
        findViewById<View>(R.id.iconImageView5).setOnClickListener(this)
        findViewById<View>(R.id.iconImageView6).setOnClickListener(this)
        findViewById<View>(R.id.iconImageView7).setOnClickListener(this)
        findViewById<View>(R.id.iconImageView8).setOnClickListener(this)
        findViewById<View>(R.id.iconImageView9).setOnClickListener(this)
    }

    override fun onClick(view: View) {
        // Handle click events for each icon
        when (view.id) {
            R.id.iconImageView -> {
                // Handle click for iconImageView
                // Navigate to the target amount activity
                val intent = Intent(this, TargetAmountActivity::class.java)
                startActivity(intent)
            }
            R.id.iconImageView3 -> {
                // Handle click for iconImageView3
                // Navigate to the add item activity
                val intent = Intent(this, AddItemActivity::class.java)
                startActivity(intent)
            }
            R.id.iconImageView4 -> {
                // Handle click for iconImageView4
                // Navigate to the wishlist activity
                val intent = Intent(this, WishlistActivity::class.java)
                startActivity(intent)
            }
            R.id.iconImageView5 -> {
                // Handle click for iconImageView5
                // Navigate to the sell activity
                val intent = Intent(this, SellActivity::class.java)
                startActivity(intent)
            }
            R.id.iconImageView6 -> {
                // Handle click for iconImageView6
                // Navigate to the view by category activity
                val intent = Intent(this, ViewByCategoryActivity::class.java)
                startActivity(intent)
            }
            R.id.iconImageView7 -> {
                // Handle click for iconImageView7
                // Navigate to the view all items activity
                val intent = Intent(this, ViewAllItemActivity::class.java)
                startActivity(intent)
            }
            R.id.iconImageView8 -> {
                // Handle click for iconImageView8
                // Navigate to the change password activity
                val intent = Intent(this, AppInfoActivity::class.java)
                startActivity(intent)
            }
            R.id.iconImageView9 -> {
                // Handle click for iconImageView9
                // Navigate to the update profile activity
                val intent = Intent(this, UpdateProfileActivity::class.java)
                startActivity(intent)
            }
        }
    }
}
