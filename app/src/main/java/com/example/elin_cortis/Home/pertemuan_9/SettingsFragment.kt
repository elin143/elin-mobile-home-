package com.example.elin_cortis.Home.pertemuan_9

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import com.example.elin_cortis.R
import com.example.elin_cortis.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    // Data title + subtitle
    private val settingsTitle = arrayOf(
        "👤 Akun & Profil",
        "🔔 Notifikasi",
        "🎨 Tampilan (Tema)",
        "🔒 Keamanan & Sandi",
        "🌐 Bahasa",
        "💡 Bantuan & Dukungan",
        "📄 Syarat & Ketentuan",
        "🔄 Versi Aplikasi"
    )

    private val settingsSubtitle = arrayOf(
        "Detail profil administrator desa",
        "Pengatur pengingat pemeliharaan aset",
        "Ganti ke Mode Gelap atau Hijau Sage",
        "Kelola kata sandi dan pin pengaman",
        "Atur bahasa aplikasi (Indonesia / Inggris)",
        "Laporkan kendala sistem inventaris",
        "Kebijakan penggunaan data desa",
        "Versi 1.0.0"
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = object : ArrayAdapter<String>(
            requireContext(),
            R.layout.item_settings,
            R.id.tvTitle,
            settingsTitle
        ) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val itemView = super.getView(position, convertView, parent)
                val tvSubtitle = itemView.findViewById<TextView>(R.id.tvSubtitle)
                tvSubtitle.text = settingsSubtitle[position]
                return itemView
            }
        }

        binding.listViewSettings.adapter = adapter

        binding.listViewSettings.setOnItemClickListener { _, _, position, _ ->
            Toast.makeText(requireContext(), settingsTitle[position], Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}