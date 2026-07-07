package com.example.elin_cortis.data.model.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.elin_cortis.data.model.entity.AsetEntity

@Dao
interface AsetDao {

    @Query("SELECT * FROM aset")
    suspend fun getAll(): List<AsetEntity>

    @Insert
    suspend fun insert(aset: AsetEntity)

    @Update
    suspend fun update(aset: AsetEntity)

    @Delete
    suspend fun delete(aset: AsetEntity)
}