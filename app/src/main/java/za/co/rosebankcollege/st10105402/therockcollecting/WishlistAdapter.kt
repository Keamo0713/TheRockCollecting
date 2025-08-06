package za.co.rosebankcollege.st10105402.therockcollecting

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import za.co.rosebankcollege.st10105402.therockcollecting.R

class WishlistAdapter(private val wishlistItems: List<WishlistItem>) :
    RecyclerView.Adapter<WishlistAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val stoneNameTextView: TextView = itemView.findViewById(R.id.stoneNameTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_wishlist, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = wishlistItems[position]
        holder.stoneNameTextView.text = item.stoneName
    }

    override fun getItemCount(): Int {
        return wishlistItems.size
    }
}
