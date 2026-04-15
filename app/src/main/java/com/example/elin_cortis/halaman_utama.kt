package com.example.elin_cortis

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.elin_cortis.databinding.ActivityHalamanUtamaBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import kotlin.jvm.java

class halaman_utama : AppCompatActivity() {
    private lateinit var binding: ActivityHalamanUtamaBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityHalamanUtamaBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnhitung.setOnClickListener {

            val intent = Intent(this, Hitung_hitung::class.java)
            intent.putExtra("judul", binding.judul.text.toString())
            intent.putExtra("deskripsi", binding.deskrpsi.text.toString())
            startActivity(intent)
        }

        binding.buttonWelcome.setOnClickListener {

            val intent = Intent(this, Welcome_user::class.java)
            intent.putExtra("judul", binding.judul.text.toString())
            intent.putExtra("deskripsi", binding.deskrpsi.text.toString())
            startActivity(intent)
        }

        binding.btncustom.setOnClickListener {

            val intent = Intent(this, custom::class.java)
            intent.putExtra("judul", binding.judul.text.toString())
            intent.putExtra("deskripsi", binding.deskrpsi.text.toString())
            startActivity(intent)
        }

        binding.buttonLogout.setOnClickListener {

            MaterialAlertDialogBuilder(this)
                .setTitle("Konfirmasi")
                .setMessage("Apakah anda yakkult untuk logout?")
                .setPositiveButton("Iya") { dialog, _ ->
                     dialog.dismiss()
                    Log.e("Info Login", "Login berhasil ")

                    val intent = Intent(this, Halaman_login::class.java)
                    startActivity(intent)
                }

                .setNegativeButton("Tidak") {dialog, _ ->
                    dialog.dismiss()
                    Log.e("Info login", "user tidak jadi login")

                    Snackbar.make(binding.root, "Logout Dibatalkan", Snackbar.LENGTH_INDEFINITE)
                        .setAction("tutup") {
                            Log.e("Infor Snackbar", "snackbar logout ditutup")
                        }
                        .show()
        }
                .show()
        }
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets


        }
    }
}