package com.example.valorantpruebaapi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.example.valorantpruebaapi.databinding.ActivityRegisterScreenBinding
import com.google.firebase.auth.FirebaseAuth

class RegisterScreen : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Sobre el botón de iniciar sesión
        binding.buttonSignup.setOnClickListener {
            //Comprobamos que los campos no estén vacios
            if (binding.tilUser.text!!.isNotEmpty() && binding.tilPw.text!!.isNotEmpty()) {
                //Creamos una instancia de Firebase con una función de iniciar sesión con usuario con el correo y la contraseña
                //Como parámetros le pasamos nuestros campos de texto de los cuales cogerá la información
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                    binding.tilUser.text.toString(),
                    binding.tilPw.text.toString()
                ).addOnCompleteListener {
                    //Si no hay fallos, registrará el usuario y nos devolverá a la pantalla de login
                    if (it.isSuccessful) {
                        showLogin(it.result.user?.email, ProviderType.BASIC)
                        //En caso que no se pueda realizar el registro, dará una pantalla de error
                    } else {
                        showAlertError()
                    }
                }
            }
        }



    }
    //MÉTODO PARA MOSTRAR ALERTAS EN CASO DE ERROR EN EL REGISTRO
    private fun showAlertError() { //
        val builder = AlertDialog.Builder(this)
        builder.setTitle("ERROR")
        builder.setMessage("Se ha producido un error registrando al usuario")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun showLogin(email: String?, provider: ProviderType) {
        val loginIntent = Intent(this@RegisterScreen, LoginActivity::class.java).apply {
            putExtra("EMAIL", email)
            putExtra("PROVIDER", provider.name)
        }
        startActivity(loginIntent)
    }

}