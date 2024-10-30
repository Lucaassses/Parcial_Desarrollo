package com.example.parcial_desarrollo.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.parcial_desarrollo.Model.Venta

@Dao
interface VentaDao {
    @Query("SELECT * FROM ventas")
    suspend fun getAll(): List<Venta>

    @Query("SELECT * FROM ventas WHERE id = :id")
    suspend fun getById(id: Int): Venta?

    @Insert
    suspend fun insert(venta: Venta)

    @Delete
    suspend fun delete(venta: Venta)
}