package com.example.codigoelectrizante

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.codigoelectrizante.databinding.ActivityChangePassBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class ChangePassActivity : AppCompatActivity() {

    //Binding
    private lateinit var binding: ActivityChangePassBinding

    //Firebase auth
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangePassBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth



        binding.btnChangePass.setOnClickListener {

            //Validar datos
            val contraActual: String = binding.etPassActual.text.toString()
            val contraNueva: String = binding.etPassword.text.toString()
            val contraConNueva: String = binding.etPassword2.text.toString()

            if (contraActual.isEmpty()) {
                binding.etPassActual.error = "Ingresa la contraseña"
                return@setOnClickListener
            }
            if (contraNueva.isEmpty()) {
                binding.etPassword.error = "Ingresa la contraseña"
                return@setOnClickListener
            }
            if (contraConNueva.isEmpty()) {
                binding.etPassword2.error = "Ingresa la contraseña"
                return@setOnClickListener
            }

            //Validar que las contraseñas sean iguales
            if (contraNueva == contraConNueva) {
                //DAtos de mi conexión
                val user = Firebase.auth.currentUser
                //Relogear el usuario
                val credential = EmailAuthProvider
                    .getCredential(user!!.email.toString(), contraActual)

                user.reauthenticate(credential)
                    .addOnCompleteListener {
                        user!!.updatePassword(contraNueva)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    //Cerrar la ventana actual
                                    finish()
                                }
                            }
                    }

            }
        }

    }
}
