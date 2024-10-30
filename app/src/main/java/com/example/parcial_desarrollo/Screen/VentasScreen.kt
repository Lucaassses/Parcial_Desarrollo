package com.example.parcial_desarrollo.Screen
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.parcial_desarrollo.DataBase.AppDatabase
import com.example.parcial_desarrollo.Model.Cliente
import com.example.parcial_desarrollo.Model.Venta
import com.example.parcial_desarrollo.Model.Producto
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun VentasScreen(navController: NavController) {
    var showDialog by remember { mutableStateOf(false) }
    var selectedProductoId by remember { mutableStateOf<Int?>(null) }
    var selectedClienteId by remember { mutableStateOf<Int?>(null) }
    var cantidad by remember { mutableStateOf("") }

    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val db = AppDatabase.getDatabase(context)

    val ventas = remember { mutableStateOf<List<Venta>>(emptyList()) }
    val productos = remember { mutableStateOf<List<Producto>>(emptyList()) }
    val clientes = remember { mutableStateOf<List<Cliente>>(emptyList()) }

    LaunchedEffect(Unit) {
        ventas.value = db.ventaDao().getAll()
        productos.value = db.productoDao().getAll()
        clientes.value = db.clienteDao().getAll()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Button(
            onClick = { showDialog = true },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Registrar Venta")
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(ventas.value) { venta ->
                VentaItem(venta, productos.value, clientes.value)
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Nueva Venta") },
            text = {
                Column {
                    // Dropdown para productos
                    ExposedDropdownMenuBox(
                        expanded = false,
                        onExpandedChange = { }
                    ) {
                        productos.value.forEach { producto ->
                            DropdownMenuItem(
                                onClick = { selectedProductoId = producto.id }
                            ) {
                                Text(producto.nombre)
                            }
                        }
                    }

                    // Dropdown para clientes
                    ExposedDropdownMenuBox(
                        expanded = false,
                        onExpandedChange = { }
                    ) {
                        clientes.value.forEach { cliente ->
                            DropdownMenuItem(
                                onClick = { selectedClienteId = cliente.id }
                            ) {
                                Text(cliente.nombre)
                            }
                        }
                    }

                    TextField(
                        value = cantidad,
                        onValueChange = { cantidad = it },
                        label = { Text("Cantidad") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                }
            },
            confirmButton = {
                Button(onClick = {
                    scope.launch {
                        try {
                            if (selectedProductoId != null && selectedClienteId != null) {
                                val venta = Venta(
                                    productoId = selectedProductoId!!,
                                    clienteId = selectedClienteId!!,
                                    cantidad = cantidad.toIntOrNull() ?: 0,
                                    fecha = Date()
                                )
                                db.ventaDao().insert(venta)
                                ventas.value = db.ventaDao().getAll()
                                showDialog = false
                                selectedProductoId = null
                                selectedClienteId = null
                                cantidad = ""
                            }
                        } catch (e: Exception) {
                            Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                        }
                    }
                }) {
                    Text("Guardar")
                }
            }
        )
    }
}

@Composable
fun VentaItem(venta: Venta, productos: List<Producto>, clientes: List<Cliente>) {
    val producto = productos.find { it.id == venta.productoId }
    val cliente = clientes.find { it.id == venta.clienteId }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Producto: ${producto?.nombre ?: "Desconocido"}")
            Text(text = "Cliente: ${cliente?.nombre ?: "Desconocido"}")
            Text(text = "Cantidad: ${venta.cantidad}")
            Text(text = "Fecha: ${SimpleDateFormat("dd/MM/yyyy").format(venta.fecha)}")
        }
    }
}