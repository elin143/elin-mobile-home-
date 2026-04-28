package pertemuan6

import android.os.Bundle
import android.view.MenuItem
import android.webkit.WebViewClient
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.elin_cortis.R
import com.example.elin_cortis.databinding.ActivityWebViewBinding

class WebViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWebViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = "Villagio"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // WebView pakai apply
        binding.webView.apply {
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true
            loadUrl("https://elin-inventaris.alwaysdata.net/") // ganti ke web kamu nanti
        }
    }

    // Back di toolbar
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            if (binding.webView.canGoBack()) {
                binding.webView.goBack()
            } else {
                finish()
            }
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    // Back tombol HP
    override fun onBackPressed() {
        if (binding.webView.canGoBack()) {
            binding.webView.goBack()
        } else {
            super.onBackPressed()
        }
    }
}