package com.example.elin_cortis

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.elin_cortis.databinding.ActivityRegistrasiBinding
import com.google.android.material.snackbar.Snackbar

class RegistrasiActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegistrasiBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRegistrasiBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.submit.setOnClickListener {
            val input_no_hp = binding.nomorHp.text.toString()
            val nama = binding.nama.text.toString()
            val username = binding.username.text.toString()
            val password = binding.Password.text.toString()

            var errorMessage: String? = null
            if (nama.isEmpty()) {
                errorMessage = "Nama tidak boleh kosong"
            } else if (username.isEmpty()) {
                errorMessage = "Username tidak boleh kosong"
            } else if (input_no_hp.isEmpty()) {
                errorMessage = "Nomor HP tidak boleh kosong"
            } else if (password.isEmpty()) {
                errorMessage = "Password tidak boleh kosong"
            }

            if (errorMessage != null) {
                Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_LONG).show()
            } else {
                val sharedPref = getSharedPreferences("user_pref", MODE_PRIVATE)
                val editor = sharedPref.edit()

                editor.putString("name", nama)
                editor.putString("username", username)
                editor.putString("no_hp", input_no_hp)
                editor.putString("password", password)
                editor.apply()

                val intent = Intent(this, OTPActivity::class.java)
                intent.putExtra("evelin", input_no_hp)
                startActivity(intent)
            }
        }

        binding.btnBackToLogin.setOnClickListener {
            finish()
        }
    }
}
