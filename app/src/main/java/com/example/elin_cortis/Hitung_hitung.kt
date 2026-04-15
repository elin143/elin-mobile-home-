package com.example.elin_cortis


import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import android.widget.EditText
import android.widget.TextView
import android.widget.Button
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.elin_cortis.databinding.ActivityHitungHitungBinding


class Hitung_hitung : AppCompatActivity() {
    private lateinit var binding: ActivityHitungHitungBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityHitungHitungBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val etAlas = findViewById<EditText>(R.id.etAlas)
        val etTinggi = findViewById<EditText>(R.id.etTinggi)
        val etSisi = findViewById<EditText>(R.id.etSisi)
        val tvHasil = findViewById<TextView>(R.id.tvHasil)

        val btnSegitiga = findViewById<Button>(R.id.btnSegitiga)
        val btnKubus = findViewById<Button>(R.id.btnKubus)

        val judul = intent.getStringExtra("judul")
        val deskripsi = intent.getStringExtra("deskripsi")

        binding.judul.text = "$judul"
        binding.deskrpsi.text = "$deskripsi"

        // Hitung Segitiga
        btnSegitiga.setOnClickListener {
            val alas = etAlas.text.toString().toDoubleOrNull()
            val tinggi = etTinggi.text.toString().toDoubleOrNull()

            if (alas != null && tinggi != null) {
                val hasil = 0.5 * alas * tinggi
                tvHasil.text = "Luas Segitiga: $hasil"

                Log.d("DEBUG", "Segitiga dihitung: $hasil")
            } else {
                Toast.makeText(this, "Input tidak valid", Toast.LENGTH_SHORT).show()
            }
        }

        // Hitung Kubus
        btnKubus.setOnClickListener {
            val sisi = etSisi.text.toString().toDoubleOrNull()

            if (sisi != null) {
                val hasil = sisi * sisi * sisi
                tvHasil.text = "Volume Kubus: $hasil"

                Log.d("DEBUG", "Kubus dihitung: $hasil")
            } else {
                Toast.makeText(this, "Input tidak valid", Toast.LENGTH_SHORT).show()
            }
        }
    }
}