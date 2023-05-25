package com.example.valorantpruebaapi.lineups

import ClasesApi.*
import ClasesApi.Map
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.valorantpruebaapi.agents.AgentAdapter
import com.example.valorantpruebaapi.databinding.ActivityLineupsBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


private var agents: List<Agent> = emptyList()
private var maps: List<Map> = emptyList()
private lateinit var binding: ActivityLineupsBinding

class ActivityLineups : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLineupsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "LINEUPS"

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
    }

    fun onItemSelected(map: Map) {
    }

}

