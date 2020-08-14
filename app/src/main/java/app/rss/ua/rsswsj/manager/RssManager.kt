package app.rss.ua.rsswsj.manager

import android.util.Xml
import app.rss.ua.rsswsj.UPDATE_PERIOD
import app.rss.ua.rsswsj.lifeStyleUrl
import app.rss.ua.rsswsj.model.RssFeed
import app.rss.ua.rsswsj.worldNewsUrl
import app.rss.ua.rsswsj.wsjNewsUrl
import io.reactivex.Observable
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import java.io.IOException
import java.net.URL
import java.util.concurrent.TimeUnit

/**
 * @author OTarasiuk
 * @since 13.08.2020
 */
class RssManager {

        fun getNews() =
        Observable.interval(0, UPDATE_PERIOD, TimeUnit.SECONDS)
                .flatMap { parseNews(wsjNewsUrl) }

    fun getMergedNews() = Observable.interval(0, UPDATE_PERIOD, TimeUnit.SECONDS)
            .flatMap { parseNews(lifeStyleUrl) }
            .map { val list2 = parseNews(worldNewsUrl).blockingFirst()
                val list3 = ArrayList<RssFeed>()
                list3.addAll(it)
                list3.addAll(list2)
                list3
            }


    @Throws(XmlPullParserException::class, IOException::class)
    fun parseNews(url: URL): Observable<List<RssFeed>> {
        return Observable.create {emitter ->
            val inputStream = url.openConnection().getInputStream()

            var title = ""
            var link = ""
            var description = ""
            var date = ""
            var isItem = false
            val items = mutableListOf<RssFeed>()

            try {
                val xmlPullParser = Xml.newPullParser()
                xmlPullParser.setInput(inputStream, null)

                xmlPullParser.nextTag()
                while (xmlPullParser.next() != XmlPullParser.END_DOCUMENT) {
                    val eventType = xmlPullParser.eventType

                    val name = xmlPullParser.name ?: continue

                    if (eventType == XmlPullParser.START_TAG) {
                        if (name.equals("item", ignoreCase = true)) {
                            isItem = true
                            continue
                        }
                    }

                    else if (eventType == XmlPullParser.END_TAG) {
                        if (name.equals("item", ignoreCase = true)) {
                            isItem = false
                        }
                        continue
                    }

                    var result = ""
                    if (xmlPullParser.next() == XmlPullParser.TEXT) {
                        result = xmlPullParser.text
                        xmlPullParser.nextTag()
                    }

                    if (name.equals("title", ignoreCase = true)) {
                        title = result
                    } else if (name.equals("link", ignoreCase = true)) {
                        link = result
                    } else if (name.equals("description", ignoreCase = true)) {
                        description = result.substringBefore("<div class")
                    }
                    else if (name.equals("pubDate", ignoreCase = true)) {
                        date = result
                    }

                    if (title.isNotEmpty() && description.isNotEmpty() && link.isNotEmpty() && date.isNotEmpty()) {
                        if (isItem) {
                            val item = RssFeed(title, description, date, link)
                            items.add(item)
                        }

                        title = ""
                        link = ""
                        description = ""
                        date = ""
                        isItem = false
                    }
                }

                emitter.onNext(items)
            }
            finally {
                inputStream.close()
            }
        }
    }
}