package com.example.parcial_desarrollo.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.parcial_desarrollo.Model.Cliente

@Dao
interface ClienteDao {
    @Query("SELECT * FROM clientes")
    suspend fun getAll(): List<Cliente>

    @Query("SELECT * FROM clientes WHERE id = :id")
    suspend fun getById(id: Int): Cliente?

    @Insert
    suspend fun insert(cliente: Cliente)

    @Update
    suspend fun update(cliente: Cliente)

    @Delete
    suspend fun delete(cliente: Cliente)
}