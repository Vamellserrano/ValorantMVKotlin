package com.example.valorantpruebaapi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.valorantpruebaapi.weapons.ActivityWeapons
import com.example.valorantpruebaapi.agents.ActivityAgents
import com.example.valorantpruebaapi.databinding.ActivityMainScreenBinding
import com.example.valorantpruebaapi.lineups.ActivityLineups
import com.example.valorantpruebaapi.maps.ActivityMaps
import com.google.firebase.auth.FirebaseAuth

class MainScreen : AppCompatActivity() {

    private lateinit var binding: ActivityMainScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonAgents.setOnClickListener {
            val intent = Intent(this@MainScreen, ActivityAgents::class.java)
            startActivity(intent)
        }
        binding.buttonWeapons.setOnClickListener {
            val intent = Intent(this@MainScreen, ActivityWeapons::class.java)
            startActivity(intent)
        }

        binding.buttonMaps.setOnClickListener {
            val intent = Intent(this@MainScreen, ActivityMaps::class.java)
            startActivity(intent)
        }
        //The "lineups" option can be only accessed by logging into the application.
        binding.buttonLineups.setOnClickListener {
            if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                val lineupsIntent = Intent(this@MainScreen, ActivityLineups::class.java) //.apply
                startActivity(lineupsIntent)
            } else {
                //showAlertError()
            }
        }

        //MÉTODO PARA MOSTRAR ALERTAS EN CASO DE ERROR EN EL REGISTRO
//    private fun showAlertError() { //
//        val builder = AlertDialog.Builder(this)
//        builder.setTitle("ERROR")
//        builder.setMessage("A esta opción solo se puede acceder si has iniciado sesión antes.")
//        builder.setPositiveButton("Aceptar", null)
//        val dialog: AlertDialog = builder.create()
//        dialog.show()
//    }
    }
}