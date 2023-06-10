package com.example.valorantpruebaapi

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.drawerlayout.widget.DrawerLayout
import com.example.valorantpruebaapi.agents.ActivityAgents
import com.example.valorantpruebaapi.databinding.ActivityMainScreenBinding
import com.example.valorantpruebaapi.lineups.ActivityLineups
import com.example.valorantpruebaapi.maps.ActivityMaps
import com.example.valorantpruebaapi.weapons.ActivityWeapons
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

class MainScreen : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var drawerToggle: ActionBarDrawerToggle
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView

    private lateinit var binding: ActivityMainScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Asignar la Toolbar
        setSupportActionBar(binding.toolbarms)
        supportActionBar?.title = "MENU"


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
            if (FirebaseAuth.getInstance().currentUser != null) {
                val intent = Intent(this@MainScreen, ActivityLineups::class.java)
                startActivity(intent)
            } else {
                showAlertError()
            }
        }

        // -----------------------------------------------------------
        // --------------------- NAVIGATION MENU ---------------------
        // -----------------------------------------------------------

        //Asignar la navigationView
        navigationView = findViewById(R.id.nav_view)
        //Asignar el drawer
        drawerLayout = findViewById(R.id.drawermainscreen)
        //Asignar el user
        val user = FirebaseAuth.getInstance().currentUser
        val guser = GoogleSignIn.getLastSignedInAccount(applicationContext)
        if (user != null || guser != null) {
            val userEmail = user?.email
            val guserEmail = guser?.email
            binding.userWelcome.text = "Bienvenido/a \n$userEmail"
            binding.userWelcome.text = "Bienvenido/a \n$guserEmail"
        } else {
            binding.userWelcome.isVisible = false
        }


        // Inicializar ActionBarDrawerToggle y asociarlo al DrawerLayout y la Toolbar
        drawerToggle = ActionBarDrawerToggle(
            this, drawerLayout, binding.toolbarms, R.string.opendrawer, R.string.closedrawer
        )
        drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        val navMenu: Menu = navigationView.menu
        navMenu.findItem(R.id.home_nav).isVisible = false
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

        if (user != null || guser != null) {
            binding.btnLogoutMs.text = "LOGOUT"
            binding.btnLogoutMs.setOnClickListener {
                mAuth = FirebaseAuth.getInstance()
                mAuth.signOut()
                Toast.makeText(this, "You've logged out.", Toast.LENGTH_SHORT).show()
                val logout =
                    Intent(this@MainScreen, LoginActivity::class.java)
                startActivity(logout)
            }
        } else {
            binding.btnLogoutMs.text = "LOGIN"
            binding.btnLogoutMs.setOnClickListener {
                val login =
                    Intent(this@MainScreen, LoginActivity::class.java)
                startActivity(login)
            }
        }
    }
        override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showAlertError() { //
        val builder = AlertDialog.Builder(this)
        builder.setTitle("ERROR")
        builder.setMessage("A esta opción solo se puede acceder si has iniciado sesión antes. \t¿Quieres iniciar sesión ahora?")
        builder.setPositiveButton("Aceptar"){ _, _ ->
            val intent = Intent(this@MainScreen, LoginActivity::class.java)
            startActivity(intent)
        }
        builder.setNegativeButton("Cancelar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}
