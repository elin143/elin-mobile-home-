package com.example.elin_cortis

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.elin_cortis.databinding.ActivityWelcomeUserBinding

class Welcome_user : AppCompatActivity() {
    private lateinit var binding: ActivityWelcomeUserBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        var binding = ActivityWelcomeUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra("inputUsername")
        binding.welcomeuser.text = "Selamat datang $username"

        val judul = intent.getStringExtra("judul")
        val deskripsi = intent.getStringExtra("deskripsi")

        binding.judul.text = "$judul"
        binding.deskrpsi.text = "$deskripsi"


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}