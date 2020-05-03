package polygonull.hextatic

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView


class MainActivity : AppCompatActivity() {

    private lateinit var webView: WebView


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        webView = findViewById(R.id.activity_webview)

        webView.setOnLongClickListener({true})
        webView.setLongClickable(false)
        webView.setHapticFeedbackEnabled(false)

        val webSettings = webView.getSettings()
        webSettings.setJavaScriptEnabled(true)
        webSettings.setDomStorageEnabled(true)


        webView.loadUrl("file:///android_asset/hextatic/index.html")
    }

}
