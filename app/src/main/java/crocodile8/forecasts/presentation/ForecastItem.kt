package crocodile8.forecasts.presentation

sealed class ForecastItem(val type: Int) {

    data class Card(
        val title: String,
        val time: String,
        val who: String,
        val coefficient: String,
        val belowWho: String,
        val description: String,
        val authorName: String,
        val authorROI: String,
        val repeat: String
    ) : ForecastItem(
        ForecastsFeedAdapter.TYPE_CARD
    )

    data class Bookmakers(
        val items: List<Any>
    ) : ForecastItem(
        ForecastsFeedAdapter.TYPE_BOOKMAKERS
    )
}