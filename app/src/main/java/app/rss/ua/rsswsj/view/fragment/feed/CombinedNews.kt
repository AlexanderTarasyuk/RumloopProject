package app.rss.ua.rsswsj.view.fragment.feed

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.rss.ua.rsswsj.manager.RssManager
import app.rss.ua.rsswsj.model.RssFeed
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
/**
 * @author OTarasiuk
 * @since 13.08.2020
 */
class CombinedNews : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    private var listFeed = MutableLiveData<List<RssFeed>>()
    private val isLoading = MutableLiveData<Boolean>()
    private val rssManager = RssManager()


    fun getNews() = listFeed
    fun getLoading() = isLoading

    fun loadNews() {
        rssManager.getNews()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext { isLoading.value = true }
                .subscribe(::success, ::error)
                .addTo(compositeDisposable)
    }

    fun loadMergedFeed() {
        rssManager.getMergedNews()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext { isLoading.value = true }
                .subscribe(::success, ::error)
                .addTo(compositeDisposable)
    }

    private fun success(list: List<RssFeed>) {
        listFeed.value = list
        isLoading.value = false
    }


    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }

}