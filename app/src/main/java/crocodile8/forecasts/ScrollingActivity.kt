package crocodile8.forecasts

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import kotlinx.android.synthetic.main.activity_scrolling.*

class ScrollingActivity : AppCompatActivity() {

    private val adapter = ForecastsFeedAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scrolling)
        setSupportActionBar(toolbar)
        toolbarLayout.title = title

        appBar.addOnOffsetChangedListener(OnOffsetChangedListener { barLayout, verticalOffset ->
            val expandFraction =
                ((verticalOffset.toFloat() / barLayout.totalScrollRange.toFloat()) + 1f)
                .coerceIn(0f, 1f)
            toolbarLayout.alpha = expandFraction
        })

        adapter.setItems(
            listOf(
                ForecastsFeedAdapter.Item.Bookmakers(listOf()),
                ForecastsFeedAdapter.Item.Card("1"),
                ForecastsFeedAdapter.Item.Card("2"),
                ForecastsFeedAdapter.Item.Card("3"),
                ForecastsFeedAdapter.Item.Card("4"),
                ForecastsFeedAdapter.Item.Card("5"),
                ForecastsFeedAdapter.Item.Card("6"),
                ForecastsFeedAdapter.Item.Card("7"),
                ForecastsFeedAdapter.Item.Card("8"),
            )
        )
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recyclerView.adapter = adapter
    }
}