package crocodile8.forecasts.data

/**
 * Created by Andrei Riik in 2020.
 */

data class Forecast(
    val title: String,
    val time: String,
    val who: String,
    val coefficient: String,
    val belowWho: String,
    val description: String,
    val authorName: String,
    val authorROI: String,
    val repeat: String
)