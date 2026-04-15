package com.example.elin_cortis

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.elin_cortis.databinding.ActivityHalamanLoginBinding

class Halaman_login : AppCompatActivity() {
    private lateinit var binding: ActivityHalamanLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityHalamanLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var inputUsername = binding.etUsername.text.toString()

        binding.btnLogin.setOnClickListener {

            val intent = Intent(this, halaman_utama::class.java)
            intent.putExtra("inputUsername", binding.etUsername.text.toString())
            startActivity(intent)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}