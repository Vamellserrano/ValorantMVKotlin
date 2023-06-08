package com.example.valorantpruebaapi.maps

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.isVisible
import androidx.drawerlayout.widget.DrawerLayout
import com.bumptech.glide.Glide
import com.example.valorantpruebaapi.LoginActivity
import com.example.valorantpruebaapi.MainScreen
import com.example.valorantpruebaapi.R
import com.example.valorantpruebaapi.agents.ActivityAgents
import com.example.valorantpruebaapi.databinding.ActivityMapUnitBinding
import com.example.valorantpruebaapi.lineups.ActivityLineups
import com.example.valorantpruebaapi.weapons.ActivityWeapons
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

class ActivityMapUnit : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth

    private lateinit var drawerToggle: ActionBarDrawerToggle
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView

    private lateinit var binding: ActivityMapUnitBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapUnitBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val map = intent.getParcelableExtra<ClasesApi.Map>("MAP")

        if (map != null) {

            // Asignar la Toolbar
            setSupportActionBar(binding.toolbarmap)
            supportActionBar?.title = map.displayName

            Glide.with(binding.imageViewMapPreview.context).load(map.listViewIcon)
                .into((binding.imageViewMapPreview))

            Glide.with(binding.imageViewMapDisposition.context).load(map.displayIcon)
                .into((binding.imageViewMapDisposition))

            binding.textViewMapName.text = map.displayName

        }
    // -----------------------------------------------------------
    // --------------------- NAVIGATION MENU ---------------------
    // -----------------------------------------------------------

    //Asignar la navigationView
    navigationView = findViewById(R.id.nav_view_map)
    //Asignar el drawer
    drawerLayout = findViewById(R.id.drawermap)
        //Asignar el user
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            val userEmail = user.email
            binding.userWelcome.text = "Bienvenido/a \n$userEmail"
        } else {
            binding.userWelcome.isVisible = false
        }

    // Inicializar ActionBarDrawerToggle y asociarlo al DrawerLayout y la Toolbar
    drawerToggle = ActionBarDrawerToggle(
    this, drawerLayout, binding.toolbarmap, R.string.opendrawer, R.string.closedrawer
    )
    drawerLayout.addDrawerListener(drawerToggle)
    drawerToggle.syncState()
    supportActionBar?.setDisplayHomeAsUpEnabled(false)
    navigationView.setNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.home_nav -> {
                val home =
                    Intent(this@ActivityMapUnit, MainScreen::class.java)
                startActivity(home)
                true
            }
            R.id.agents_nav -> {
                val agentsIntent =
                    Intent(this@ActivityMapUnit, ActivityAgents::class.java)
                startActivity(agentsIntent)
                true
            }
            R.id.maps_nav -> {
                val mapsIntent =
                    Intent(this@ActivityMapUnit, ActivityMaps::class.java)
                startActivity(mapsIntent)
                true
            }
            R.id.weapons_nav -> {
                val weaponsIntent =
                    Intent(this@ActivityMapUnit, ActivityWeapons::class.java)
                startActivity(weaponsIntent)
                true
            }
            R.id.lineups_nav -> {
                val lineupsIntent2 =
                    Intent(this@ActivityMapUnit, ActivityLineups::class.java)
                startActivity(lineupsIntent2)
                true
            }
            else -> false
        }
    }
        if (user != null) {
            binding.btnLogoutMs.setOnClickListener {
                mAuth = FirebaseAuth.getInstance()
                mAuth.signOut()
                Toast.makeText(this, "You've logged out.", Toast.LENGTH_SHORT).show()
                val logout =
                    Intent(this@ActivityMapUnit, LoginActivity::class.java)
                startActivity(logout)
            }
        } else {
            binding.btnLogoutMs.isVisible = false
        }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}