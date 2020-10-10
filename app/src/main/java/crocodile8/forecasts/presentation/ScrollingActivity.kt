package crocodile8.forecasts.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import crocodile8.forecasts.R
import crocodile8.forecasts.presentation.forecasts.ForecastsFeedAdapter
import crocodile8.forecasts.utils.ScrollListener
import crocodile8.forecasts.utils.subscribeDefault
import kotlinx.android.synthetic.main.activity_scrolling.*

class ScrollingActivity : AppCompatActivity() {

    private val adapter = ForecastsFeedAdapter(
        onRepeatClick = { viewModel.onRepeatClick(it) }
    )
    private val scrollListener = ScrollListener()
    private val viewModel: ScrollingActivityVM by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scrolling)
        setupAppBarCollapsing()
        setupMainRecycler()
        observeViewModel()
    }

    // Use own implementation instead of default CollapsingToolbar
    // for more control
    private fun setupAppBarCollapsing() {
        val appBarMaxHeight = resources.getDimensionPixelSize(R.dimen.app_bar_max_height)
        val appBarMinHeight = resources.getDimensionPixelSize(R.dimen.app_bar_min_height)
        val workHeight = appBarMaxHeight - appBarMinHeight
        scrollListener.observeY()
            .subscribeDefault { yScroll ->
                val expandFraction = (1f - (yScroll / workHeight)).coerceIn(0f, 1f)
                mainTitleTextView.alpha = expandFraction
                val appBarHeight = (appBarMinHeight + workHeight * expandFraction).toInt()
                if (appBarHeight != appBar.height) {
                    appBar.updateLayoutParams {
                        height = appBarHeight
                    }
                }
            }
    }

    private fun setupMainRecycler() {
        val linearLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = adapter
        recyclerView.addOnScrollListener(scrollListener)
    }

    private fun observeViewModel() {
        viewModel.getForecasts().observe(this, Observer {
            adapter.setItems(it)
        })
        viewModel.getProgressBarVisible().observe(this, Observer {
            progressBar.isVisible = it
        })
    }
}
