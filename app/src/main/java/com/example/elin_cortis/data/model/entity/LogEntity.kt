package com.example.elin_cortis.data.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "logs")
data class LogEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val description: String,
    val category: String, // Misal: "Aset", "Sistem", "Maintenance"
    val timestamp: String
)