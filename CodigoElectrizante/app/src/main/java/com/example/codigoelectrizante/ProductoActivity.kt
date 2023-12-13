package com.example.codigoelectrizante

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.codigoelectrizante.Models.Producto
import com.example.codigoelectrizante.databinding.ActivityProductoBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ProductoActivity : AppCompatActivity() {

    //ViewBinding
    private lateinit var binding: ActivityProductoBinding
    //Firebase Database
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Inicio de la base de datos y ruta de almacenamiento
        database= FirebaseDatabase.getInstance().getReference("Productos")

        val btnVer = findViewById<Button>(R.id.btnVer)

        btnVer.setOnClickListener {

            // Crear un Intent para la nueva actividad (postlogin.kt)
            val intent = Intent(this, PostLogin::class.java)

            // Iniciar la nueva actividad
            startActivity(intent)
        }

        //obtener datos e insertar
        binding.btnGuardar.setOnClickListener {
            //obtener los datos del formulario
            val nombre = binding.etNombreProducto.text.toString()
            val precio = binding.etPrecioProducto.text.toString()
            val descripcion = binding.etDescripcionProducto.text.toString()
            //Generar el id de forma que sea unico
            val id = database.child("Productos").push().key

            if(nombre.isEmpty()) {
                binding.etNombreProducto.error = "Por favor ingrese el nombre"
                return@setOnClickListener
            }
            if(precio.isEmpty()) {
                binding.etPrecioProducto.error = "Por favor ingrese el precio"
                return@setOnClickListener
            }
            if(descripcion.isEmpty()) {
                binding.etDescripcionProducto.error = "Por favor ingrese la descripcion"
                return@setOnClickListener
            }
            val producto = Producto(id, nombre, precio, descripcion)

            database.child(id!!).setValue(producto)
                .addOnSuccessListener {
                    Toast.makeText(this, "Producto Insertado", Toast.LENGTH_LONG).show()
                }.addOnFailureListener{
                    Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()
                }

        }


    }
}