package com.example.elin_cortis.Home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.elin_cortis.Aset.AsetActivity
import com.example.elin_cortis.Aset.ScanQrActivity
import com.example.elin_cortis.Hitung_hitung
import com.example.elin_cortis.Home.pertemuan6.AuthActivity
import com.example.elin_cortis.Home.pertemuan6.WebViewActivity
import com.example.elin_cortis.Home.pertemuan_10.TenthActivity
import com.example.elin_cortis.Home.pertemuan_5.FifthActivity
import com.example.elin_cortis.Home.pertemuan_9.NinthActivity
import com.example.elin_cortis.Home.photo.PhotoAdapter
import com.example.elin_cortis.data.model.api.PhotoApiClient
import com.example.elin_cortis.data.model.api.PhotoApiService
import com.example.elin_cortis.databinding.FragmentHomeBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_home, container, false)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPref = requireActivity().getSharedPreferences("user_pref", AppCompatActivity.MODE_PRIVATE)

        binding.btnAset.setOnClickListener {
            val intent = Intent(requireContext(), AsetActivity::class.java)
            startActivity(intent)
        }

        binding.btn13.setOnClickListener {
            val intent = Intent(requireContext(), ScanQrActivity::class.java)
            startActivity(intent)
        }

        binding.btnKalkulator.setOnClickListener {
            val intent = Intent(requireContext(), Hitung_hitung::class.java)
            startActivity(intent)
        }

        binding.btnWebView.setOnClickListener {
            val intent = Intent(requireContext(), WebViewActivity::class.java)
            startActivity(intent)
        }

        binding.btn5.setOnClickListener {
            val intent = Intent(requireContext(), FifthActivity::class.java)
            startActivity(intent)
        }
        binding.btn9.setOnClickListener {
            val intent = Intent(requireContext(), NinthActivity::class.java)
            startActivity(intent)
        }
        binding.btn10.setOnClickListener {
            val intent = Intent(requireContext(), TenthActivity::class.java)
            startActivity(intent)
        }
        binding.btnlogout.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Konfirmasi")
                .setMessage("Apakah Anda yakin ingin logout?")
                .setPositiveButton("Ya") { dialog, _ ->
                    dialog.dismiss()

                    val editor = sharedPref.edit()
                    editor.clear()
                    editor.apply()

                    val intent = Intent(requireContext(), AuthActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish()
                }
                .setNegativeButton("Batal") { dialog, _ ->
                    dialog.dismiss()
                    Log.e("Info Dialog","Anda memilih Tidak!")
                }
                .show()
        }
    loadPhoto()
}

private fun loadPhoto() {
    lifecycleScope.launch {
        try {
            val photos = PhotoApiClient.apiService.getPhotos()
            val assetNames = listOf(
                "Balai Pertemuan Desa Villagio",
                "Mobil Ambulans Siaga",
                "Traktor Pertanian Tani",
                "Komputer Administrasi Pelayanan",
                "Peralatan Linmas & Keamanan",
                "Lampu Penerangan Jalan Umum",
                "Mesin Pencacah Sampah",
                "Tenda Pengungsian Darurat",
                "Pompa Air Irigasi",
                "Fasilitas Playground PAUD"
            )
            val assetImages = listOf(
                "https://images.unsplash.com/photo-1577083552431-6e5fd01aa342?q=80&w=600&auto=format&fit=crop",
                "https://images.unsplash.com/photo-1587300003388-59208cc962cb?q=80&w=600&auto=format&fit=crop",
                "https://images.unsplash.com/photo-1530268729831-4b0b9e170218?q=80&w=600&auto=format&fit=crop",
                "https://images.unsplash.com/photo-1588702547919-26089e690eca?q=80&w=600&auto=format&fit=crop",
                "https://images.unsplash.com/photo-1509391366360-2e959784a276?q=80&w=600&auto=format&fit=crop",
                "https://images.unsplash.com/photo-1599819811279-d5ad9cccf838?q=80&w=600&auto=format&fit=crop",
                "https://images.unsplash.com/photo-1605647540924-852290f6b0d5?q=80&w=600&auto=format&fit=crop",
                "https://images.unsplash.com/photo-1504280390367-361c6d9f38f4?q=80&w=600&auto=format&fit=crop",
                "https://images.unsplash.com/photo-1463171359979-300627e36b06?q=80&w=600&auto=format&fit=crop",
                "https://images.unsplash.com/photo-1596464716127-f2a82984de30?q=80&w=600&auto=format&fit=crop"
            )

            val themedPhotos = photos.mapIndexed { index, photoModel ->
                val nameIndex = index % assetNames.size
                photoModel.copy(
                    author = assetNames[nameIndex],
                    download_url = assetImages[nameIndex]
                )
            }

            val adapter = PhotoAdapter(themedPhotos)
            binding.rvGallery.adapter = adapter
            binding.rvGallery.layoutManager = LinearLayoutManager(requireContext())
        } catch (e: Exception) {
            val fallbackPhotos = listOf(
                com.example.elin_cortis.data.model.PhotoModel("1", "Balai Pertemuan Desa Villagio", "https://images.unsplash.com/photo-1577083552431-6e5fd01aa342?q=80&w=600&auto=format&fit=crop"),
                com.example.elin_cortis.data.model.PhotoModel("2", "Mobil Ambulans Siaga", "https://images.unsplash.com/photo-1587300003388-59208cc962cb?q=80&w=600&auto=format&fit=crop"),
                com.example.elin_cortis.data.model.PhotoModel("3", "Traktor Pertanian Tani", "https://images.unsplash.com/photo-1530268729831-4b0b9e170218?q=80&w=600&auto=format&fit=crop"),
                com.example.elin_cortis.data.model.PhotoModel("4", "Komputer Administrasi Pelayanan", "https://images.unsplash.com/photo-1588702547919-26089e690eca?q=80&w=600&auto=format&fit=crop")
            )
            val adapter = PhotoAdapter(fallbackPhotos)
            binding.rvGallery.adapter = adapter
            binding.rvGallery.layoutManager = LinearLayoutManager(requireContext())
        }
    }
}
}