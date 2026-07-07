package com.example.elin_cortis.data.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "aset")
data class AsetEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val kode_aset: String,
    val nama_aset: String,
    val kategori_aset: String,
    val lokasi_aset: String,
    val kondisi_aset: String
)