package com.example.elin_cortis.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.elin_cortis.data.model.dao.AsetDao
import com.example.elin_cortis.data.model.dao.LogDao
import com.example.elin_cortis.data.model.entity.AsetEntity
import com.example.elin_cortis.data.model.entity.LogEntity

@Database(
    entities = [AsetEntity::class, LogEntity::class], // Menambahkan LogEntity
    version = 2, // Menaikkan versi karena ada perubahan skema
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun AsetDao(): AsetDao
    abstract fun LogDao(): LogDao // Menambahkan LogDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                )
                    .fallbackToDestructiveMigration() // Menghapus data lama jika versi naik (untuk mempermudah saat dev)
                    .build().also { INSTANCE = it }
            }
        }
    }
}