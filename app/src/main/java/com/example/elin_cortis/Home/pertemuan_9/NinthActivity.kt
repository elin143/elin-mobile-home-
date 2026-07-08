package com.example.elin_cortis.Home.pertemuan_9

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.SimpleAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.elin_cortis.R
import com.example.elin_cortis.databinding.ActivityNinthBinding
import com.example.elin_cortis.utils.NotificationHelper
import com.example.elin_cortis.utils.PermissionHelper
import com.example.elin_cortis.utils.ReminderHelper
import com.google.android.material.chip.Chip

class NinthActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNinthBinding

    private val notificationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            Toast.makeText(this, "✅ Izin notifikasi diberikan", Toast.LENGTH_SHORT).show()
            updatePermissionChip()
        } else {
            showNotificationPermissionRationale()
            updatePermissionChip()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityNinthBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setHomeAsUpIndicator(R.drawable.ic_home)
            title = "Pengingat Pemeliharaan"
            subtitle = "Jadwalkan alarm &amp; notifikasi aset"
            setDisplayHomeAsUpEnabled(true)
        }

        NotificationHelper.createNotificationChannels(this)
        updatePermissionChip()
        setupClickListeners()
        setupMaterialDesignDemos()
        setupListViewDemo()
    }

    override fun onResume() {
        super.onResume()
        updatePermissionChip()
    }

    private fun setupClickListeners() {
        binding.btnShowNotification.setOnClickListener {
            handleShowNotification()
        }

        binding.btnScheduleReminder.setOnClickListener {
            handleScheduleReminder()
        }

        binding.btnMenuCekAset.setOnClickListener {
            val intent = Intent(this, com.example.elin_cortis.Aset.AsetActivity::class.java)
            startActivity(intent)
        }

        binding.btnMenuTambah.setOnClickListener {
            val intent = Intent(this, com.example.elin_cortis.Aset.FormAsetActivity::class.java)
            startActivity(intent)
        }

        binding.btnMenuLaporan.setOnClickListener {
            val intent = Intent(this, com.example.elin_cortis.Aset.AsetActivity::class.java)
            startActivity(intent)
        }

        binding.btnMenuKategori.setOnClickListener {
            val intent = Intent(this, com.example.elin_cortis.Aset.AsetActivity::class.java)
            startActivity(intent)
        }

        binding.btnMenuPengingat.setOnClickListener {
            handleScheduleReminder()
        }

        binding.btnMenuBatalReminder.setOnClickListener {
            ReminderHelper.cancelAllReminders(this)
            Toast.makeText(this, "🔕 Pengingat dibatalkan", Toast.LENGTH_SHORT).show()
        }
    }

    // Material Design: ChipGroup Listener & TextInputLayout validation
    private fun setupMaterialDesignDemos() {
        // Priority selection listener
        binding.chipGroupPriority.setOnCheckedStateChangeListener { _, checkedIds ->
            val checkedId = checkedIds.firstOrNull()
            if (checkedId != null) {
                val chip = findViewById<Chip>(checkedId)
                Toast.makeText(this, "Prioritas dipilih: ${chip.text}", Toast.LENGTH_SHORT).show()
            }
        }

        // Form Submit button with error handling
        binding.btnSubmitLogQuick.setOnClickListener {
            val codeInput = binding.etLogCode.text.toString().trim()
            if (codeInput.isEmpty()) {
                binding.layoutEtLogCode.error = "Kode log tidak boleh kosong!"
            } else {
                binding.layoutEtLogCode.error = null
                val checkedId = binding.chipGroupPriority.checkedChipId
                val chip = findViewById<Chip>(checkedId)
                val priority = chip?.text ?: "Rendah"
                Toast.makeText(
                    this, 
                    "Log LOG-${codeInput}-2026 dikirim dengan prioritas $priority", 
                    Toast.LENGTH_LONG
                ).show()
                binding.etLogCode.text = null
            }
        }
    }

    // ListView Adapters Demos
    private fun setupListViewDemo() {
        // 1. ArrayAdapter Data Source
        val arrayData = listOf(
            "Gedung Balai Desa", 
            "Poskesdes Suka Makmur", 
            "PAUD Kenanga", 
            "Lapangan Olahraga Desa", 
            "Rumah Dinas Kepala Desa"
        )
        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayData)

        // 2. SimpleAdapter Data Source
        val simpleData = listOf(
            mapOf("nama" to "Mobil Ambulans Desa", "lokasi" to "Garasi Balai Desa"),
            mapOf("nama" to "Mesin Pencacah Sampah", "lokasi" to "TPS Lingkungan 2"),
            mapOf("nama" to "Traktor Tangan Tani", "lokasi" to "Gudang Kelompok Tani")
        )
        val fromKeys = arrayOf("nama", "lokasi")
        val toViews = intArrayOf(android.R.id.text1, android.R.id.text2)
        val simpleAdapter = SimpleAdapter(
            this, 
            simpleData, 
            android.R.layout.simple_list_item_2, 
            fromKeys, 
            toViews
        )

        // 3. CustomAdapter Data Source
        val customData = listOf(
            AssetDemoItem("Laptop Asus Staf Kaur", "Peralatan Kantor", "Baik", R.drawable.ic_home),
            AssetDemoItem("Genset Honda Balai Desa", "Mesin & Genset", "Rusak Ringan", R.drawable.ic_settings),
            AssetDemoItem("Tenda Posko Bencana", "Fasilitas Darurat", "Baik", R.drawable.ic_info)
        )
        val customAdapter = AssetDemoAdapter(this, customData)

        // Set default mode (ArrayAdapter)
        binding.listViewDemo.adapter = arrayAdapter

        // Switch ListView adapter modes on chip selection
        binding.chipGroupAdapterMode.setOnCheckedStateChangeListener { _, checkedIds ->
            val checkedId = checkedIds.firstOrNull()
            if (checkedId != null) {
                when (checkedId) {
                    R.id.chipModeArrayAdapter -> {
                        binding.listViewDemo.adapter = arrayAdapter
                    }
                    R.id.chipModeSimpleAdapter -> {
                        binding.listViewDemo.adapter = simpleAdapter
                    }
                    R.id.chipModeCustomAdapter -> {
                        binding.listViewDemo.adapter = customAdapter
                    }
                }
            }
        }
    }

    private fun handleShowNotification() {
        if (!PermissionHelper.isNotificationPermissionGranted(this)) {
            requestNotificationPermission()
            return
        }

        NotificationHelper.showNotification(
            context = this,
            title = "📋 Pemeliharaan Aset Rutin",
            message = "Jadwal pengecekan berkala aset desa telah tiba. Pastikan kondisi sarana prasarana terpantau dengan baik.",
            notificationId = 9001
        )
        Toast.makeText(this, "✅ Notifikasi dikirim!", Toast.LENGTH_SHORT).show()
    }

    private fun handleScheduleReminder() {
        if (!PermissionHelper.isNotificationPermissionGranted(this)) {
            requestNotificationPermission()
            return
        }

        if (!PermissionHelper.isExactAlarmPermissionGranted(this)) {
            PermissionHelper.showExactAlarmRationale(this) { }
            return
        }

        ReminderHelper.scheduleReminderInSeconds(
            context = this,
            title = "🔔 Pengecekan Rutin Aset Desa",
            message = "Jangan lupa untuk mencatat kondisi terbaru sarana prasarana desa.",
            delaySeconds = 10
        )
        binding.tvReminderHint.text = "✅ Pengingat akan muncul dalam 10 detik"
    }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                showNotificationPermissionRationale()
            } else {
                notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    private fun showNotificationPermissionRationale() {
        AlertDialog.Builder(this)
            .setTitle("Izin Notifikasi Diperlukan")
            .setMessage("Villagio memerlukan izin notifikasi untuk mengirimkan alarm pengecekan aset.")
            .setPositiveButton("Buka Pengaturan") { _, _ ->
                PermissionHelper.openAppSettings(this)
            }
            .setNegativeButton("Nanti", null)
            .show()
    }

    private fun updatePermissionChip() {
        val isGranted = PermissionHelper.isNotificationPermissionGranted(this)
        val isAlarmGranted = PermissionHelper.isExactAlarmPermissionGranted(this)

        when {
            !isGranted -> {
                binding.chipPermissionStatus.text = "⚠️ Izin Off"
                binding.chipPermissionStatus.chipBackgroundColor =
                    ContextCompat.getColorStateList(this, R.color.sage_error_container)
                binding.chipPermissionStatus.setOnClickListener {
                    requestNotificationPermission()
                }
            }
            !isAlarmGranted -> {
                binding.chipPermissionStatus.text = "⏰ Alarm Off"
                binding.chipPermissionStatus.chipBackgroundColor =
                    ContextCompat.getColorStateList(this, R.color.sage_secondary_container)
                binding.chipPermissionStatus.setOnClickListener {
                    PermissionHelper.showExactAlarmRationale(this)
                }
            }
            else -> {
                binding.chipPermissionStatus.text = "✅ Izin OK"
                binding.chipPermissionStatus.chipBackgroundColor =
                    ContextCompat.getColorStateList(this, R.color.sage_primary_container)
                binding.chipPermissionStatus.setOnClickListener(null)
            }
        }
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

    // Custom Data Model Class for ListView
    data class AssetDemoItem(val name: String, val category: String, val status: String, val iconRes: Int)

    // Custom ListView Adapter
    private class AssetDemoAdapter(
        private val context: Context, 
        private val data: List<AssetDemoItem>
    ) : BaseAdapter() {

        override fun getCount(): Int = data.size
        override fun getItem(position: Int): Any = data[position]
        override fun getItemId(position: Int): Long = position.toLong()

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val view = convertView ?: LayoutInflater.from(context).inflate(
                R.layout.item_asset_demo, 
                parent, 
                false
            )
            val item = data[position]

            val tvName = view.findViewById<TextView>(R.id.tvDemoName)
            val tvCategory = view.findViewById<TextView>(R.id.tvDemoCategory)
            val tvStatus = view.findViewById<TextView>(R.id.tvDemoStatus)
            val imgIcon = view.findViewById<ImageView>(R.id.imgDemoIcon)

            tvName.text = item.name
            tvCategory.text = item.category
            tvStatus.text = item.status
            imgIcon.setImageResource(item.iconRes)

            return view
        }
    }
}
