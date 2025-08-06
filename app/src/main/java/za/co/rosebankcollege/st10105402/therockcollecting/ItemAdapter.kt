package za.co.rosebankcollege.st10105402.therockcollecting

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class ItemAdapter : RecyclerView.Adapter<ItemAdapter.ViewHolder>() {
    private var itemList: List<Item> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_card_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun setItems(items: List<Item>) {
        itemList = items
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.imageView)
        private val itemNameTextView: TextView = itemView.findViewById(R.id.mStoneName)
        private val purchaseDateTextView: TextView = itemView.findViewById(R.id.mPurchaseDate)
        private val stoneDetailsTextView: TextView = itemView.findViewById(R.id.mStoneDetails)
        private val categoryTextView: TextView = itemView.findViewById(R.id.mStoneCategory) // Add the category TextView

        fun bind(item: Item) {
            // Set the item data to the views
            // For example:
            itemNameTextView.text = item.itemName
            purchaseDateTextView.text = item.purchaseDate
            stoneDetailsTextView.text = item.description
            categoryTextView.text = item.category

            // Load the image using an image loading library like Glide or Picasso
            // For example:
            Glide.with(itemView)
                .load(item.imageUrl)
                .into(imageView)
        }
    }
}
