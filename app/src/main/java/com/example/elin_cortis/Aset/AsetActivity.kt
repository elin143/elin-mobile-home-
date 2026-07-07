package com.example.elin_cortis.Aset

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.elin_cortis.R
import com.example.elin_cortis.data.AppDatabase
import com.example.elin_cortis.data.model.entity.AsetEntity
import com.example.elin_cortis.data.model.entity.LogEntity
import com.example.elin_cortis.databinding.ActivityAsetBinding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AsetActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAsetBinding
    private lateinit var adapter: AsetAdapter
    private lateinit var db: AppDatabase
    private val asetList = mutableListOf< AsetEntity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAsetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup Toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Data Aset"
        binding.toolbar.setNavigationOnClickListener { finish() }

        db = AppDatabase.getInstance(this)

        setupRecyclerView()

        binding.fabAddAset.setOnClickListener {
            startActivity(Intent(this, FormAsetActivity::class.java))
        }
    }

    private fun setupRecyclerView() {
        adapter = AsetAdapter(asetList) { item ->
            deleteAset(item)
        }
        binding.rvAset.layoutManager = LinearLayoutManager(this)
        binding.rvAset.adapter = adapter
        binding.rvAset.addItemDecoration(
            DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        )
    }

    override fun onResume() {
        super.onResume()
        fetchAset()
    }

    private fun fetchAset() {
        lifecycleScope.launch {
            val data = db.AsetDao().getAll()
            asetList.clear()
            asetList.addAll(data)
            adapter.notifyDataSetChanged()
        }
    }

    fun deleteAset(aset: AsetEntity) {
        lifecycleScope.launch {
            // 1. Hapus Data Aset
            db.AsetDao().delete(aset)

            // 2. CATAT LOG OTOMATIS (Penghapusan)
            val currentTime = SimpleDateFormat(
                "dd MMM yyyy, HH:mm",
                Locale.getDefault()
            ).format(Date())

            val logEntry = LogEntity(
                title = "Penghapusan Data Aset",
                description = "Data Aset ${aset.kode_aset} dengan nama ${aset.nama_aset} telah dihapus.",
                category = "Aset",
                timestamp = currentTime
            )

            db.LogDao().insertLog(logEntry)

            fetchAset()
        }
    }
}