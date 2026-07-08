package com.example.elin_cortis

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.elin_cortis.databinding.ActivityHitungHitungBinding
import java.text.NumberFormat
import java.util.Locale

class Hitung_hitung : AppCompatActivity() {
    private lateinit var binding: ActivityHitungHitungBinding

    private val categories = listOf(
        "Elektronik / Komputer",
        "Peralatan Kantor / Furniture",
        "Mesin / Alat Pertanian",
        "Kendaraan Operasional",
        "Gedung / Bangunan Desa"
    )

    // Standard maintenance cost recommendation per category
    private val standardMaintenanceCosts = listOf(150000, 50000, 200000, 500000, 1000000)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityHitungHitungBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Setup Toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = "Kalkulator Anggaran Aset"
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        // Setup Spinner
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerKategori.adapter = adapter

        // Spinner Selection Listener to auto-fill recommended maintenance cost
        binding.spinnerKategori.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val recommendedCost = standardMaintenanceCosts[position]
                binding.etBiayaPerawatan.setText(recommendedCost.toString())
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        // Calculate Button Action
        binding.btnHitung.setOnClickListener {
            calculateBudget()
        }
    }

    private fun calculateBudget() {
        val hargaSatuanStr = binding.etHargaSatuan.text.toString().trim()
        val jumlahStr = binding.etJumlah.text.toString().trim()
        val biayaPerawatanStr = binding.etBiayaPerawatan.text.toString().trim()

        if (hargaSatuanStr.isEmpty() || jumlahStr.isEmpty() || biayaPerawatanStr.isEmpty()) {
            Toast.makeText(this, "Mohon lengkapi semua input data!", Toast.LENGTH_SHORT).show()
            return
        }

        val hargaSatuan = hargaSatuanStr.toDoubleOrNull() ?: 0.0
        val jumlah = jumlahStr.toDoubleOrNull() ?: 0.0
        val biayaPerawatan = biayaPerawatanStr.toDoubleOrNull() ?: 0.0

        if (hargaSatuan <= 0) {
            binding.tilHargaSatuan.error = "Harga satuan harus lebih dari 0"
            return
        } else {
            binding.tilHargaSatuan.error = null
        }

        if (jumlah <= 0) {
            binding.tilJumlah.error = "Jumlah unit harus lebih dari 0"
            return
        } else {
            binding.tilJumlah.error = null
        }

        if (biayaPerawatan < 0) {
            binding.tilBiayaPerawatan.error = "Biaya perawatan tidak boleh kurang dari 0"
            return
        } else {
            binding.tilBiayaPerawatan.error = null
        }

        // Simple Calculations
        val totalPengadaan = hargaSatuan * jumlah
        val totalPerawatan = biayaPerawatan * jumlah
        val grandTotal = totalPengadaan + totalPerawatan

        // Format Currency (Indonesian Rupiah)
        val localeId = Locale.forLanguageTag("id-ID")
        val currencyFormatter = NumberFormat.getCurrencyInstance(localeId).apply {
            maximumFractionDigits = 0
        }

        binding.tvTotalPengadaan.text = currencyFormatter.format(totalPengadaan)
        binding.tvTotalPerawatan.text = currencyFormatter.format(totalPerawatan)
        binding.tvTotalAnggaran.text = currencyFormatter.format(grandTotal)

        // Simple budget classification and recommendation
        val rekomendasiTitle: String
        val rekomendasiContent: String
        val cardColorRes: Int
        val textColorRes: Int

        when {
            grandTotal < 5000000 -> {
                rekomendasiTitle = "🟢 ANGGARAN OPERASIONAL HARIAN (KAS KECIL)"
                rekomendasiContent = "Estimasi anggaran relatif kecil. Pengadaan dan pemeliharaan aset dapat dibiayai menggunakan anggaran operasional harian / kas kecil kantor desa tanpa memerlukan prosedur birokrasi yang panjang."
                cardColorRes = R.color.sage_primary_container
                textColorRes = R.color.sage_on_primary_container
            }
            grandTotal <= 50000000 -> {
                rekomendasiTitle = "🟡 PERSETUJUAN KAUR KEUANGAN DESA"
                rekomendasiContent = "Estimasi anggaran tingkat menengah. Diperlukan koordinasi dan persetujuan dari Kepala Urusan (Kaur) Keuangan Desa untuk alokasi dana dari pos anggaran belanja barang operasional berjalan."
                cardColorRes = R.color.sage_secondary_container
                textColorRes = R.color.sage_on_secondary_container
            }
            else -> {
                rekomendasiTitle = "🚨 PENGAJUAN RENCANA ANGGARAN BIAYA (RAB) APBDESA"
                rekomendasiContent = "Estimasi anggaran besar. Pengadaan aset ini wajib masuk ke dalam pembahasan Musyawarah Desa (Musdes), dituangkan dalam dokumen RAB formal, dan dianggarkan dalam APBDesa tahunan."
                cardColorRes = R.color.sage_error_container
                textColorRes = R.color.sage_on_error_container
            }
        }

        binding.tvRekomendasiTitle.text = rekomendasiTitle
        binding.tvRekomendasiContent.text = rekomendasiContent
        binding.cardRekomendasi.setCardBackgroundColor(ContextCompat.getColor(this, cardColorRes))
        binding.tvRekomendasiTitle.setTextColor(ContextCompat.getColor(this, textColorRes))
        binding.tvRekomendasiContent.setTextColor(ContextCompat.getColor(this, textColorRes))

        // Show Results Card
        binding.cardHasil.visibility = View.VISIBLE
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressedDispatcher.onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}