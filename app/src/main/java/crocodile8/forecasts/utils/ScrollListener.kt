package crocodile8.forecasts.utils

import androidx.recyclerview.widget.RecyclerView
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

class ScrollListener : RecyclerView.OnScrollListener() {

    private var yScroll = BehaviorSubject.createDefault(0f)

    fun observeY(): Observable<Float> = yScroll

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        yScroll.onNext(((yScroll.value ?: 0f) + dy).coerceAtLeast(0f))
    }
}