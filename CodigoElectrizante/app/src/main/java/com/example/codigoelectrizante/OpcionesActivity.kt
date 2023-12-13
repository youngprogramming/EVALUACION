package com.example.codigoelectrizante

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.codigoelectrizante.databinding.ActivityOpcionesBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class OpcionesActivity : AppCompatActivity() {
    //Agregar viewBinding
    private lateinit var binding: ActivityOpcionesBinding
    //importamos firebase auth
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOpcionesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Inicializar firebase auth
        auth = Firebase.auth

        //Me permitan cambiar mi contraseña
        // PEdir la contraseña actual
        // PEdir la nueva y la confirmación
        // Volver a reconectar al usuario
        // Validar si la contraseña es valida
        // Cambiar contraseña

        //Otra Tarea, puede borrar su cuenta.
        binding.btnChangePass.setOnClickListener {
            val intent = Intent(this, ChangePassActivity::class.java)
            startActivity(intent)
        }

        //Funcion del boton para cerrar sesion
        binding.btnCerrarSesion.setOnClickListener {
            auth.signOut()

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

            finish()
        }
    }
}