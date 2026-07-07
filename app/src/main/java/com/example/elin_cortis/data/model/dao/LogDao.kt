package com.example.elin_cortis.data.model.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.elin_cortis.data.model.entity.LogEntity

@Dao
interface LogDao {
    @Query("SELECT * FROM logs ORDER BY id DESC")
    suspend fun getAllLogs(): List<LogEntity>

    @Query("SELECT * FROM logs WHERE category = :category ORDER BY id DESC")
    suspend fun getLogsByCategory(category: String): List<LogEntity>

    @Insert
    suspend fun insertLog(log: LogEntity)

    @Delete
    suspend fun deleteLog(log: LogEntity)

    @Update
    suspend fun updateLog(log: LogEntity)

    @Query("DELETE FROM logs WHERE category = :category")
    suspend fun deleteLogsByCategory(category: String)

    @Query("DELETE FROM logs")
    suspend fun deleteAllLogs()
}