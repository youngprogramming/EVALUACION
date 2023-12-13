package com.example.codigoelectrizante

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.codigoelectrizante.databinding.ActivityPostLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class PostLogin : AppCompatActivity() {
    //Agregar viewBinding
    private lateinit var binding: ActivityPostLoginBinding
    //importamos firebase auth
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Inicializar firebase auth
        auth = Firebase.auth

        // Selecciona la opción del menú de navegación
        binding.bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.nav_crear -> {
                    // Cuando se selecciona "Crear", lanzamos la actividad ProductoActivity
                    val intent = Intent(this, ProductoActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.nav_buscar -> {
                    // Cuando se selecciona "Buscar", lanzamos la actividad VerProductosActivity
                    val intent = Intent(this, VerProductosActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.nav_settings -> {
                    // Cuando se selecciona "Configuración", lanzamos la actividad OpcionesActivity
                    val intent = Intent(this, OpcionesActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }

        }


        }
