package com.example.elin_cortis.Home.pertemuan_10

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.elin_cortis.R
import com.example.elin_cortis.databinding.FragmentTabCBinding
import com.google.android.material.chip.Chip

class TabCFragment : Fragment() {
    private var _binding: FragmentTabCBinding? = null
    private val binding get() = _binding!!

    private val productList = listOf(
        // Kategori: Perkantoran & Administrasi Desa
        ProductModel("Meja Kerja Kepala Desa", "Rp 2.500.000", "https://picsum.photos/seed/officedesk/400/300", "Kantor"),
        ProductModel("Kursi Kerja Staf Kantor Desa", "Rp 850.000", "https://picsum.photos/seed/officechair/400/300", "Kantor"),
        ProductModel("Laptop Administrasi Desa", "Rp 7.500.000", "https://picsum.photos/seed/officelaptop/400/300", "Kantor"),
        ProductModel("Printer Cetak Surat KK/KTP", "Rp 3.200.000", "https://picsum.photos/seed/officeprinter/400/300", "Kantor"),
        ProductModel("Lemari Arsip Dokumen Desa", "Rp 4.500.000", "https://picsum.photos/seed/filingcabinet/400/300", "Kantor"),
        ProductModel("Komputer Pelayanan Publik", "Rp 6.800.000", "https://picsum.photos/seed/desktopcomp/400/300", "Kantor"),
        ProductModel("Papan Informasi Digital Desa", "Rp 5.500.000", "https://picsum.photos/seed/leddisplay/400/300", "Kantor"),
        ProductModel("AC Split Ruang Pertemuan", "Rp 4.200.000", "https://picsum.photos/seed/aircon/400/300", "Kantor"),
        ProductModel("Genset Darurat Kantor Desa", "Rp 12.500.000", "https://picsum.photos/seed/generator/400/300", "Kantor"),
        ProductModel("Brankas Penyimpanan Dana Desa", "Rp 6.000.000", "https://picsum.photos/seed/officesafe/400/300", "Kantor"),

        // Kategori: Fasilitas Balai Desa & Pertemuan
        ProductModel("Sound System Balai Desa", "Rp 8.500.000", "https://picsum.photos/seed/soundsystem/400/300", "Kantor"),
        ProductModel("Proyektor Epson Balai Desa", "Rp 6.200.000", "https://picsum.photos/seed/projector/400/300", "Kantor"),
        ProductModel("Kursi Lipat Pertemuan (50 Unit)", "Rp 12.500.000", "https://picsum.photos/seed/foldingchairs/400/300", "Kantor"),
        ProductModel("Meja Rapat Oval Kayu Jati", "Rp 15.000.000", "https://picsum.photos/seed/meetingtable/400/300", "Kantor"),
        ProductModel("Podium Pidato Kayu", "Rp 2.200.000", "https://picsum.photos/seed/podium/400/300", "Kantor"),
        ProductModel("Wireless Microphone Set", "Rp 1.800.000", "https://picsum.photos/seed/micwireless/400/300", "Kantor"),
        ProductModel("Panggung Lipat Portable", "Rp 9.500.000", "https://picsum.photos/seed/portablestage/400/300", "Kantor"),
        ProductModel("Kipas Angin Dinding Industri", "Rp 1.200.000", "https://picsum.photos/seed/wallfan/400/300", "Kantor"),
        ProductModel("Tenda Posko Tanggap Bencana", "Rp 7.800.000", "https://picsum.photos/seed/disastertent/400/300", "Kantor"),
        ProductModel("Dispenser Air Galon Balai Desa", "Rp 1.450.000", "https://picsum.photos/seed/waterdispenser/400/300", "Kantor"),

        // Kategori: Kendaraan & Alat Angkut Operasional
        ProductModel("Mobil Ambulans Desa", "Rp 245.000.000", "https://picsum.photos/seed/ambulance/400/300", "Kendaraan"),
        ProductModel("Motor Dinas Kepala Desa", "Rp 22.000.000", "https://picsum.photos/seed/officialmotorcycle/400/300", "Kendaraan"),
        ProductModel("Motor Tiga Roda Sampah", "Rp 32.500.000", "https://picsum.photos/seed/garbagebike/400/300", "Kendaraan"),
        ProductModel("Mobil Siaga Pengangkut Logistik", "Rp 185.000.000", "https://picsum.photos/seed/operationalcar/400/300", "Kendaraan"),
        ProductModel("Sepeda Listrik Patroli Linmas", "Rp 7.350.000", "https://picsum.photos/seed/ebike/400/300", "Kendaraan"),
        ProductModel("Tandu Lipat Darurat", "Rp 950.000", "https://picsum.photos/seed/stretcher/400/300", "Kendaraan"),
        ProductModel("Helm Keselamatan Linmas", "Rp 350.000", "https://picsum.photos/seed/safetyhelmet/400/300", "Kendaraan"),
        ProductModel("Rompi Reflektor Penjaga Malam", "Rp 120.000", "https://picsum.photos/seed/safetyvest/400/300", "Kendaraan"),
        ProductModel("HT (Handy Talkie) Komunikasi", "Rp 850.000", "https://picsum.photos/seed/walkietalkie/400/300", "Kendaraan"),
        ProductModel("Megaphone Pengumuman", "Rp 650.000", "https://picsum.photos/seed/megaphone/400/300", "Kendaraan"),

        // Kategori: Perkebunan, Pertanian, & Infrastruktur Desa
        ProductModel("Mesin Pencacah Sampah Organik", "Rp 14.500.000", "https://picsum.photos/seed/shreddermachine/400/300", "Pertanian"),
        ProductModel("Traktor Tangan Bantuan Tani", "Rp 28.000.000", "https://picsum.photos/seed/handtractor/400/300", "Pertanian"),
        ProductModel("Mesin Pompa Air Irigasi Sawah", "Rp 4.800.000", "https://picsum.photos/seed/waterpump/400/300", "Pertanian"),
        ProductModel("Mesin Pemotong Rumput Lapangan", "Rp 2.450.000", "https://picsum.photos/seed/lawnmower/400/300", "Pertanian"),
        ProductModel("Tangki Air Bersih Kapasitas Besar", "Rp 3.800.000", "https://picsum.photos/seed/watertank/400/300", "Pertanian"),
        ProductModel("Peralatan Pertukangan Desa", "Rp 1.750.000", "https://picsum.photos/seed/toolsset/400/300", "Pertanian"),
        ProductModel("Gergaji Mesin (Chainsaw)", "Rp 2.900.000", "https://picsum.photos/seed/chainsaw/400/300", "Pertanian"),
        ProductModel("Alat Semprot Hama Otomatis", "Rp 1.150.000", "https://picsum.photos/seed/sprayer/400/300", "Pertanian"),
        ProductModel("Lampu Jalan Tenaga Surya", "Rp 1.680.000", "https://picsum.photos/seed/solarstreet/400/300", "Pertanian"),
        ProductModel("Gerobak Sorong Proyek Desa", "Rp 550.000", "https://picsum.photos/seed/wheelbarrow/400/300", "Pertanian"),

        // Kategori: Kesehatan (Posyandu) & Pendidikan (PAUD)
        ProductModel("Timbangan Digital Balita Posyandu", "Rp 720.000", "https://picsum.photos/seed/babyscale/400/300", "PAUD/Posyandu"),
        ProductModel("Alat Ukur Tinggi Badan Elektrik", "Rp 450.000", "https://picsum.photos/seed/heightmeasure/400/300", "PAUD/Posyandu"),
        ProductModel("Lemari Obat Medis Posyandu", "Rp 2.100.000", "https://picsum.photos/seed/medicinecabinet/400/300", "PAUD/Posyandu"),
        ProductModel("Alat Cek Darah Multiguna", "Rp 650.000", "https://picsum.photos/seed/bloodcheck/400/300", "PAUD/Posyandu"),
        ProductModel("Meja Belajar Anak PAUD Desa", "Rp 1.250.000", "https://picsum.photos/seed/kidsdesk/400/300", "PAUD/Posyandu"),
        ProductModel("Loker Mainan Edukatif PAUD", "Rp 2.300.000", "https://picsum.photos/seed/toylooker/400/300", "PAUD/Posyandu"),
        ProductModel("Perosotan Anak Area Playground", "Rp 4.500.000", "https://picsum.photos/seed/playgroundslide/400/300", "PAUD/Posyandu"),
        ProductModel("Ayunan Besi Taman Desa", "Rp 3.800.000", "https://picsum.photos/seed/playgroundswing/400/300", "PAUD/Posyandu"),
        ProductModel("Karpet Puzzle Evamat Edukasi", "Rp 850.000", "https://picsum.photos/seed/puzzlemat/400/300", "PAUD/Posyandu"),
        ProductModel("Papan Tulis Whiteboard Besar", "Rp 450.000", "https://picsum.photos/seed/whiteboard/400/300", "PAUD/Posyandu")
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTabCBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvProducts.layoutManager = LinearLayoutManager(requireContext())
        
        // Initial setup showing all products
        updateProductList(productList)

        // Setup filter chips click logic
        binding.chipGroupFilter.setOnCheckedStateChangeListener { _, checkedIds ->
            val checkedId = checkedIds.firstOrNull()
            if (checkedId != null) {
                val chip = view.findViewById<Chip>(checkedId)
                val category = chip.text.toString()
                if (category == "Semua") {
                    updateProductList(productList)
                } else {
                    val filtered = productList.filter { it.category.equals(category, ignoreCase = true) }
                    updateProductList(filtered)
                }
            } else {
                updateProductList(productList)
            }
        }
    }

    private fun updateProductList(list: List<ProductModel>) {
        val adapter = ProductAdapter(list) { selectedItem ->
            Toast.makeText(requireContext(), "Pilihan: ${selectedItem.name} (${selectedItem.price})", Toast.LENGTH_SHORT).show()
        }
        binding.rvProducts.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}