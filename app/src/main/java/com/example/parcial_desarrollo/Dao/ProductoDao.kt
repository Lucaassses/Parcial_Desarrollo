package com.example.parcial_desarrollo.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.parcial_desarrollo.Model.Producto

@Dao
interface ProductoDao {
    @Query("SELECT * FROM productos")
    suspend fun getAll(): List<Producto>

    @Query("SELECT * FROM productos WHERE id = :id")
    suspend fun getById(id: Int): Producto?

    @Insert
    suspend fun insert(producto: Producto)

    @Update
    suspend fun update(producto: Producto)

    @Delete
    suspend fun delete(producto: Producto)
}
