package com.example.elin_cortis.Aset

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.elin_cortis.data.model.entity.AsetEntity
import com.example.elin_cortis.databinding.ItemAsetBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar

class AsetAdapter(
    private val asetList: List<AsetEntity>,
    private val onDeleteClick: (AsetEntity) -> Unit
) : RecyclerView.Adapter<AsetAdapter.AsetViewHolder>() {

    inner class AsetViewHolder(val binding: ItemAsetBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsetViewHolder {
        val binding = ItemAsetBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return AsetViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AsetViewHolder, position: Int) {
        val item = asetList[position]

        holder.binding.tvTitle.text = "Kode Aset: ${item.kode_aset}"
        holder.binding.tvContent.text =
            "Nama Aset: ${item.nama_aset}\nKategori: ${item.kategori_aset}"

        holder.itemView.setOnClickListener {
            Snackbar.make(
                holder.itemView,
                "Aset ${item.nama_aset}",
                Snackbar.LENGTH_SHORT
            ).show()
        }

        holder.binding.btnDelete.setOnClickListener {
            MaterialAlertDialogBuilder(holder.itemView.context)
                .setTitle("Hapus Data Aset")
                .setMessage("Apakah kamu yakin ingin menghapus aset ${item.kode_aset}?")
                .setPositiveButton("Ya") { dialog, _ ->
                    onDeleteClick(item)
                    dialog.dismiss()
                }
                .setNegativeButton("Batal") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }
    }

    override fun getItemCount(): Int = asetList.size
}