package xyz.hyunto.rssreader

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*
import org.w3c.dom.Element
import org.w3c.dom.Node
import javax.xml.parsers.DocumentBuilderFactory

class MainActivity : AppCompatActivity() {
    private val netDispatcher = newSingleThreadContext(name = "ServiceCall")
    private val factory = DocumentBuilderFactory.newInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 개선방법 1) 비동기 호출자로 감싼 동기 함수
        // loadNews()를 launch 블록으로 감싸지 않으면 UI
        // 스레드에서 네트워크 요청이 일어나기 때문에 NetworksOnMainThreadException 이 발생한다.
        GlobalScope.launch(netDispatcher) {
            loadNews()
        }

        // 개선방법 2) 미리 정의된 디스패처를 갖는 비동기 함수
        // 코드는 간단해지지만 백그라운드 스레드(netDispatcher)에서 강제로 실행되기 때문에 유연성이 떨어진다
        // 추가로 함수 이름에서 비동기로 실행됨을 잘 나타내야 한다
        asyncLoadNews()

        // 개선방법 3) 유연한 디스패처를 가지는 비동기 함수
        asyncLoadNews(netDispatcher)
    }

    override fun onResume() {
        super.onResume()
        Thread.sleep(1000)
    }

    // 개선방법 1) 비동기 호출자로 감싼 동기 함수
    private fun loadNews() {
        val headlines = fetchRssHeadlines()
        val newsCount = findViewById<TextView>(R.id.newsCount)
        GlobalScope.launch(Dispatchers.Main) {
            newsCount.text = "Found ${headlines.size} News"
        }
    }

    // 개선방법 2) 미리 정의된 디스패처를 갖는 비동기 함수
    private fun asyncLoadNews() = GlobalScope.launch(netDispatcher) {
        val headlines = fetchRssHeadlines()
        val newsCount = findViewById<TextView>(R.id.newsCount)
        launch(Dispatchers.Main) {
            newsCount.text = "Found ${headlines.size} News"
        }
    }

    // 개선방법 3) 유연한 디스패처를 가지는 비동기 함수
    private fun asyncLoadNews(dispatcher: CoroutineDispatcher = netDispatcher) =
        GlobalScope.launch(dispatcher) {
            val headlines = fetchRssHeadlines()
            val newsCount = findViewById<TextView>(R.id.newsCount)
            launch(Dispatchers.Main) {
                newsCount.text = "Found ${headlines.size} News"
            }
        }

    private fun fetchRssHeadlines(): List<String> {
        val builder = factory.newDocumentBuilder()
        val xml = builder.parse("https://www.npr.org/rss/rss.php?id=1001")
        val news = xml.getElementsByTagName("channel").item(0)
        return (0 until news.childNodes.length)
            .asSequence()
            .map { news.childNodes.item(it) }
            .filter { Node.ELEMENT_NODE == it.nodeType }
            .map { it as Element }
            .filter { "item" == it.tagName }
            .map { it.getElementsByTagName("title").item(0).textContent }
            .toList()
    }
}