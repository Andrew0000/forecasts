package crocodile8.forecasts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.forecast_feed_item.view.*

//TODO Improvement: Can be used any kind of delegates-based adapter

class BookmakersAdapter : RecyclerView.Adapter<BookmakersAdapter.ViewHolder>() {

    private var items = listOf<Item>()

    fun setItems(newItems: List<Item>) {
        items = newItems
        notifyDataSetChanged()
        //TODO Improvement: Use Diff utils if needed
    }

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.forecast_feed_item, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.titleTextView.text = item.title
    }

    data class Item(
        val title: String
    )

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val titleTextView: TextView = view.feedItemTitleTextView
    }
}