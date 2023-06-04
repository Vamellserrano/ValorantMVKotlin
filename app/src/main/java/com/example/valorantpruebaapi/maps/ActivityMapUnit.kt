package com.example.valorantpruebaapi.maps

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.bumptech.glide.Glide
import com.example.valorantpruebaapi.R
import com.example.valorantpruebaapi.agents.ActivityAgents
import com.example.valorantpruebaapi.databinding.ActivityMapUnitBinding
import com.example.valorantpruebaapi.lineups.ActivityLineups
import com.example.valorantpruebaapi.weapons.ActivityWeapons
import com.google.android.material.navigation.NavigationView

class ActivityMapUnit : AppCompatActivity() {

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

    // Inicializar ActionBarDrawerToggle y asociarlo al DrawerLayout y la Toolbar
    drawerToggle = ActionBarDrawerToggle(
    this, drawerLayout, binding.toolbarmap, R.string.opendrawer, R.string.closedrawer
    )
    drawerLayout.addDrawerListener(drawerToggle)
    drawerToggle.syncState()
    supportActionBar?.setDisplayHomeAsUpEnabled(false)
    navigationView.setNavigationItemSelectedListener { item ->
        when (item.itemId) {
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
}
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}