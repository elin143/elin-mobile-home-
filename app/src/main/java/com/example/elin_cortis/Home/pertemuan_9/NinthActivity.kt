package com.example.elin_cortis.Home.pertemuan_9

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
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

class NinthActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNinthBinding

    /** Launcher untuk meminta izin notifikasi (Android 13+) */
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

        // -- Toolbar --
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setHomeAsUpIndicator(R.drawable.ic_home)
            title = "Villagio"
            subtitle = "Inventaris Aset Desa"
            setDisplayHomeAsUpEnabled(true)
        }

        // -- Inisialisasi channel notifikasi --
        NotificationHelper.createNotificationChannels(this)

        // -- Update status chip izin --
        updatePermissionChip()

        // -- Setup Click Listeners --
        setupClickListeners()
    }

    override fun onResume() {
        super.onResume()
        updatePermissionChip()
    }

    /**
     * Mendaftarkan semua click listener pada tombol-tombol di layout.
     */
    private fun setupClickListeners() {
        // 🔔 Tombol Notifikasi Utama
        binding.btnShowNotification.setOnClickListener {
            handleShowNotification()
        }

        // ⏰ Tombol Pengingat
        binding.btnScheduleReminder.setOnClickListener {
            handleScheduleReminder()
        }

        // 📋 Cek Aset
        binding.btnMenuCekAset.setOnClickListener {
            Toast.makeText(this, "📋 Mengecek daftar aset desa...", Toast.LENGTH_SHORT).show()
        }

        // ➕ Tambah Aset
        binding.btnMenuTambah.setOnClickListener {
            Toast.makeText(this, "➕ Membuka form tambah aset...", Toast.LENGTH_SHORT).show()
        }

        // 📊 Laporan
        binding.btnMenuLaporan.setOnClickListener {
            Toast.makeText(this, "📊 Membuka laporan aset desa...", Toast.LENGTH_SHORT).show()
        }

        // 🏷️ Kategori
        binding.btnMenuKategori.setOnClickListener {
            Toast.makeText(this, "🏷️ Filter berdasarkan kategori...", Toast.LENGTH_SHORT).show()
        }

        // 🔔 Pengingat (grid menu)
        binding.btnMenuPengingat.setOnClickListener {
            handleScheduleReminder()
        }

        // 🔕 Batalkan Pengingat
        binding.btnMenuBatalReminder.setOnClickListener {
            ReminderHelper.cancelAllReminders(this)
        }
    }

    /**
     * Menangani klik tombol "Tampilkan Notifikasi".
     * Mengecek izin terlebih dahulu sebelum menampilkan notifikasi.
     */
    private fun handleShowNotification() {
        if (!PermissionHelper.isNotificationPermissionGranted(this)) {
            requestNotificationPermission()
            return
        }

        NotificationHelper.showNotification(
            context = this,
            title = "📋 Inventaris Aset Desa",
            message = "Terdapat 12 aset desa yang tercatat. " +
                    "Pastikan semua data aset telah diperbarui. " +
                    "Ketuk untuk melihat detail selengkapnya.",
            notificationId = 9001
        )

        Toast.makeText(this, "✅ Notifikasi telah dikirim!", Toast.LENGTH_SHORT).show()
    }

    /**
     * Menangani klik tombol "Jadwalkan Pengingat".
     * Mengecek izin notifikasi & alarm sebelum menjadwalkan.
     */
    private fun handleScheduleReminder() {
        // 1. Cek izin notifikasi
        if (!PermissionHelper.isNotificationPermissionGranted(this)) {
            requestNotificationPermission()
            return
        }

        // 2. Cek izin alarm eksak (Android 12+)
        if (!PermissionHelper.isExactAlarmPermissionGranted(this)) {
            PermissionHelper.showExactAlarmRationale(this) {
                // Callback setelah user membuka pengaturan
            }
            return
        }

        // 3. Jadwalkan pengingat
        ReminderHelper.scheduleReminderInSeconds(
            context = this,
            title = "🔔 Cek Inventaris Aset Desa",
            message = "Saatnya melakukan pengecekan rutin inventaris aset desa. " +
                    "Pastikan kondisi aset tercatat dengan benar dan laporkan " +
                    "jika ada kerusakan atau kehilangan.",
            delaySeconds = 10
        )

        binding.tvReminderHint.text = "✅ Pengingat akan muncul 10 detik dari sekarang"
    }

    /**
     * Meminta izin notifikasi POST_NOTIFICATIONS.
     */
    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                showNotificationPermissionRationale()
            } else {
                notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    /**
     * Menampilkan dialog penjelasan mengapa izin notifikasi diperlukan.
     */
    private fun showNotificationPermissionRationale() {
        AlertDialog.Builder(this)
            .setTitle("Izin Notifikasi Diperlukan")
            .setMessage(
                "Aplikasi Villagio memerlukan izin notifikasi untuk mengirimkan " +
                        "pengingat pengecekan aset desa dan pemberitahuan penting terkait " +
                        "inventarisasi aset.\n\n" +
                        "Aktifkan izin di pengaturan?"
            )
            .setPositiveButton("Buka Pengaturan") { _, _ ->
                PermissionHelper.openAppSettings(this)
            }
            .setNegativeButton("Nanti", null)
            .show()
    }

    /**
     * Memperbarui tampilan chip status izin.
     */
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

            R.id.action_search -> {
                Toast.makeText(this, "Search Clicked", Toast.LENGTH_SHORT).show()
                true
            }

            R.id.action_settings -> {
                Toast.makeText(this, "Settings Clicked", Toast.LENGTH_SHORT).show()
                true
            }

            R.id.action_darkmode -> {
                item.isChecked = !item.isChecked

                if (item.isChecked) {
                    Toast.makeText(this, "Dark Mode ON", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Dark Mode OFF", Toast.LENGTH_SHORT).show()
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
