package crocodile8.forecasts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.forecast_feed_item.view.*
import java.lang.IllegalArgumentException

//TODO Improvement: Use any kind of delegates-based adapter

class ForecastsFeedAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        val TYPE_CARD = 1
        val TYPE_BOOKMAKERS = 2
    }

    private var items = listOf<Item>()

    fun setItems(newItems: List<Item>) {
        items = newItems
        notifyDataSetChanged()
        //TODO Improvement: Use Diff utils if needed
    }

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int = items[position].type

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TYPE_CARD -> CardVH(inflater.inflate(R.layout.forecast_feed_item, parent, false))
            TYPE_BOOKMAKERS -> BookmakersVH(inflater.inflate(R.layout.forecast_bookmakers_item, parent, false))
            else -> throw IllegalArgumentException()
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        when (holder) {
            is CardVH -> {
                item as Item.Card
                holder.titleTextView.text = item.title
            }
            is BookmakersVH -> {
                item as Item.Bookmakers

            }
        }
    }

    sealed class Item(val type: Int) {

        data class Card(
            val title: String
        ) : Item(TYPE_CARD)

        data class Bookmakers(
            val items: List<Any>
        ) : Item(TYPE_BOOKMAKERS)
    }

    class CardVH(view: View) : RecyclerView.ViewHolder(view) {
        val titleTextView: TextView = view.feedItemTitleTextView
    }

    class BookmakersVH(view: View) : RecyclerView.ViewHolder(view)
}