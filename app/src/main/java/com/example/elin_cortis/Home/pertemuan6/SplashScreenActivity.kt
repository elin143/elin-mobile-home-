package com.example.elin_cortis.Home.pertemuan6

import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.elin_cortis.BaseActivity
import com.example.elin_cortis.R
import com.example.elin_cortis.databinding.ActivitySplashScreenBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Start beautiful fade-in animations
        val logoAnim = AnimationUtils.loadAnimation(this, R.anim.splash_fade_in)
        val textAnim = AnimationUtils.loadAnimation(this, R.anim.splash_text_fade_in)
        val subtitleAnim = AnimationUtils.loadAnimation(this, R.anim.splash_subtitle_fade_in)

        binding.cardLogo.startAnimation(logoAnim)
        binding.txtAppName.startAnimation(textAnim)
        binding.txtSubtitle.startAnimation(subtitleAnim)
        binding.txtTagline.startAnimation(subtitleAnim)
        binding.txtLoading.startAnimation(subtitleAnim)
        binding.txtVersion.startAnimation(subtitleAnim)

        val sharedPref = getSharedPreferences("user_pref", MODE_PRIVATE)

        // Kondisi jika isLogin bernilai true
        val isLogin = sharedPref.getBoolean("isLogin", false)
        if (isLogin) {
            lifecycleScope.launch {
                delay(1800)
                val intent = Intent(this@SplashScreenActivity, BaseActivity::class.java)
                startActivity(intent)
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                finish()
            }
            return
        }

        lifecycleScope.launch {
            delay(2200) // cukup waktu untuk animasi selesai
            val intent = Intent(this@SplashScreenActivity, AuthActivity::class.java)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }
    }
}