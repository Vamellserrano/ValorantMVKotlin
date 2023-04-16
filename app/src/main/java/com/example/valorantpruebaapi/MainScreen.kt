package com.example.valorantpruebaapi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.example.valorantpruebaapi.databinding.ActivityMainScreenBinding
import com.google.firebase.auth.FirebaseAuth

class MainScreen : AppCompatActivity() {

    private lateinit var binding: ActivityMainScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonAgents.setOnClickListener {
            val intent = Intent(this@MainScreen,ActivityAgents::class.java)
            startActivity(intent)
            }

        //The "lineups" option can be only accessed by logging into the application.
        binding.buttonLineups.setOnClickListener {
            if (FirebaseAuth.getInstance().getCurrentUser() != null){
//                val lineupsIntent = Intent(this@MainScreen, LineupsActivity::class.java).apply {
//                startActivity(lineupsIntent)
            } else {
                showAlertError()
            }
        }
    }

    //MÉTODO PARA MOSTRAR ALERTAS EN CASO DE ERROR EN EL REGISTRO
    private fun showAlertError() { //
        val builder = AlertDialog.Builder(this)
        builder.setTitle("ERROR")
        builder.setMessage("A esta opción solo se puede acceder si has iniciado sesión antes.")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}