package com.example.valorantpruebaapi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.valorantpruebaapi.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

enum class ProviderType {
    BASIC
}
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Acceso al menú principal CON LOGIN
        binding.buttonSignin.setOnClickListener {
                //Comprobamos que los campos no estén vacios
                if (binding.tilUser.text!!.isNotEmpty() && binding.tilPw.text!!.isNotEmpty()){
                    //Creamos una instancia de Firebase con una función de crear usuario con el correo y la contraseña
                    //Como parámetros le pasamos nuestros campos de texto de los cuales cogerá la información
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(
                        binding.tilUser.text.toString(),
                        binding.tilPw.text.toString()).addOnCompleteListener {
                        //Si no hay fallos, registrará el usuario y nos devolverá a la pantalla de login
                        if (it.isSuccessful){
                            showMain(it.result.user?.email, ProviderType.BASIC)
                            //En caso que no se pueda realizar el registro, dará una pantalla de error
                        } else {
                            showAlertLoginError()
                        }
                    }
                } else {
                    showAlertLoginError()
                }
            }

        //Acceso al menú principal SIN LOGIN
        binding.buttonContinue.setOnClickListener {
            val intent = Intent(this@LoginActivity,MainScreen::class.java)
            startActivity(intent)
        }

        //Acceso a la pantalla de registro
        binding.buttonSignup.setOnClickListener {
            val intent = Intent(this@LoginActivity,RegisterScreen::class.java)
            startActivity(intent)
        }
    }

    private fun showAlertLoginError(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("ERROR")
        builder.setMessage("Se ha producido un error al iniciar sesión. Por favor, revise los datos introducidos")
        builder.setPositiveButton("Aceptar",null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
    private fun showMain(email: String?, provider: ProviderType) {
        val mainIntent = Intent(this@LoginActivity, MainScreen::class.java).apply {
            putExtra("EMAIL", email)
            putExtra("PROVIDER", provider.name)
        }
        startActivity(mainIntent)
    }
}