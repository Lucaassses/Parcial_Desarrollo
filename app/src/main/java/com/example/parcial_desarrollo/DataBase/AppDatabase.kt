package com.example.parcial_desarrollo.DataBase

import android.content.Context
import androidx.databinding.adapters.Converters
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.parcial_desarrollo.Dao.ClienteDao
import com.example.parcial_desarrollo.Dao.ProductoDao
import com.example.parcial_desarrollo.Dao.VentaDao
import com.example.parcial_desarrollo.Model.Cliente
import com.example.parcial_desarrollo.Model.Producto
import com.example.parcial_desarrollo.Model.Venta
@Database(
    entities = [Producto::class, Cliente::class, Venta::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productoDao(): ProductoDao
    abstract fun clienteDao(): ClienteDao
    abstract fun ventaDao(): VentaDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}