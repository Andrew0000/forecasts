package crocodile8.forecasts.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import crocodile8.forecasts.R
import kotlinx.android.synthetic.main.bookmaker_item.view.*

//TODO Improvement: Can be used any kind of delegates-based adapter

class BookmakersAdapter : RecyclerView.Adapter<BookmakersAdapter.ViewHolder>() {

    private var items = listOf<BookmakersItem>()

    fun setItems(newItems: List<BookmakersItem>) {
        items = newItems
        notifyDataSetChanged()
        //TODO Improvement: Use Diff utils if needed
    }

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.bookmaker_item,
                    parent,
                    false
                )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.apply {
            title.text = item.title
            rating.text = item.rating
            bottomText.text = item.bottomText
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.title
        val rating: TextView = view.rating
        val bottomText: TextView = view.bottomText
    }
}

