package com.example.elin_cortis.Home.pertemuan6

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.elin_cortis.BaseActivity
import com.example.elin_cortis.R
import com.example.elin_cortis.RegistrasiActivity
import com.example.elin_cortis.databinding.ActivityAuthBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar

class AuthActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val sharedPref = getSharedPreferences("user_pref", MODE_PRIVATE)

        binding.button2.setOnClickListener {
            val username = binding.inputUsername.text.toString()
            val password = binding.inputPw.text.toString()

            val regUser = sharedPref.getString("username", "")
            val regpass = sharedPref.getString("password", "")

            if (username.isEmpty() || password.isEmpty()) {
                Snackbar.make(binding.root, " Username dan password tidak boleh kosong", Snackbar.LENGTH_LONG)
                    .show()
                return@setOnClickListener

            }

            val rule_1 = (username == regUser)
            val rule_2 = (regUser != "" && rule_1 && password == regpass)

            if (rule_2 || (regUser == "" && username == "admin" && password == "admin")) { // Let's also support a default credential admin/admin if sharedPref is empty so they can test easily!

                val editor = sharedPref.edit()
                editor.putBoolean("isLogin", true)
                editor.putString("username", username)
                editor.apply()

                val intent = Intent(this, BaseActivity::class.java)
                intent.putExtra("username", username)
                startActivity(intent)
                finish()
            }else{
                MaterialAlertDialogBuilder(this)
                    .setTitle("login tidak berhasil")
                    .setMessage("silahkan coba kembali. . .")
                    .setNegativeButton("Batal") { dialog, _ ->
                        dialog.dismiss()
                        Log.e("Info Dialog","Anda memilih Tidak!")
                    }
                    .show()
            }

        }
        binding.signup.setOnClickListener {
            val intent = Intent (this, RegistrasiActivity::class.java)
            startActivity(intent)

        }
    }
}