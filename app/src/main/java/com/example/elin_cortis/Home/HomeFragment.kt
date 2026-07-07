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
import com.example.elin_cortis.Home.pertemuan6.AuthActivity
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
            val adapter = PhotoAdapter(photos)
            binding.rvGallery.adapter = adapter

            /** List Tampil Vertical*/
            binding.rvGallery.layoutManager = LinearLayoutManager(requireContext())

            /** List Tampil Horizontal */
            //binding.rvGallery.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

            /** List Tampil Grid */
            //binding.rvGallery.layoutManager = GridLayoutManager(requireContext(),2)

        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Gagal memuat gambar", Toast.LENGTH_SHORT).show()
        }
    }
}
}