package com.example.codigoelectrizante

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.codigoelectrizante.databinding.ActivityRegistroBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class RegistroActivity : AppCompatActivity() {

    //Activamos viewBinding
    private  lateinit var binding: ActivityRegistroBinding

    //Firebase auth
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Activar el auth de Firebase
        auth = Firebase.auth


        binding.btnRegistrar.setOnClickListener {

        // Obtener el correo electrónico, la contraseña y la confirmación de contraseña de los campos de texto
            val email:String = binding.etCorreo.text.toString()
            val password:String = binding.etPassword.text.toString()
            val conPassword:String = binding.etPassword2.text.toString()


            if(email.isEmpty()) {
                binding.etCorreo.error = "Ingresa un correo"
                return@setOnClickListener
            }
            if (password.isEmpty()){
                binding.etPassword.error = "Ingresa una contraseña"
                return@setOnClickListener
            }
            if (conPassword.isEmpty()){
                binding.etPassword2.error = "Debes confirmar la contraseña"
                return@setOnClickListener
            }


            if(password == conPassword){
                registrarUsuario(email,password)
            }

        }




    }
    // Función para registrar un usuario en Firebase con el correo electrónico y la contraseña proporcionados
    private fun registrarUsuario(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener{
                // Verificar si el registro fue exitoso
                if (it.isSuccessful){
                    Toast.makeText(this,"Usuario registrado", Toast.LENGTH_LONG).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }else{
                    // Mostrar un mensaje de error si el registro no fue exitoso
                    Toast.makeText(this,"ERROR", Toast.LENGTH_LONG).show()
                }
            }


    }
}