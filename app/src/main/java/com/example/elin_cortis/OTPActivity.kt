package com.example.elin_cortis

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Toast
import com.example.elin_cortis.Home.pertemuan6.AuthActivity
import com.example.elin_cortis.databinding.ActivityOtpactivityBinding
import com.google.android.material.snackbar.Snackbar

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

        val sharedPref = getSharedPreferences("user_pref", MODE_PRIVATE)


        //OTP
        binding.submitOTP.setOnClickListener {
            val input_no_hp = binding.verifikasi.text.toString()
            val reg_no_hp = sharedPref.getString("no_hp", "")

            if (reg_no_hp == input_no_hp) {
                Toast.makeText(this, "Registrasi Berhasil! Silakan masuk.", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, AuthActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            } else {
                Snackbar.make(binding.root, "Kode OTP salah", Snackbar.LENGTH_SHORT)
                    .show()
            }
        }
    }
}