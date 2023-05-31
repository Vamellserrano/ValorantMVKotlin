package com.example.valorantpruebaapi

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.example.valorantpruebaapi.agents.ActivityAgents
import com.example.valorantpruebaapi.databinding.ActivityMainScreenBinding
import com.example.valorantpruebaapi.lineups.ActivityLineups
import com.example.valorantpruebaapi.maps.ActivityMaps
import com.example.valorantpruebaapi.weapons.ActivityWeapons
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth


class MainScreen : AppCompatActivity() {

    private lateinit var drawerToggle: ActionBarDrawerToggle
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView

    private lateinit var binding: ActivityMainScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // ---------------------------------------------------
        // --------------------- BUTTONS ---------------------
        // ---------------------------------------------------
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

        // -----------------------------------------------------------
        // --------------------- NAVIGATION MENU ---------------------
        // -----------------------------------------------------------
        // Asignar la Toolbar
        setSupportActionBar(binding.toolbarms)
        //Asignar la navigationView
        navigationView = findViewById(R.id.nav_view)
        //Asignar el drawer
        drawerLayout = findViewById(R.id.drawermainscreen)

        // Inicializar ActionBarDrawerToggle y asociarlo al DrawerLayout y la Toolbar
        drawerToggle = ActionBarDrawerToggle(
            this, drawerLayout, binding.toolbarms, R.string.opendrawer, R.string.closedrawer
        )
        drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        navigationView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.agents_nav -> {
                    val agentsIntent =
                        Intent(this@MainScreen, ActivityAgents::class.java)
                    startActivity(agentsIntent)
                    true
                }
                R.id.maps_nav -> {
                    val mapsIntent =
                        Intent(this@MainScreen, ActivityMaps::class.java)
                    startActivity(mapsIntent)
                    true
                }
                R.id.weapons_nav -> {
                    val weaponsIntent =
                        Intent(this@MainScreen, ActivityWeapons::class.java)
                    startActivity(weaponsIntent)
                    true
                }
                R.id.lineups_nav -> {
                    val lineupsIntent2 =
                        Intent(this@MainScreen, ActivityLineups::class.java)
                    startActivity(lineupsIntent2)
                    true
                }
                else -> false
            }
        }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}

//@Deprecated("Deprecated in Java")
//override fun onBackPressed() {
//    if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
//        drawerLayout.closeDrawer(GravityCompat.START)
//    } else {
//        super.onBackPressed()
//    }
//}

//MÉTODO PARA MOSTRAR ALERTAS EN CASO DE ERROR EN EL REGISTRO
//    private fun showAlertError() { //
//        val builder = AlertDialog.Builder(this)
//        builder.setTitle("ERROR")
//        builder.setMessage("A esta opción solo se puede acceder si has iniciado sesión antes.")
//        builder.setPositiveButton("Aceptar", null)
//        val dialog: AlertDialog = builder.create()
//        dialog.show()
//    }