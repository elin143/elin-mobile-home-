package com.example.elin_cortis.Home.pertemuan_10

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.elin_cortis.databinding.FragmentTabCBinding

class TabCFragment : Fragment() {
    private var _binding: FragmentTabCBinding? = null
    private val binding get() = _binding!!

    private val productList = listOf(
        // Kategori: Perkantoran & Administrasi Desa
        ProductModel("Meja Kerja Kepala Desa", "Rp 2.500.000", "https://picsum.photos/seed/officedesk/400/300"),
        ProductModel("Kursi Kerja Staf Kantor Desa", "Rp 850.000", "https://picsum.photos/seed/officechair/400/300"),
        ProductModel("Laptop Administrasi Desa", "Rp 7.500.000", "https://picsum.photos/seed/officelaptop/400/300"),
        ProductModel("Printer Cetak Surat KK/KTP", "Rp 3.200.000", "https://picsum.photos/seed/officeprinter/400/300"),
        ProductModel("Lemari Arsip Dokumen Desa", "Rp 4.500.000", "https://picsum.photos/seed/filingcabinet/400/300"),
        ProductModel("Komputer Pelayanan Publik", "Rp 6.800.000", "https://picsum.photos/seed/desktopcomp/400/300"),
        ProductModel("Papan Informasi Digital Desa", "Rp 5.500.000", "https://picsum.photos/seed/leddisplay/400/300"),
        ProductModel("AC Split Ruang Pertemuan", "Rp 4.200.000", "https://picsum.photos/seed/aircon/400/300"),
        ProductModel("Genset Darurat Kantor Desa", "Rp 12.500.000", "https://picsum.photos/seed/generator/400/300"),
        ProductModel("Brankas Penyimpanan Dana Desa", "Rp 6.000.000", "https://picsum.photos/seed/officesafe/400/300"),

        // Kategori: Fasilitas Balai Desa & Pertemuan
        ProductModel("Sound System Balai Desa", "Rp 8.500.000", "https://picsum.photos/seed/soundsystem/400/300"),
        ProductModel("Proyektor Epson Balai Desa", "Rp 6.200.000", "https://picsum.photos/seed/projector/400/300"),
        ProductModel("Kursi Lipat Pertemuan (50 Unit)", "Rp 12.500.000", "https://picsum.photos/seed/foldingchairs/400/300"),
        ProductModel("Meja Rapat Oval Kayu Jati", "Rp 15.000.000", "https://picsum.photos/seed/meetingtable/400/300"),
        ProductModel("Podium Pidato Kayu", "Rp 2.200.000", "https://picsum.photos/seed/podium/400/300"),
        ProductModel("Wireless Microphone Set", "Rp 1.800.000", "https://picsum.photos/seed/micwireless/400/300"),
        ProductModel("Panggung Lipat Portable", "Rp 9.500.000", "https://picsum.photos/seed/portablestage/400/300"),
        ProductModel("Kipas Angin Dinding Industri", "Rp 1.200.000", "https://picsum.photos/seed/wallfan/400/300"),
        ProductModel("Tenda Posko Tanggap Bencana", "Rp 7.800.000", "https://picsum.photos/seed/disastertent/400/300"),
        ProductModel("Dispenser Air Galon Balai Desa", "Rp 1.450.000", "https://picsum.photos/seed/waterdispenser/400/300"),

        // Kategori: Kendaraan & Alat Angkut Operasional
        ProductModel("Mobil Ambulans Desa", "Rp 245.000.000", "https://picsum.photos/seed/ambulance/400/300"),
        ProductModel("Motor Dinas Kepala Desa", "Rp 22.000.000", "https://picsum.photos/seed/officialmotorcycle/400/300"),
        ProductModel("Motor Tiga Roda Sampah", "Rp 32.500.000", "https://picsum.photos/seed/garbagebike/400/300"),
        ProductModel("Mobil Siaga Pengangkut Logistik", "Rp 185.000.000", "https://picsum.photos/seed/operationalcar/400/300"),
        ProductModel("Sepeda Listrik Patroli Linmas", "Rp 7.350.000", "https://picsum.photos/seed/ebike/400/300"),
        ProductModel("Tandu Lipat Darurat", "Rp 950.000", "https://picsum.photos/seed/stretcher/400/300"),
        ProductModel("Helm Keselamatan Linmas", "Rp 350.000", "https://picsum.photos/seed/safetyhelmet/400/300"),
        ProductModel("Rompi Reflektor Penjaga Malam", "Rp 120.000", "https://picsum.photos/seed/safetyvest/400/300"),
        ProductModel("HT (Handy Talkie) Komunikasi", "Rp 850.000", "https://picsum.photos/seed/walkietalkie/400/300"),
        ProductModel("Megaphone Pengumuman", "Rp 650.000", "https://picsum.photos/seed/megaphone/400/300"),

        // Kategori: Perkebunan, Pertanian, & Infrastruktur Desa
        ProductModel("Mesin Pencacah Sampah Organik", "Rp 14.500.000", "https://picsum.photos/seed/shreddermachine/400/300"),
        ProductModel("Traktor Tangan Bantuan Tani", "Rp 28.000.000", "https://picsum.photos/seed/handtractor/400/300"),
        ProductModel("Mesin Pompa Air Irigasi Sawah", "Rp 4.800.000", "https://picsum.photos/seed/waterpump/400/300"),
        ProductModel("Mesin Pemotong Rumput Lapangan", "Rp 2.450.000", "https://picsum.photos/seed/lawnmower/400/300"),
        ProductModel("Tangki Air Bersih Kapasitas Besar", "Rp 3.800.000", "https://picsum.photos/seed/watertank/400/300"),
        ProductModel("Peralatan Pertukangan Desa", "Rp 1.750.000", "https://picsum.photos/seed/toolsset/400/300"),
        ProductModel("Gergaji Mesin (Chainsaw)", "Rp 2.900.000", "https://picsum.photos/seed/chainsaw/400/300"),
        ProductModel("Alat Semprot Hama Otomatis", "Rp 1.150.000", "https://picsum.photos/seed/sprayer/400/300"),
        ProductModel("Lampu Jalan Tenaga Surya", "Rp 1.680.000", "https://picsum.photos/seed/solarstreet/400/300"),
        ProductModel("Gerobak Sorong Proyek Desa", "Rp 550.000", "https://picsum.photos/seed/wheelbarrow/400/300"),

        // Kategori: Kesehatan (Posyandu) & Pendidikan (PAUD)
        ProductModel("Timbangan Digital Balita Posyandu", "Rp 720.000", "https://picsum.photos/seed/babyscale/400/300"),
        ProductModel("Alat Ukur Tinggi Badan Elektrik", "Rp 450.000", "https://picsum.photos/seed/heightmeasure/400/300"),
        ProductModel("Lemari Obat Medis Posyandu", "Rp 2.100.000", "https://picsum.photos/seed/medicinecabinet/400/300"),
        ProductModel("Alat Cek Darah Multiguna", "Rp 650.000", "https://picsum.photos/seed/bloodcheck/400/300"),
        ProductModel("Meja Belajar Anak PAUD Desa", "Rp 1.250.000", "https://picsum.photos/seed/kidsdesk/400/300"),
        ProductModel("Loker Mainan Edukatif PAUD", "Rp 2.300.000", "https://picsum.photos/seed/toylooker/400/300"),
        ProductModel("Perosotan Anak Area Playground", "Rp 4.500.000", "https://picsum.photos/seed/playgroundslide/400/300"),
        ProductModel("Ayunan Besi Taman Desa", "Rp 3.800.000", "https://picsum.photos/seed/playgroundswing/400/300"),
        ProductModel("Karpet Puzzle Evamat Edukasi", "Rp 850.000", "https://picsum.photos/seed/puzzlemat/400/300"),
        ProductModel("Papan Tulis Whiteboard Besar", "Rp 450.000", "https://picsum.photos/seed/whiteboard/400/300")
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTabCBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ProductAdapter(productList) { selectedItem ->
            Toast.makeText(requireContext(), "Anda memilih ${selectedItem.name}", Toast.LENGTH_SHORT).show()
        }

        binding.rvProducts.apply {
            /** Mode Grid **/
//            layoutManager = GridLayoutManager(requireContext(), 3)

            /** Jika ingin model Linear **/
            layoutManager = LinearLayoutManager(requireContext())

            this.adapter = adapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}