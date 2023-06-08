package com.example.valorantpruebaapi.maps

import ClasesApi.*
import ClasesApi.Map
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.isVisible
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import com.example.valorantpruebaapi.LoginActivity
import com.example.valorantpruebaapi.MainScreen
import com.example.valorantpruebaapi.R
import com.example.valorantpruebaapi.agents.ActivityAgents
import com.example.valorantpruebaapi.databinding.ActivityMapsBinding
import com.example.valorantpruebaapi.lineups.ActivityLineups
import com.example.valorantpruebaapi.weapons.ActivityWeapons
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ActivityMaps : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth

    private lateinit var drawerToggle: ActionBarDrawerToggle
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView

    private var maps: List<Map> = emptyList()
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // Asignar la Toolbar
        setSupportActionBar(binding.toolbarmaps)
        supportActionBar?.title = "MAPS"

        val valorantService = ValorantService.create()

        valorantService.getMaps().enqueue(object : Callback<MapsResponse> {
            override fun onResponse(
                call: Call<MapsResponse>, response: Response <MapsResponse>
            ) {
                if (response.isSuccessful) {
                    val mapsResponse = response.body()
                    maps = mapsResponse?.data?.map {map ->
                        Map (
                            uuid = map.uuid,
                            displayName = map.displayName,
                            coordinates = map.coordinates,
                            displayIcon = map.displayIcon ?: "",
                            listViewIcon = map.listViewIcon,
                            splash = map.splash,
                            assetPath = map.assetPath,
                            mapUrl = map.mapUrl,
                            xMultiplier = map.xMultiplier,
                            yMultiplier = map.yMultiplier,
                            xScalarToAdd = map.xScalarToAdd,
                            yScalarToAdd = map.yScalarToAdd
                        )
                    } ?: emptyList()
                    initRecyclerView()
                } else {
                    Log.e("MANIII", response.message())
                }
            }
            override fun onFailure(call: Call<MapsResponse>, t: Throwable) {
                Log.e("MainActivity", t.message, t)
            }

        })

        // -----------------------------------------------------------
        // --------------------- NAVIGATION MENU ---------------------
        // -----------------------------------------------------------

        //Asignar la navigationView
        navigationView = findViewById(R.id.nav_view_maps)
        //Asignar el drawer
        drawerLayout = findViewById(R.id.drawermaps)
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
            this, drawerLayout, binding.toolbarmaps, R.string.opendrawer, R.string.closedrawer
        )
        drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        val navMenu: Menu = navigationView.menu
        navMenu.findItem(R.id.maps_nav).isVisible = false
        navigationView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home_nav -> {
                    val home =
                        Intent(this@ActivityMaps, MainScreen::class.java)
                    startActivity(home)
                    true
                }
                R.id.agents_nav -> {
                    val agentsIntent =
                        Intent(this@ActivityMaps, ActivityAgents::class.java)
                    startActivity(agentsIntent)
                    true
                }
                R.id.weapons_nav -> {
                    val weaponsIntent =
                        Intent(this@ActivityMaps, ActivityWeapons::class.java)
                    startActivity(weaponsIntent)
                    true
                }
                R.id.lineups_nav -> {
                    val lineupsIntent2 =
                        Intent(this@ActivityMaps, ActivityLineups::class.java)
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
                    Intent(this@ActivityMaps, LoginActivity::class.java)
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

    fun initRecyclerView() {
        val manager = GridLayoutManager(this, 1)

        binding.recyclerViewMaps.layoutManager = manager
            binding.recyclerViewMaps.adapter =
                MapAdapter(maps) { map -> onItemSelected(map) }
    }

    fun onItemSelected(map: Map) {
        val intent = Intent(this, ActivityMapUnit::class.java)
        intent.putExtra("MAP", map)
        startActivity(intent)

    }
}