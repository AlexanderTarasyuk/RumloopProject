package app.rss.ua.rsswsj.view.fragment.tabs

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
import app.rss.ua.rsswsj.R
import app.rss.ua.rsswsj.bundleKey
import app.rss.ua.rsswsj.fragmentKey
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_time.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * @author OTarasiuk
 * @since 13.08.2020
 */
class TimeFragment : Fragment() {

    private var sdf = SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.getDefault())

    private var currentDate = ""
    private lateinit var disposable: Disposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragmentResultListener(fragmentKey) { key, bundle ->
            val result = bundle.getString(bundleKey)
            tvLabel.text = result
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_time, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        updateCurrentTime()
    }

    private fun updateCurrentTime() {
        sdf.setTimeZone(TimeZone.getDefault())
        disposable = Observable.interval(0, 1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    currentDate = sdf.format(Date())
                    tvCurrentDate.text = getString(R.string.current_date, currentDate)
                }
    }

    override fun onDestroyView() {
        disposable.dispose()
        super.onDestroyView()
    }
}
