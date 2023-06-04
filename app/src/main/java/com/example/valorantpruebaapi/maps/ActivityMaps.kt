package com.example.valorantpruebaapi.maps

import ClasesApi.*
import ClasesApi.Map
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import com.example.valorantpruebaapi.R
import com.example.valorantpruebaapi.agents.ActivityAgents
import com.example.valorantpruebaapi.databinding.ActivityMapsBinding
import com.example.valorantpruebaapi.lineups.ActivityLineups
import com.example.valorantpruebaapi.weapons.ActivityWeapons
import com.google.android.material.navigation.NavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ActivityMaps : AppCompatActivity() {

    private lateinit var drawerToggle: ActionBarDrawerToggle
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView

    private var maps: List<Map> = emptyList()
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "MAPAS"

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
        // Asignar la Toolbar
        setSupportActionBar(binding.toolbarmaps)
        //Asignar la navigationView
        navigationView = findViewById(R.id.nav_view_maps)
        //Asignar el drawer
        drawerLayout = findViewById(R.id.drawermaps)

        // Inicializar ActionBarDrawerToggle y asociarlo al DrawerLayout y la Toolbar
        drawerToggle = ActionBarDrawerToggle(
            this, drawerLayout, binding.toolbarmaps, R.string.opendrawer, R.string.closedrawer
        )
        drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        navigationView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.agents_nav -> {
                    val agentsIntent =
                        Intent(this@ActivityMaps, ActivityAgents::class.java)
                    startActivity(agentsIntent)
                    true
                }
                R.id.maps_nav -> {
                    val mapsIntent =
                        Intent(this@ActivityMaps, ActivityMaps::class.java)
                    startActivity(mapsIntent)
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