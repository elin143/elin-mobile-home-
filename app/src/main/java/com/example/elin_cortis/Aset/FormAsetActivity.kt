package com.example.elin_cortis.Aset

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.elin_cortis.data.AppDatabase
import com.example.elin_cortis.data.model.entity.AsetEntity
import com.example.elin_cortis.data.model.entity.LogEntity
import com.example.elin_cortis.databinding.ActivityFormAsetBinding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class FormAsetActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFormAsetBinding
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormAsetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = AppDatabase.getInstance(this)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

        binding.btnSaveNote.setOnClickListener {
            saveData()
        }
    }

    private fun saveData() {
        val kodeAset = binding.namaObjek.text.toString()
        val namaAset = binding.namaPemilik.text.toString()
        val kategoriAset = binding.luasTanah.text.toString()
        val lokasiAset = binding.alamat.text.toString()
        val kondisiAset = binding.JenisTanah.text.toString()

        if (kodeAset.isEmpty() || namaAset.isEmpty()) {
            Toast.makeText(this, "Kode Aset dan Nama Aset wajib diisi!", Toast.LENGTH_SHORT).show()
            return
        }

        lifecycleScope.launch {
            // 1. Simpan Data Aset
            val aset = AsetEntity(
                kode_aset = kodeAset,
                nama_aset = namaAset,
                kategori_aset = kategoriAset,
                lokasi_aset = lokasiAset,
                kondisi_aset = kondisiAset
            )
            db.AsetDao().insert(aset)

            // 2. CATAT LOG OTOMATIS
            val currentTime = SimpleDateFormat(
                "dd MMM yyyy, HH:mm",
                Locale.getDefault()
            ).format(Date())

            val logEntry = LogEntity(
                title = "Penambahan Aset Baru",
                description = "Aset $kodeAset dengan nama $namaAset berhasil ditambahkan.",
                category = "Aset",
                timestamp = currentTime
            )

            db.LogDao().insertLog(logEntry)

            Toast.makeText(
                this@FormAsetActivity,
                "Data Aset Berhasil Disimpan",
                Toast.LENGTH_SHORT
            ).show()

            finish()
        }
    }
}
