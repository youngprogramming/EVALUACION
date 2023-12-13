package com.example.codigoelectrizante

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.codigoelectrizante.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    //Agregar viewBinding
    private lateinit var binding: ActivityMainBinding
    //importamos firebase auth
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Inicializar firebase auth
        auth = Firebase.auth

        //Reconocer el boton del login
        binding.btnLogin.setOnClickListener {
            //Obtener el correo y la password
            val email = binding.etCorreo.text.toString()
            val password = binding.etPassword.text.toString()

            //Validar que los campos cuenten con datos
            if(email.isEmpty()){
                binding.etCorreo.error = "Por favor ingrese un correo"
                return@setOnClickListener
            }
            if(password.isEmpty()){
                binding.etPassword.error = "Por favor ingrese una contraseña"
                return@setOnClickListener
            }
            signIn(email,password)
        }

        //Reconocer el texto para registrar un usuario
        binding.tvRegistrar.setOnClickListener {
            val intent = Intent(this, RegistroActivity::class.java)
            startActivity(intent)
        }

    }
    // Intenta iniciar sesión con el correo electrónico y la contraseña proporcionados
    private fun signIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener { task ->
                if(task.isSuccessful){
                    Toast.makeText(this,"Inicio correcto", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, PostLogin::class.java)
                    startActivity(intent)
                }
                else{
                    Toast.makeText(this,"ERROR", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
