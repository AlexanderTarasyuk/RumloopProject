package app.rss.ua.rsswsj.view.activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.MenuItem
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import app.rss.ua.rsswsj.KEY_RSS_URL
import app.rss.ua.rsswsj.R
import kotlinx.android.synthetic.main.activity_news.*

/**
 * @author OTarasiuk
 * @since 13.08.2020
 */
class WSJActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)
        initViews()
    }

    private fun initViews() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val url = intent.getStringExtra(KEY_RSS_URL)
        webView.settings.javaScriptEnabled = true
        webView.loadUrl(url)
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView,  request: WebResourceRequest): Boolean {
                view.loadUrl(request.url.toString())
                return false
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            finish()
        }
        return false
    }
}
