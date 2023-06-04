package com.example.valorantpruebaapi.lineups

import ClasesApi.*
import ClasesApi.Map
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import com.example.valorantpruebaapi.R
import com.example.valorantpruebaapi.agents.ActivityAgents
import com.example.valorantpruebaapi.agents.AgentAdapter
import com.example.valorantpruebaapi.databinding.ActivityLineupsBinding
import com.example.valorantpruebaapi.maps.ActivityMaps
import com.example.valorantpruebaapi.weapons.ActivityWeapons
import com.google.android.material.navigation.NavigationView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private lateinit var drawerToggle: ActionBarDrawerToggle
private lateinit var drawerLayout: DrawerLayout
private lateinit var navigationView: NavigationView

private var agents: List<Agent> = emptyList()
private var maps: List<Map> = emptyList()
private lateinit var binding: ActivityLineupsBinding
private var displayNameAgent: String = ""
private var displayNameMap: String = ""
class ActivityLineups : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLineupsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "LINEUPS"

        //Para el método showVideos.
        binding.webViewLineups.settings.javaScriptEnabled = true

        //Para el recyclerView de Agents
        val valorantService = ValorantService.create()

        valorantService.getAgents().enqueue(object : Callback<AgentsResponse> {
            override fun onResponse(
                call: Call<AgentsResponse>, response: Response<AgentsResponse>
            ) {
                if (response.isSuccessful) {
                    val agentsResponse = response.body()
                    agents = agentsResponse?.data?.map { agent ->
                        Agent(
                            uuid = agent.uuid,
                            displayName = agent.displayName,
                            description = agent.description,
                            developerName = agent.developerName,
                            characterTags = agent.characterTags ?: emptyList(),
                            displayIcon = agent.displayIcon,
                            displayIconSmall = agent.displayIconSmall,
                            bustPortrait = agent.bustPortrait ?: "",
                            fullPortrait = agent.fullPortrait ?: "",
                            fullPortraitV2 = agent.fullPortraitV2 ?: "",
                            killfeedPortrait = agent.killfeedPortrait,
                            background = agent.background ?: "",
                            backgroundGradientColors = agent.backgroundGradientColors,
                            assetPath = agent.assetPath,
                            isFullPortraitRightFacing = agent.isFullPortraitRightFacing,
                            isPlayableCharacter = agent.isPlayableCharacter,
                            isAvailableForTest = agent.isAvailableForTest,
                            isBaseContent = agent.isBaseContent,
                            role = agent.role ?: Role("", "", "", "", ""),
                            abilities = agent.abilities
                        )
                    } ?: emptyList()
                    initRecyclerView()
                } else {
                    Log.e("MANIII", response.message())
                }
            }

            override fun onFailure(call: Call<AgentsResponse>, t: Throwable) {
                Log.e("MainActivity", t.message, t)
            }
        })

        //Para el recyclerView de Maps
        valorantService.getMaps().enqueue(object : Callback<MapsResponse> {
            override fun onResponse(
                call: Call<MapsResponse>, response: Response<MapsResponse>
            ) {
                if (response.isSuccessful) {
                    val mapsResponse = response.body()
                    maps = mapsResponse?.data?.map { map ->
                        Map(
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
        setSupportActionBar(binding.toolbarlineups)
        //Asignar la navigationView
        navigationView = findViewById(R.id.nav_view_lineups)
        //Asignar el drawer
        drawerLayout = findViewById(R.id.drawerLineups)

        // Inicializar ActionBarDrawerToggle y asociarlo al DrawerLayout y la Toolbar
        drawerToggle = ActionBarDrawerToggle(
            this, drawerLayout, binding.toolbarlineups, R.string.opendrawer, R.string.closedrawer
        )
        drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        navigationView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.agents_nav -> {
                    val agentsIntent =
                        Intent(this@ActivityLineups, ActivityAgents::class.java)
                    startActivity(agentsIntent)
                    true
                }
                R.id.maps_nav -> {
                    val mapsIntent =
                        Intent(this@ActivityLineups, ActivityMaps::class.java)
                    startActivity(mapsIntent)
                    true
                }
                R.id.weapons_nav -> {
                    val weaponsIntent =
                        Intent(this@ActivityLineups, ActivityWeapons::class.java)
                    startActivity(weaponsIntent)
                    true
                }
                R.id.lineups_nav -> {
                    val lineupsIntent2 =
                        Intent(this@ActivityLineups, ActivityLineups::class.java)
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

    private fun showVideoLineup() {
        // Read from database
        val database = FirebaseDatabase.getInstance()
        val agentsRef = database.getReference("Agents")

        agentsRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (agentSnapshot in snapshot.children) {
                    if (agentSnapshot.key == displayNameAgent) //display name del agente
                    {
                        val agentName = agentSnapshot.key
                        val mapsSnapshot = agentSnapshot.child("Map")
                        for (mapSnapshot in mapsSnapshot.children) {
                            if (mapSnapshot.key == displayNameMap) //display name del mapa
                            {
                                val mapName = mapSnapshot.key
                                val url = mapSnapshot.getValue(String::class.java)
                                Log.d("LALALA", "Agente: $agentName, Mapa: $mapName, URL: $url")
                                if (url != null) {
                                    binding.webViewLineups.loadUrl(url)
                                }
                            }
                        }
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.w("LALALA", "Failed to read value.", error.toException())
            }
        })
    }

    fun initRecyclerView() {
        val agentsManager = GridLayoutManager(this, 1, GridLayoutManager.HORIZONTAL, false)
        binding.recyclerViewAgentsLineups.layoutManager = agentsManager
        binding.recyclerViewAgentsLineups.adapter =
            AgentAdapter(agents) { agent -> onItemSelected(agent) }

        val mapsManager = GridLayoutManager(this, 1, GridLayoutManager.HORIZONTAL, false)
        binding.recyclerViewMapsLineups.layoutManager = mapsManager
        binding.recyclerViewMapsLineups.adapter =
            MapLineupAdapter(maps) { map -> onItemSelected(map) }
    }

    fun onItemSelected(agent: Agent) {
        displayNameAgent = agent.displayName
        showVideoLineup()
    }

    fun onItemSelected(map: Map) {
        displayNameMap = map.displayName
        showVideoLineup()
    }
}

