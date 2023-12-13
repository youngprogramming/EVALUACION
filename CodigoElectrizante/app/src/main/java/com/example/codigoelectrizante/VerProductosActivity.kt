package com.example.codigoelectrizante

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.codigoelectrizante.Models.Producto
import com.example.codigoelectrizante.databinding.ActivityVerProductosBinding
import com.example.crudsec3.Adapter.AdapterProducto
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class VerProductosActivity : AppCompatActivity() {
    // View Binding
    private lateinit var binding: ActivityVerProductosBinding

    // Lista de productos
    private lateinit var productosList: ArrayList<Producto>

    // Adaptador
    private lateinit var productosRecyclerView: RecyclerView

    // Firebase
    private lateinit var database: DatabaseReference

    // Adaptador
    private lateinit var adapterProducto: AdapterProducto

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerProductosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicialización de las vistas
        productosRecyclerView = binding.rvProductos
        productosRecyclerView.layoutManager = LinearLayoutManager(this)
        productosRecyclerView.setHasFixedSize(true)

        // Inicialización de la lista de productos
        productosList = arrayListOf()

        // Obtener y mostrar los productos desde Firebase
        getProductos()

        // Configuración del botón "Volver"
        val btnVolver: Button = findViewById(R.id.btnVer)
        btnVolver.setOnClickListener {
            // Crear un Intent para iniciar la actividad PostLoginActivity
            val intent = Intent(this, PostLogin::class.java)
            startActivity(intent)
            // Cierra la actividad actual si es necesario
            finish()
        }
    }

    private fun getProductos() {
        // Ruta de productos en Firebase
        database = FirebaseDatabase.getInstance().getReference("Productos")

        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (productosSnapshot in snapshot.children) {
                        val producto = productosSnapshot.getValue(Producto::class.java)
                        productosList.add(producto!!)
                    }

                    // Configuración del adaptador y asignación al RecyclerView
                    adapterProducto = AdapterProducto(productosList)
                    // Configurar el listener para manejar clics en el botón de eliminar
                    adapterProducto.onEliminarClickListener = object : AdapterProducto.OnEliminarClickListener {
                        override fun onEliminarClick(position: Int, productoId: String?) {
                            eliminarProducto(position, productoId)
                        }
                    }
                    productosRecyclerView.adapter = adapterProducto
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Manejar la cancelación si es necesario
            }
        })
    }

    // Método para eliminar un producto
    private fun eliminarProducto(position: Int, productoId: String?) {
        // Eliminar de Firebase
        val database = FirebaseDatabase.getInstance().getReference("Productos")
        database.child(productoId!!).removeValue()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Éxito al eliminar de Firebase
                    // Puedes mostrar un mensaje de éxito si lo deseas

                    // Eliminar de la lista local
                    productosList.removeAt(position)
                    // Notificar al adaptador que se ha eliminado un elemento en la posición dada
                    adapterProducto.notifyItemRemoved(position)
                    // Notificar al adaptador que los datos han cambiado
                    adapterProducto.notifyDataSetChanged()
                } else {
                    // Error al eliminar de Firebase
                    // Puedes mostrar un mensaje de error si lo deseas
                    // Log.e("EliminarProducto", "Error al eliminar producto", task.exception)
                }
            }
    }
}