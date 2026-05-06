package com.example.elin_cortis

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.elin_cortis.databinding.ActivityOtpactivityBinding
import com.google.android.material.snackbar.Snackbar
import pertemuan6.MainActivity

class OTPActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOtpactivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityOtpactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //OTP
        binding.submitOTP.setOnClickListener {
            val input_no_hp = intent.getStringExtra("nomorHp")

            if (binding.verifikasi.text.toString() == input_no_hp) {
                intent = Intent(this, MainActivity::class.java)
                startActivity(intent)

            } else {
                Snackbar.make(binding.root, "password salah", Snackbar.LENGTH_SHORT)
                    .show()
                intent
            }
        }
    }
}